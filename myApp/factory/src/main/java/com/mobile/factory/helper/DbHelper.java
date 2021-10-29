package com.mobile.factory.helper;

import com.mobile.factory.model.db.Database;
import com.mobile.factory.model.db.entity.Group;
import com.mobile.factory.model.db.entity.GroupMember;
import com.mobile.factory.model.db.entity.Group_Table;
import com.mobile.factory.model.db.entity.Message;
import com.raizlabs.android.dbflow.config.DatabaseDefinition;
import com.raizlabs.android.dbflow.config.FlowManager;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.structure.BaseModel;
import com.raizlabs.android.dbflow.structure.ModelAdapter;
import com.raizlabs.android.dbflow.structure.database.DatabaseWrapper;
import com.raizlabs.android.dbflow.structure.database.transaction.ITransaction;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class DbHelper {

    /**
     * Collection of observers
     */
    private final Map<Class<?>, Set<ChangedListener>> changedListeners = new HashMap<>();

    private static final DbHelper application = new DbHelper();

    private DbHelper(){
    }

    /**
     * Unified method for adding or modifying
     * @param tClass  Pass a Class message
     * @param models  Array of instances corresponding to this Class
     * @param <Model> Generics Type of this instance，limited to BaseModel
     */
    public static <Model extends BaseModel> void save(final Class<Model> tClass,
                                                      final Model... models) {
        if (models == null) {
            return;
        }
        if(models.length == 0){
            return;
        }

        // A manager of the current database
        DatabaseDefinition definition = FlowManager.getDatabase(Database.class);
        // Commit a transaction
        definition.beginTransactionAsync(new ITransaction() {
            @Override
            public void execute(DatabaseWrapper databaseWrapper) {
                // Implement
                ModelAdapter<Model> adapter = FlowManager.getModelAdapter(tClass);
                // Save
                adapter.saveAll(Arrays.asList(models));
                // Notify
                application.notifySave(tClass, models);
            }
        }).build().execute();
    }

    /**
     * Call a notification
     *
     * @param tClass  Type of the notification
     * @param models  Notification Model array
     * @param <Model> Generics Type of this instance，limited to BaseModel
     */
    private final <Model extends BaseModel> void notifySave(final Class<Model> tClass,
                                                            final Model... models) {
        // Find listeners
        final Set<ChangedListener> listeners = getListeners(tClass);
        if (listeners != null && listeners.size() > 0) {
            // General notice
            for (ChangedListener<Model> listener : listeners) {
                listener.onDataSave(models);
            }
        }
//        // Exceptions
//        if (GroupMember.class.equals(tClass)) {
//            // Group members change, need to notify the corresponding group information update
//            updateGroup((GroupMember[]) models);
//        } else if (Message.class.equals(tClass)) {
//            // Message changes, should notify the conversation list update
//            updateSession((Message[]) models);
//        }
    }



    /**
     * Add a listener
     *
     * @param tClass   Follow a list
     * @param listener Listener
     * @param <Model>  Generics Type Model
     */
    public static <Model extends BaseModel> void addChangedListener(final Class<Model> tClass,
                                                                    ChangedListener<Model> listener) {
        Set<ChangedListener> changedListeners = application.getListeners(tClass);
        if (changedListeners == null) {
            // Initialize a certain type of container
            changedListeners = new HashSet<>();
            // Add Map
            application.changedListeners.put(tClass, changedListeners);
        }
        changedListeners.add(listener);
    }


    /**
     * Delete a listener in a tClass
     *
     * @param tClass   A list
     * @param listener Listener
     * @param <Model>  Generics Type Model
     */
    public static <Model extends BaseModel> void removeChangedListener(final Class<Model> tClass,
                                                                       ChangedListener<Model> listener) {
        Set<ChangedListener> changedListeners = application.getListeners(tClass);
        if (changedListeners == null) {
            // The container itself is null, which means there is no
            return;
        }
        // Remove your listener from the container
        changedListeners.remove(listener);
    }
    /**
     * Notification listener
     */
    @SuppressWarnings({"unused", "unchecked"})
    public interface ChangedListener<Data extends BaseModel> {
        void onDataSave(Data... list);

        void onDataDelete(Data... list);
    }

    /**
     * From all the listeners, get all the listeners of a certain list
     *
     * @param modelClass corresponding Class info
     * @param <Model>    Generics Type
     * @return Set<ChangedListener>
     */
    private <Model extends BaseModel> Set<ChangedListener> getListeners(Class<Model> modelClass) {
        if (changedListeners.containsKey(modelClass)) {
            return changedListeners.get(modelClass);
        }
        return null;
    }
}
