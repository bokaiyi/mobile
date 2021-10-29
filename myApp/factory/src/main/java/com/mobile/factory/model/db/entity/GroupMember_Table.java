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
import java.lang.Boolean;
import java.lang.Class;
import java.lang.IllegalArgumentException;
import java.lang.Long;
import java.lang.Override;
import java.lang.String;
import java.util.Date;

/**
 * This is generated code. Please do not modify */
public final class GroupMember_Table extends ModelAdapter<GroupMember> {
  /**
   * Primary Key */
  public static final Property<String> id = new Property<String>(GroupMember.class, "id");

  public static final Property<String> alias = new Property<String>(GroupMember.class, "alias");

  public static final Property<Boolean> isAdmin = new Property<Boolean>(GroupMember.class, "isAdmin");

  public static final Property<Boolean> isOwner = new Property<Boolean>(GroupMember.class, "isOwner");

  public static final TypeConvertedProperty<Long, Date> modifyAt = new TypeConvertedProperty<Long, Date>(GroupMember.class, "modifyAt", true,
  new TypeConvertedProperty.TypeConverterGetter() {
  @Override
  public TypeConverter getTypeConverter(Class<?> modelClass) {
    GroupMember_Table adapter = (GroupMember_Table) FlowManager.getInstanceAdapter(modelClass);
  return adapter.global_typeConverterDateConverter;
  }
  });

  /**
   * Foreign Key */
  public static final Property<String> group_id = new Property<String>(GroupMember.class, "group_id");

  /**
   * Foreign Key */
  public static final Property<String> user_id = new Property<String>(GroupMember.class, "user_id");

  public static final IProperty[] ALL_COLUMN_PROPERTIES = new IProperty[]{id,alias,isAdmin,isOwner,modifyAt,group_id,user_id};

  private final DateConverter global_typeConverterDateConverter;

  public GroupMember_Table(DatabaseHolder holder, DatabaseDefinition databaseDefinition) {
    super(databaseDefinition);
    global_typeConverterDateConverter = (DateConverter) holder.getTypeConverterForClass(Date.class);
  }

  @Override
  public final Class<GroupMember> getModelClass() {
    return GroupMember.class;
  }

  @Override
  public final String getTableName() {
    return "`GroupMember`";
  }

  @Override
  public final GroupMember newInstance() {
    return new GroupMember();
  }

  @Override
  public final Property getProperty(String columnName) {
    columnName = QueryBuilder.quoteIfNeeded(columnName);
    switch ((columnName)) {
      case "`id`":  {
        return id;
      }
      case "`alias`":  {
        return alias;
      }
      case "`isAdmin`":  {
        return isAdmin;
      }
      case "`isOwner`":  {
        return isOwner;
      }
      case "`modifyAt`":  {
        return modifyAt;
      }
      case "`group_id`": {
        return group_id;
      }
      case "`user_id`": {
        return user_id;
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
  public final void bindToInsertValues(ContentValues values, GroupMember model) {
    values.put("`id`", model.getId() != null ? model.getId() : null);
    values.put("`alias`", model.getAlias() != null ? model.getAlias() : null);
    values.put("`isAdmin`", model.isAdmin() ? 1 : 0);
    values.put("`isOwner`", model.isOwner() ? 1 : 0);
    Long refmodifyAt = model.getModifyAt() != null ? global_typeConverterDateConverter.getDBValue(model.getModifyAt()) : null;
    values.put("`modifyAt`", refmodifyAt != null ? refmodifyAt : null);
    if (model.getGroup() != null) {
      values.put("`group_id`", model.getGroup().getId());
    } else {
      values.putNull("`group_id`");
    }
    if (model.getUser() != null) {
      values.put("`user_id`", model.getUser().getId());
    } else {
      values.putNull("`user_id`");
    }
  }

  @Override
  public final void bindToInsertStatement(DatabaseStatement statement, GroupMember model,
      int start) {
    statement.bindStringOrNull(1 + start, model.getId());
    statement.bindStringOrNull(2 + start, model.getAlias());
    statement.bindLong(3 + start, model.isAdmin() ? 1 : 0);
    statement.bindLong(4 + start, model.isOwner() ? 1 : 0);
    Long refmodifyAt = model.getModifyAt() != null ? global_typeConverterDateConverter.getDBValue(model.getModifyAt()) : null;
    statement.bindNumberOrNull(5 + start, refmodifyAt);
    if (model.getGroup() != null) {
      statement.bindStringOrNull(6 + start, model.getGroup().getId());
    } else {
      statement.bindNull(6 + start);
    }
    if (model.getUser() != null) {
      statement.bindStringOrNull(7 + start, model.getUser().getId());
    } else {
      statement.bindNull(7 + start);
    }
  }

  @Override
  public final void bindToUpdateStatement(DatabaseStatement statement, GroupMember model) {
    statement.bindStringOrNull(1, model.getId());
    statement.bindStringOrNull(2, model.getAlias());
    statement.bindLong(3, model.isAdmin() ? 1 : 0);
    statement.bindLong(4, model.isOwner() ? 1 : 0);
    Long refmodifyAt = model.getModifyAt() != null ? global_typeConverterDateConverter.getDBValue(model.getModifyAt()) : null;
    statement.bindNumberOrNull(5, refmodifyAt);
    if (model.getGroup() != null) {
      statement.bindStringOrNull(6, model.getGroup().getId());
    } else {
      statement.bindNull(6);
    }
    if (model.getUser() != null) {
      statement.bindStringOrNull(7, model.getUser().getId());
    } else {
      statement.bindNull(7);
    }
    statement.bindStringOrNull(8, model.getId());
  }

  @Override
  public final void bindToDeleteStatement(DatabaseStatement statement, GroupMember model) {
    statement.bindStringOrNull(1, model.getId());
  }

  @Override
  public final String getCompiledStatementQuery() {
    return "INSERT INTO `GroupMember`(`id`,`alias`,`isAdmin`,`isOwner`,`modifyAt`,`group_id`,`user_id`) VALUES (?,?,?,?,?,?,?)";
  }

  @Override
  public final String getUpdateStatementQuery() {
    return "UPDATE `GroupMember` SET `id`=?,`alias`=?,`isAdmin`=?,`isOwner`=?,`modifyAt`=?,`group_id`=?,`user_id`=? WHERE `id`=?";
  }

  @Override
  public final String getDeleteStatementQuery() {
    return "DELETE FROM `GroupMember` WHERE `id`=?";
  }

  @Override
  public final String getCreationQuery() {
    return "CREATE TABLE IF NOT EXISTS `GroupMember`(`id` TEXT, `alias` TEXT, `isAdmin` INTEGER, `isOwner` INTEGER, `modifyAt` TEXT, `group_id` TEXT, `user_id` TEXT, PRIMARY KEY(`id`)"+ ", FOREIGN KEY(`group_id`) REFERENCES " + com.raizlabs.android.dbflow.config.FlowManager.getTableName(com.mobile.factory.model.db.entity.Group.class) + "(`id`) ON UPDATE NO ACTION ON DELETE NO ACTION"+ ", FOREIGN KEY(`user_id`) REFERENCES " + com.raizlabs.android.dbflow.config.FlowManager.getTableName(com.mobile.factory.model.db.entity.User.class) + "(`id`) ON UPDATE NO ACTION ON DELETE NO ACTION" + ");";
  }

  @Override
  public final void loadFromCursor(FlowCursor cursor, GroupMember model) {
    model.setId(cursor.getStringOrDefault("id"));
    model.setAlias(cursor.getStringOrDefault("alias"));
    int index_isAdmin = cursor.getColumnIndex("isAdmin");
    if (index_isAdmin != -1 && !cursor.isNull(index_isAdmin)) {
      model.setAdmin(cursor.getBoolean(index_isAdmin));
    } else {
      model.setAdmin(false);
    }
    int index_isOwner = cursor.getColumnIndex("isOwner");
    if (index_isOwner != -1 && !cursor.isNull(index_isOwner)) {
      model.setOwner(cursor.getBoolean(index_isOwner));
    } else {
      model.setOwner(false);
    }
    int index_modifyAt = cursor.getColumnIndex("modifyAt");
    if (index_modifyAt != -1 && !cursor.isNull(index_modifyAt)) {
      model.setModifyAt(global_typeConverterDateConverter.getModelValue(cursor.getLong(index_modifyAt)));
    } else {
      model.setModifyAt(global_typeConverterDateConverter.getModelValue(null));
    }
    int index_group_id_Group_Table = cursor.getColumnIndex("group_id");
    if (index_group_id_Group_Table != -1 && !cursor.isNull(index_group_id_Group_Table)) {
      model.setGroup(new com.mobile.factory.model.db.entity.Group());
      model.getGroup().setId(cursor.getString(index_group_id_Group_Table));
    } else {
      model.setGroup(null);
    }
    int index_user_id_User_Table = cursor.getColumnIndex("user_id");
    if (index_user_id_User_Table != -1 && !cursor.isNull(index_user_id_User_Table)) {
      model.setUser(new com.mobile.factory.model.db.entity.User());
      model.getUser().setId(cursor.getString(index_user_id_User_Table));
    } else {
      model.setUser(null);
    }
  }

  @Override
  public final boolean exists(GroupMember model, DatabaseWrapper wrapper) {
    return SQLite.selectCountOf()
    .from(GroupMember.class)
    .where(getPrimaryConditionClause(model))
    .hasData(wrapper);
  }

  @Override
  public final OperatorGroup getPrimaryConditionClause(GroupMember model) {
    OperatorGroup clause = OperatorGroup.clause();
    clause.and(id.eq(model.getId()));
    return clause;
  }
}
