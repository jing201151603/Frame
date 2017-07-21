package com.jing.dao_lib;

import android.database.Cursor;
import android.text.TextUtils;

import com.jing.dao_lib.annotion.DbField;
import com.jing.dao_lib.annotion.DbPrimaryField;
import com.jing.dao_lib.annotion.DbTable;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 数据库执行者的父类
 *
 * @param <T>
 */
public abstract class BaseDao<T> implements IBaseDao<T> {
    protected HashMap<String, Field> cacheMap;
    protected String tableName;
    protected Class<T> entityClazz;

    /**
     * @param entity 需要缓存的数据类型
     */
    protected synchronized void init(Class<T> entity) {
        this.entityClazz = entity;
        if (entityClazz.getAnnotation(DbTable.class) != null) {
            tableName = entityClazz.getAnnotation(DbTable.class).value();
        } else {
            tableName = entity.getSimpleName();
        }

        if (!TextUtils.isEmpty(createTable(entity))) {
            DaoManager.getInstance().getDatabase().execSQL(createTable(entity));
        }
        cacheMap = new HashMap<>();
        initCacheMap();
    }

    /**
     * @param entity 动态建表对应的类型
     * @return 返回拼接的sql语句
     */
    protected String createTable(Class<T> entity) {
        Field[] fields = entity.getDeclaredFields();
        String tableName;
        if (entity.getAnnotation(DbTable.class) != null) {
            tableName = (entity.getAnnotation(DbTable.class)).value();
        } else {
            tableName = entity.getSimpleName();
        }
        List<Field> aFileds = new ArrayList<>();
        Field primaryKey = null;
        for (Field field : fields) {
            if (field.getAnnotation(DbField.class) != null) {
                aFileds.add(field);
            } else if (field.getAnnotation(DbPrimaryField.class) != null) {
                primaryKey = field;
            }
        }

        List<String> fieldNames = new ArrayList<>();
        List<String> fieldTypes = new ArrayList<>();
        String primaryKeyName = null;
        String primaryType = null;
        if (primaryKey != null) {
            primaryKeyName = primaryKey.getAnnotation(DbPrimaryField.class).value();
            Class type = primaryKey.getType();
            if (type == String.class) {
                primaryType = "TEXT";
            } else if (type == Double.class || type == double.class) {
                primaryType = "REAL";
            } else if (type == Integer.class || type == int.class) {
                primaryType = "INTEGER";
            } else if (type == Long.class || type == long.class) {
                primaryType = "LONG";
            } else if (type == byte[].class) {
                primaryType = "BLOB";
            } else if (type == boolean.class || type == Boolean.class) {
                primaryType = "Boolean";
            } else if (type == short.class || type == Short.class) {
                primaryType = "short";
            } else {
                /**
                 * 不支持的类型
                 */
                primaryType = "varchar(20)";
            }
        }

        for (int i = 0; i < aFileds.size(); i++) {
            String fieldName;
            String fieldType;
            if (aFileds.get(i).getAnnotation(DbField.class) != null) {
                fieldName = aFileds.get(i).getAnnotation(DbField.class).value();
                Class type = aFileds.get(i).getType();
                if (type == String.class) {
                    fieldType = "TEXT";
                } else if (type == Double.class || type == double.class) {
                    fieldType = "REAL";
                } else if (type == Integer.class || type == int.class) {
                    fieldType = "INTEGER";
                } else if (type == Long.class || type == long.class) {
                    fieldType = "LONG";
                } else if (type == byte[].class) {
                    fieldType = "BLOB";
                } else if (type == boolean.class || type == Boolean.class) {
                    fieldType = "Boolean";
                } else if (type == short.class || type == Short.class) {
                    fieldType = "Short";
                } else {
                    /**
                     * 不支持的类型
                     */
                    fieldType = "varchar(20)";
                }
            } else {
                fieldType = "varchar(20)";
                fieldName = aFileds.get(i).getName();
            }
            fieldTypes.add(fieldType);
            fieldNames.add(fieldName);
        }
        StringBuilder builder = new StringBuilder();
        builder.append("create table if not exists " + tableName + "(");
        if (primaryKeyName != null) {
//                    builder.append(", " + primaryKeyName + " " + primaryType + " PRIMARY KEY ");
            builder.append(primaryKeyName + " integer PRIMARY KEY autoincrement , ");
        }
        for (int index = 0; index < fieldNames.size(); index++) {

            builder.append(fieldNames.get(index) + " " + fieldTypes.get(index));
            if (index != fieldNames.size() - 1) {
                builder.append(",");
            } else {
                builder.append(")");
            }
        }
        return builder.toString();
    }

    /**
     * 数据库表和类型的映射缓存
     */
    private void initCacheMap() {
        String sql = "select * from " + tableName + " limit 1 , 0";
        Cursor cursor = null;
        try {
            cursor = DaoManager.getInstance().getDatabase().rawQuery(sql, null);
            String[] colNames = cursor.getColumnNames();
            Field[] fields = entityClazz.getDeclaredFields();
            for (Field field : fields) {
                field.setAccessible(true);
            }
            for (String colName : colNames) {
                Field colField = null;
                for (Field field : fields) {
                    String fieldName;
                    if (field.getAnnotation(DbPrimaryField.class) != null) {
                        if (colName.equals(field.getAnnotation(DbPrimaryField.class).value()))
                            cacheMap.put(colName, field);
                        continue;
                    }
                    if (field.getAnnotation(DbField.class) != null) {
                        fieldName = field.getAnnotation(DbField.class).value();
                    } else {
                        fieldName = field.getName();
                    }

                    if (fieldName.equals(colName)) {
                        colField = field;
                        break;
                    }
                }

                if (colField != null) {
                    cacheMap.put(colName, colField);
                }
            }
        } catch (Exception e) {

        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }


}
