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
public final class Group_Table extends ModelAdapter<Group> {
  /**
   * Primary Key */
  public static final Property<String> id = new Property<String>(Group.class, "id");

  public static final Property<String> name = new Property<String>(Group.class, "name");

  public static final Property<String> desc = new Property<String>(Group.class, "desc");

  public static final Property<String> picture = new Property<String>(Group.class, "picture");

  public static final Property<Integer> notifyLevel = new Property<Integer>(Group.class, "notifyLevel");

  public static final TypeConvertedProperty<Long, Date> joinAt = new TypeConvertedProperty<Long, Date>(Group.class, "joinAt", true,
  new TypeConvertedProperty.TypeConverterGetter() {
  @Override
  public TypeConverter getTypeConverter(Class<?> modelClass) {
    Group_Table adapter = (Group_Table) FlowManager.getInstanceAdapter(modelClass);
  return adapter.global_typeConverterDateConverter;
  }
  });

  public static final TypeConvertedProperty<Long, Date> modifyAt = new TypeConvertedProperty<Long, Date>(Group.class, "modifyAt", true,
  new TypeConvertedProperty.TypeConverterGetter() {
  @Override
  public TypeConverter getTypeConverter(Class<?> modelClass) {
    Group_Table adapter = (Group_Table) FlowManager.getInstanceAdapter(modelClass);
  return adapter.global_typeConverterDateConverter;
  }
  });

  /**
   * Foreign Key */
  public static final Property<String> owner_id = new Property<String>(Group.class, "owner_id");

  public static final IProperty[] ALL_COLUMN_PROPERTIES = new IProperty[]{id,name,desc,picture,notifyLevel,joinAt,modifyAt,owner_id};

  private final DateConverter global_typeConverterDateConverter;

  public Group_Table(DatabaseHolder holder, DatabaseDefinition databaseDefinition) {
    super(databaseDefinition);
    global_typeConverterDateConverter = (DateConverter) holder.getTypeConverterForClass(Date.class);
  }

  @Override
  public final Class<Group> getModelClass() {
    return Group.class;
  }

  @Override
  public final String getTableName() {
    return "`Group`";
  }

  @Override
  public final Group newInstance() {
    return new Group();
  }

  @Override
  public final Property getProperty(String columnName) {
    columnName = QueryBuilder.quoteIfNeeded(columnName);
    switch ((columnName)) {
      case "`id`":  {
        return id;
      }
      case "`name`":  {
        return name;
      }
      case "`desc`":  {
        return desc;
      }
      case "`picture`":  {
        return picture;
      }
      case "`notifyLevel`":  {
        return notifyLevel;
      }
      case "`joinAt`":  {
        return joinAt;
      }
      case "`modifyAt`":  {
        return modifyAt;
      }
      case "`owner_id`": {
        return owner_id;
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
  public final void bindToInsertValues(ContentValues values, Group model) {
    values.put("`id`", model.getId() != null ? model.getId() : null);
    values.put("`name`", model.getName() != null ? model.getName() : null);
    values.put("`desc`", model.getDesc() != null ? model.getDesc() : null);
    values.put("`picture`", model.getPicture() != null ? model.getPicture() : null);
    values.put("`notifyLevel`", model.getNotifyLevel());
    Long refjoinAt = model.getJoinAt() != null ? global_typeConverterDateConverter.getDBValue(model.getJoinAt()) : null;
    values.put("`joinAt`", refjoinAt != null ? refjoinAt : null);
    Long refmodifyAt = model.getModifyAt() != null ? global_typeConverterDateConverter.getDBValue(model.getModifyAt()) : null;
    values.put("`modifyAt`", refmodifyAt != null ? refmodifyAt : null);
    if (model.getOwner() != null) {
      values.put("`owner_id`", model.getOwner().getId());
    } else {
      values.putNull("`owner_id`");
    }
  }

  @Override
  public final void bindToInsertStatement(DatabaseStatement statement, Group model, int start) {
    statement.bindStringOrNull(1 + start, model.getId());
    statement.bindStringOrNull(2 + start, model.getName());
    statement.bindStringOrNull(3 + start, model.getDesc());
    statement.bindStringOrNull(4 + start, model.getPicture());
    statement.bindLong(5 + start, model.getNotifyLevel());
    Long refjoinAt = model.getJoinAt() != null ? global_typeConverterDateConverter.getDBValue(model.getJoinAt()) : null;
    statement.bindNumberOrNull(6 + start, refjoinAt);
    Long refmodifyAt = model.getModifyAt() != null ? global_typeConverterDateConverter.getDBValue(model.getModifyAt()) : null;
    statement.bindNumberOrNull(7 + start, refmodifyAt);
    if (model.getOwner() != null) {
      statement.bindStringOrNull(8 + start, model.getOwner().getId());
    } else {
      statement.bindNull(8 + start);
    }
  }

  @Override
  public final void bindToUpdateStatement(DatabaseStatement statement, Group model) {
    statement.bindStringOrNull(1, model.getId());
    statement.bindStringOrNull(2, model.getName());
    statement.bindStringOrNull(3, model.getDesc());
    statement.bindStringOrNull(4, model.getPicture());
    statement.bindLong(5, model.getNotifyLevel());
    Long refjoinAt = model.getJoinAt() != null ? global_typeConverterDateConverter.getDBValue(model.getJoinAt()) : null;
    statement.bindNumberOrNull(6, refjoinAt);
    Long refmodifyAt = model.getModifyAt() != null ? global_typeConverterDateConverter.getDBValue(model.getModifyAt()) : null;
    statement.bindNumberOrNull(7, refmodifyAt);
    if (model.getOwner() != null) {
      statement.bindStringOrNull(8, model.getOwner().getId());
    } else {
      statement.bindNull(8);
    }
    statement.bindStringOrNull(9, model.getId());
  }

  @Override
  public final void bindToDeleteStatement(DatabaseStatement statement, Group model) {
    statement.bindStringOrNull(1, model.getId());
  }

  @Override
  public final String getCompiledStatementQuery() {
    return "INSERT INTO `Group`(`id`,`name`,`desc`,`picture`,`notifyLevel`,`joinAt`,`modifyAt`,`owner_id`) VALUES (?,?,?,?,?,?,?,?)";
  }

  @Override
  public final String getUpdateStatementQuery() {
    return "UPDATE `Group` SET `id`=?,`name`=?,`desc`=?,`picture`=?,`notifyLevel`=?,`joinAt`=?,`modifyAt`=?,`owner_id`=? WHERE `id`=?";
  }

  @Override
  public final String getDeleteStatementQuery() {
    return "DELETE FROM `Group` WHERE `id`=?";
  }

  @Override
  public final String getCreationQuery() {
    return "CREATE TABLE IF NOT EXISTS `Group`(`id` TEXT, `name` TEXT, `desc` TEXT, `picture` TEXT, `notifyLevel` INTEGER, `joinAt` TEXT, `modifyAt` TEXT, `owner_id` TEXT, PRIMARY KEY(`id`)"+ ", FOREIGN KEY(`owner_id`) REFERENCES " + com.raizlabs.android.dbflow.config.FlowManager.getTableName(com.mobile.factory.model.db.entity.User.class) + "(`id`) ON UPDATE NO ACTION ON DELETE NO ACTION" + ");";
  }

  @Override
  public final void loadFromCursor(FlowCursor cursor, Group model) {
    model.setId(cursor.getStringOrDefault("id"));
    model.setName(cursor.getStringOrDefault("name"));
    model.setDesc(cursor.getStringOrDefault("desc"));
    model.setPicture(cursor.getStringOrDefault("picture"));
    model.setNotifyLevel(cursor.getIntOrDefault("notifyLevel"));
    int index_joinAt = cursor.getColumnIndex("joinAt");
    if (index_joinAt != -1 && !cursor.isNull(index_joinAt)) {
      model.setJoinAt(global_typeConverterDateConverter.getModelValue(cursor.getLong(index_joinAt)));
    } else {
      model.setJoinAt(global_typeConverterDateConverter.getModelValue(null));
    }
    int index_modifyAt = cursor.getColumnIndex("modifyAt");
    if (index_modifyAt != -1 && !cursor.isNull(index_modifyAt)) {
      model.setModifyAt(global_typeConverterDateConverter.getModelValue(cursor.getLong(index_modifyAt)));
    } else {
      model.setModifyAt(global_typeConverterDateConverter.getModelValue(null));
    }
    int index_owner_id_User_Table = cursor.getColumnIndex("owner_id");
    if (index_owner_id_User_Table != -1 && !cursor.isNull(index_owner_id_User_Table)) {
      model.setOwner(new com.mobile.factory.model.db.entity.User());
      model.getOwner().setId(cursor.getString(index_owner_id_User_Table));
    } else {
      model.setOwner(null);
    }
  }

  @Override
  public final boolean exists(Group model, DatabaseWrapper wrapper) {
    return SQLite.selectCountOf()
    .from(Group.class)
    .where(getPrimaryConditionClause(model))
    .hasData(wrapper);
  }

  @Override
  public final OperatorGroup getPrimaryConditionClause(Group model) {
    OperatorGroup clause = OperatorGroup.clause();
    clause.and(id.eq(model.getId()));
    return clause;
  }
}
