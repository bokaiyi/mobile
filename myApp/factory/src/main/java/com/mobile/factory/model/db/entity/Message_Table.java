package com.mobile.factory.model.db.entity;

import android.content.ContentValues;
import com.raizlabs.android.dbflow.config.DatabaseDefinition;
import com.raizlabs.android.dbflow.config.DatabaseHolder;
import com.raizlabs.android.dbflow.config.FlowManager;
import com.raizlabs.android.dbflow.converter.DateConverter;
import com.raizlabs.android.dbflow.converter.TypeConverter;
import com.raizlabs.android.dbflow.sql.QueryBuilder;
import com.raizlabs.android.dbflow.sql.language.OperatorGroup;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.sql.language.property.IProperty;
import com.raizlabs.android.dbflow.sql.language.property.Property;
import com.raizlabs.android.dbflow.sql.language.property.TypeConvertedProperty;
import com.raizlabs.android.dbflow.structure.ModelAdapter;
import com.raizlabs.android.dbflow.structure.database.DatabaseStatement;
import com.raizlabs.android.dbflow.structure.database.DatabaseWrapper;
import com.raizlabs.android.dbflow.structure.database.FlowCursor;
import java.lang.Class;
import java.lang.IllegalArgumentException;
import java.lang.Integer;
import java.lang.Long;
import java.lang.Override;
import java.lang.String;
import java.util.Date;

/**
 * This is generated code. Please do not modify */
public final class Message_Table extends ModelAdapter<Message> {
  /**
   * Primary Key */
  public static final Property<String> id = new Property<String>(Message.class, "id");

  public static final Property<String> content = new Property<String>(Message.class, "content");

  public static final Property<String> attach = new Property<String>(Message.class, "attach");

  public static final Property<Integer> type = new Property<Integer>(Message.class, "type");

  public static final TypeConvertedProperty<Long, Date> createAt = new TypeConvertedProperty<Long, Date>(Message.class, "createAt", true,
  new TypeConvertedProperty.TypeConverterGetter() {
  @Override
  public TypeConverter getTypeConverter(Class<?> modelClass) {
    Message_Table adapter = (Message_Table) FlowManager.getInstanceAdapter(modelClass);
  return adapter.global_typeConverterDateConverter;
  }
  });

  public static final Property<Integer> status = new Property<Integer>(Message.class, "status");

  /**
   * Foreign Key */
  public static final Property<String> group_id = new Property<String>(Message.class, "group_id");

  /**
   * Foreign Key */
  public static final Property<String> sender_id = new Property<String>(Message.class, "sender_id");

  /**
   * Foreign Key */
  public static final Property<String> receiver_id = new Property<String>(Message.class, "receiver_id");

  public static final IProperty[] ALL_COLUMN_PROPERTIES = new IProperty[]{id,content,attach,type,createAt,status,group_id,sender_id,receiver_id};

  private final DateConverter global_typeConverterDateConverter;

  public Message_Table(DatabaseHolder holder, DatabaseDefinition databaseDefinition) {
    super(databaseDefinition);
    global_typeConverterDateConverter = (DateConverter) holder.getTypeConverterForClass(Date.class);
  }

  @Override
  public final Class<Message> getModelClass() {
    return Message.class;
  }

  @Override
  public final String getTableName() {
    return "`Message`";
  }

  @Override
  public final Message newInstance() {
    return new Message();
  }

  @Override
  public final Property getProperty(String columnName) {
    columnName = QueryBuilder.quoteIfNeeded(columnName);
    switch ((columnName)) {
      case "`id`":  {
        return id;
      }
      case "`content`":  {
        return content;
      }
      case "`attach`":  {
        return attach;
      }
      case "`type`":  {
        return type;
      }
      case "`createAt`":  {
        return createAt;
      }
      case "`status`":  {
        return status;
      }
      case "`group_id`": {
        return group_id;
      }
      case "`sender_id`": {
        return sender_id;
      }
      case "`receiver_id`": {
        return receiver_id;
      }
      default: {
        throw new IllegalArgumentException("Invalid column name passed. Ensure you are calling the correct table's column");
      }
    }
  }

  @Override
  public final IProperty[] getAllColumnProperties() {
    return ALL_COLUMN_PROPERTIES;
  }

  @Override
  public final void bindToInsertValues(ContentValues values, Message model) {
    values.put("`id`", model.getId() != null ? model.getId() : null);
    values.put("`content`", model.getContent() != null ? model.getContent() : null);
    values.put("`attach`", model.getAttach() != null ? model.getAttach() : null);
    values.put("`type`", model.getType());
    Long refcreateAt = model.getCreateAt() != null ? global_typeConverterDateConverter.getDBValue(model.getCreateAt()) : null;
    values.put("`createAt`", refcreateAt != null ? refcreateAt : null);
    values.put("`status`", model.getStatus());
    if (model.getGroup() != null) {
      values.put("`group_id`", model.getGroup().getId());
    } else {
      values.putNull("`group_id`");
    }
    if (model.getSender() != null) {
      values.put("`sender_id`", model.getSender().getId());
    } else {
      values.putNull("`sender_id`");
    }
    if (model.getReceiver() != null) {
      values.put("`receiver_id`", model.getReceiver().getId());
    } else {
      values.putNull("`receiver_id`");
    }
  }

  @Override
  public final void bindToInsertStatement(DatabaseStatement statement, Message model, int start) {
    statement.bindStringOrNull(1 + start, model.getId());
    statement.bindStringOrNull(2 + start, model.getContent());
    statement.bindStringOrNull(3 + start, model.getAttach());
    statement.bindLong(4 + start, model.getType());
    Long refcreateAt = model.getCreateAt() != null ? global_typeConverterDateConverter.getDBValue(model.getCreateAt()) : null;
    statement.bindNumberOrNull(5 + start, refcreateAt);
    statement.bindLong(6 + start, model.getStatus());
    if (model.getGroup() != null) {
      statement.bindStringOrNull(7 + start, model.getGroup().getId());
    } else {
      statement.bindNull(7 + start);
    }
    if (model.getSender() != null) {
      statement.bindStringOrNull(8 + start, model.getSender().getId());
    } else {
      statement.bindNull(8 + start);
    }
    if (model.getReceiver() != null) {
      statement.bindStringOrNull(9 + start, model.getReceiver().getId());
    } else {
      statement.bindNull(9 + start);
    }
  }

  @Override
  public final void bindToUpdateStatement(DatabaseStatement statement, Message model) {
    statement.bindStringOrNull(1, model.getId());
    statement.bindStringOrNull(2, model.getContent());
    statement.bindStringOrNull(3, model.getAttach());
    statement.bindLong(4, model.getType());
    Long refcreateAt = model.getCreateAt() != null ? global_typeConverterDateConverter.getDBValue(model.getCreateAt()) : null;
    statement.bindNumberOrNull(5, refcreateAt);
    statement.bindLong(6, model.getStatus());
    if (model.getGroup() != null) {
      statement.bindStringOrNull(7, model.getGroup().getId());
    } else {
      statement.bindNull(7);
    }
    if (model.getSender() != null) {
      statement.bindStringOrNull(8, model.getSender().getId());
    } else {
      statement.bindNull(8);
    }
    if (model.getReceiver() != null) {
      statement.bindStringOrNull(9, model.getReceiver().getId());
    } else {
      statement.bindNull(9);
    }
    statement.bindStringOrNull(10, model.getId());
  }

  @Override
  public final void bindToDeleteStatement(DatabaseStatement statement, Message model) {
    statement.bindStringOrNull(1, model.getId());
  }

  @Override
  public final String getCompiledStatementQuery() {
    return "INSERT INTO `Message`(`id`,`content`,`attach`,`type`,`createAt`,`status`,`group_id`,`sender_id`,`receiver_id`) VALUES (?,?,?,?,?,?,?,?,?)";
  }

  @Override
  public final String getUpdateStatementQuery() {
    return "UPDATE `Message` SET `id`=?,`content`=?,`attach`=?,`type`=?,`createAt`=?,`status`=?,`group_id`=?,`sender_id`=?,`receiver_id`=? WHERE `id`=?";
  }

  @Override
  public final String getDeleteStatementQuery() {
    return "DELETE FROM `Message` WHERE `id`=?";
  }

  @Override
  public final String getCreationQuery() {
    return "CREATE TABLE IF NOT EXISTS `Message`(`id` TEXT, `content` TEXT, `attach` TEXT, `type` INTEGER, `createAt` TEXT, `status` INTEGER, `group_id` TEXT, `sender_id` TEXT, `receiver_id` TEXT, PRIMARY KEY(`id`)"+ ", FOREIGN KEY(`group_id`) REFERENCES " + com.raizlabs.android.dbflow.config.FlowManager.getTableName(com.mobile.factory.model.db.entity.Group.class) + "(`id`) ON UPDATE NO ACTION ON DELETE NO ACTION"+ ", FOREIGN KEY(`sender_id`) REFERENCES " + com.raizlabs.android.dbflow.config.FlowManager.getTableName(com.mobile.factory.model.db.entity.User.class) + "(`id`) ON UPDATE NO ACTION ON DELETE NO ACTION"+ ", FOREIGN KEY(`receiver_id`) REFERENCES " + com.raizlabs.android.dbflow.config.FlowManager.getTableName(com.mobile.factory.model.db.entity.User.class) + "(`id`) ON UPDATE NO ACTION ON DELETE NO ACTION" + ");";
  }

  @Override
  public final void loadFromCursor(FlowCursor cursor, Message model) {
    model.setId(cursor.getStringOrDefault("id"));
    model.setContent(cursor.getStringOrDefault("content"));
    model.setAttach(cursor.getStringOrDefault("attach"));
    model.setType(cursor.getIntOrDefault("type"));
    int index_createAt = cursor.getColumnIndex("createAt");
    if (index_createAt != -1 && !cursor.isNull(index_createAt)) {
      model.setCreateAt(global_typeConverterDateConverter.getModelValue(cursor.getLong(index_createAt)));
    } else {
      model.setCreateAt(global_typeConverterDateConverter.getModelValue(null));
    }
    model.setStatus(cursor.getIntOrDefault("status"));
    int index_group_id_Group_Table = cursor.getColumnIndex("group_id");
    if (index_group_id_Group_Table != -1 && !cursor.isNull(index_group_id_Group_Table)) {
      model.setGroup(new com.mobile.factory.model.db.entity.Group());
      model.getGroup().setId(cursor.getString(index_group_id_Group_Table));
    } else {
      model.setGroup(null);
    }
    int index_sender_id_User_Table = cursor.getColumnIndex("sender_id");
    if (index_sender_id_User_Table != -1 && !cursor.isNull(index_sender_id_User_Table)) {
      model.setSender(new com.mobile.factory.model.db.entity.User());
      model.getSender().setId(cursor.getString(index_sender_id_User_Table));
    } else {
      model.setSender(null);
    }
    int index_receiver_id_User_Table = cursor.getColumnIndex("receiver_id");
    if (index_receiver_id_User_Table != -1 && !cursor.isNull(index_receiver_id_User_Table)) {
      model.setReceiver(new com.mobile.factory.model.db.entity.User());
      model.getReceiver().setId(cursor.getString(index_receiver_id_User_Table));
    } else {
      model.setReceiver(null);
    }
  }

  @Override
  public final boolean exists(Message model, DatabaseWrapper wrapper) {
    return SQLite.selectCountOf()
    .from(Message.class)
    .where(getPrimaryConditionClause(model))
    .hasData(wrapper);
  }

  @Override
  public final OperatorGroup getPrimaryConditionClause(Message model) {
    OperatorGroup clause = OperatorGroup.clause();
    clause.and(id.eq(model.getId()));
    return clause;
  }
}
