package com.mobile.factory.model.db.entity;

import android.content.ContentValues;
import com.raizlabs.android.dbflow.config.DatabaseDefinition;
import com.raizlabs.android.dbflow.config.DatabaseHolder;
import com.raizlabs.android.dbflow.config.FlowManager;
import com.raizlabs.android.dbflow.converter.BooleanConverter;
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
import java.lang.Integer;
import java.lang.Long;
import java.lang.Override;
import java.lang.String;
import java.util.Date;

/**
 * This is generated code. Please do not modify */
public final class User_Table extends ModelAdapter<User> {
  /**
   * Primary Key */
  public static final Property<String> id = new Property<String>(User.class, "id");

  public static final Property<String> name = new Property<String>(User.class, "name");

  public static final Property<String> portrait = new Property<String>(User.class, "portrait");

  public static final Property<String> phone = new Property<String>(User.class, "phone");

  public static final Property<String> description = new Property<String>(User.class, "description");

  public static final Property<Integer> sex = new Property<Integer>(User.class, "sex");

  public static final TypeConvertedProperty<Long, Date> updateAt = new TypeConvertedProperty<Long, Date>(User.class, "updateAt", true,
  new TypeConvertedProperty.TypeConverterGetter() {
  @Override
  public TypeConverter getTypeConverter(Class<?> modelClass) {
    User_Table adapter = (User_Table) FlowManager.getInstanceAdapter(modelClass);
  return adapter.global_typeConverterDateConverter;
  }
  });

  public static final Property<Integer> follows = new Property<Integer>(User.class, "follows");

  public static final Property<Integer> followings = new Property<Integer>(User.class, "followings");

  public static final TypeConvertedProperty<Integer, Boolean> isFollow = new TypeConvertedProperty<Integer, Boolean>(User.class, "isFollow", true,
  new TypeConvertedProperty.TypeConverterGetter() {
  @Override
  public TypeConverter getTypeConverter(Class<?> modelClass) {
    User_Table adapter = (User_Table) FlowManager.getInstanceAdapter(modelClass);
  return adapter.global_typeConverterBooleanConverter;
  }
  });

  public static final Property<String> note = new Property<String>(User.class, "note");

  public static final IProperty[] ALL_COLUMN_PROPERTIES = new IProperty[]{id,name,portrait,phone,description,sex,updateAt,follows,followings,isFollow,note};

  private final DateConverter global_typeConverterDateConverter;

  private final BooleanConverter global_typeConverterBooleanConverter;

  public User_Table(DatabaseHolder holder, DatabaseDefinition databaseDefinition) {
    super(databaseDefinition);
    global_typeConverterDateConverter = (DateConverter) holder.getTypeConverterForClass(Date.class);
    global_typeConverterBooleanConverter = (BooleanConverter) holder.getTypeConverterForClass(Boolean.class);
  }

  @Override
  public final Class<User> getModelClass() {
    return User.class;
  }

  @Override
  public final String getTableName() {
    return "`User`";
  }

  @Override
  public final User newInstance() {
    return new User();
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
      case "`portrait`":  {
        return portrait;
      }
      case "`phone`":  {
        return phone;
      }
      case "`description`":  {
        return description;
      }
      case "`sex`":  {
        return sex;
      }
      case "`updateAt`":  {
        return updateAt;
      }
      case "`follows`":  {
        return follows;
      }
      case "`followings`":  {
        return followings;
      }
      case "`isFollow`":  {
        return isFollow;
      }
      case "`note`":  {
        return note;
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
  public final void bindToInsertValues(ContentValues values, User model) {
    values.put("`id`", model.getId() != null ? model.getId() : null);
    values.put("`name`", model.getName() != null ? model.getName() : null);
    values.put("`portrait`", model.getPortrait() != null ? model.getPortrait() : null);
    values.put("`phone`", model.getPhone() != null ? model.getPhone() : null);
    values.put("`description`", model.getDescription() != null ? model.getDescription() : null);
    values.put("`sex`", model.getSex());
    Long refupdateAt = model.getUpdateAt() != null ? global_typeConverterDateConverter.getDBValue(model.getUpdateAt()) : null;
    values.put("`updateAt`", refupdateAt != null ? refupdateAt : null);
    values.put("`follows`", model.getFollows());
    values.put("`followings`", model.getFollowings());
    Integer refisFollow = model.isFollow() != null ? global_typeConverterBooleanConverter.getDBValue(model.isFollow()) : null;
    values.put("`isFollow`", refisFollow != null ? refisFollow : null);
    values.put("`note`", model.getNote() != null ? model.getNote() : null);
  }

  @Override
  public final void bindToInsertStatement(DatabaseStatement statement, User model, int start) {
    statement.bindStringOrNull(1 + start, model.getId());
    statement.bindStringOrNull(2 + start, model.getName());
    statement.bindStringOrNull(3 + start, model.getPortrait());
    statement.bindStringOrNull(4 + start, model.getPhone());
    statement.bindStringOrNull(5 + start, model.getDescription());
    statement.bindLong(6 + start, model.getSex());
    Long refupdateAt = model.getUpdateAt() != null ? global_typeConverterDateConverter.getDBValue(model.getUpdateAt()) : null;
    statement.bindNumberOrNull(7 + start, refupdateAt);
    statement.bindLong(8 + start, model.getFollows());
    statement.bindLong(9 + start, model.getFollowings());
    Integer refisFollow = model.isFollow() != null ? global_typeConverterBooleanConverter.getDBValue(model.isFollow()) : null;
    statement.bindNumberOrNull(10 + start, refisFollow);
    statement.bindStringOrNull(11 + start, model.getNote());
  }

  @Override
  public final void bindToUpdateStatement(DatabaseStatement statement, User model) {
    statement.bindStringOrNull(1, model.getId());
    statement.bindStringOrNull(2, model.getName());
    statement.bindStringOrNull(3, model.getPortrait());
    statement.bindStringOrNull(4, model.getPhone());
    statement.bindStringOrNull(5, model.getDescription());
    statement.bindLong(6, model.getSex());
    Long refupdateAt = model.getUpdateAt() != null ? global_typeConverterDateConverter.getDBValue(model.getUpdateAt()) : null;
    statement.bindNumberOrNull(7, refupdateAt);
    statement.bindLong(8, model.getFollows());
    statement.bindLong(9, model.getFollowings());
    Integer refisFollow = model.isFollow() != null ? global_typeConverterBooleanConverter.getDBValue(model.isFollow()) : null;
    statement.bindNumberOrNull(10, refisFollow);
    statement.bindStringOrNull(11, model.getNote());
    statement.bindStringOrNull(12, model.getId());
  }

  @Override
  public final void bindToDeleteStatement(DatabaseStatement statement, User model) {
    statement.bindStringOrNull(1, model.getId());
  }

  @Override
  public final String getCompiledStatementQuery() {
    return "INSERT INTO `User`(`id`,`name`,`portrait`,`phone`,`description`,`sex`,`updateAt`,`follows`,`followings`,`isFollow`,`note`) VALUES (?,?,?,?,?,?,?,?,?,?,?)";
  }

  @Override
  public final String getUpdateStatementQuery() {
    return "UPDATE `User` SET `id`=?,`name`=?,`portrait`=?,`phone`=?,`description`=?,`sex`=?,`updateAt`=?,`follows`=?,`followings`=?,`isFollow`=?,`note`=? WHERE `id`=?";
  }

  @Override
  public final String getDeleteStatementQuery() {
    return "DELETE FROM `User` WHERE `id`=?";
  }

  @Override
  public final String getCreationQuery() {
    return "CREATE TABLE IF NOT EXISTS `User`(`id` TEXT, `name` TEXT, `portrait` TEXT, `phone` TEXT, `description` TEXT, `sex` INTEGER, `updateAt` TEXT, `follows` INTEGER, `followings` INTEGER, `isFollow` INTEGER, `note` TEXT, PRIMARY KEY(`id`))";
  }

  @Override
  public final void loadFromCursor(FlowCursor cursor, User model) {
    model.setId(cursor.getStringOrDefault("id"));
    model.setName(cursor.getStringOrDefault("name"));
    model.setPortrait(cursor.getStringOrDefault("portrait"));
    model.setPhone(cursor.getStringOrDefault("phone"));
    model.setDescription(cursor.getStringOrDefault("description"));
    model.setSex(cursor.getIntOrDefault("sex"));
    int index_updateAt = cursor.getColumnIndex("updateAt");
    if (index_updateAt != -1 && !cursor.isNull(index_updateAt)) {
      model.setUpdateAt(global_typeConverterDateConverter.getModelValue(cursor.getLong(index_updateAt)));
    } else {
      model.setUpdateAt(global_typeConverterDateConverter.getModelValue(null));
    }
    model.setFollows(cursor.getIntOrDefault("follows"));
    model.setFollowings(cursor.getIntOrDefault("followings"));
    int index_isFollow = cursor.getColumnIndex("isFollow");
    if (index_isFollow != -1 && !cursor.isNull(index_isFollow)) {
      model.setFollow(global_typeConverterBooleanConverter.getModelValue(cursor.getInt(index_isFollow)));
    } else {
      model.setFollow(global_typeConverterBooleanConverter.getModelValue(null));
    }
    model.setNote(cursor.getStringOrDefault("note"));
  }

  @Override
  public final boolean exists(User model, DatabaseWrapper wrapper) {
    return SQLite.selectCountOf()
    .from(User.class)
    .where(getPrimaryConditionClause(model))
    .hasData(wrapper);
  }

  @Override
  public final OperatorGroup getPrimaryConditionClause(User model) {
    OperatorGroup clause = OperatorGroup.clause();
    clause.and(id.eq(model.getId()));
    return clause;
  }
}
