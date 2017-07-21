package com.jing.dao_lib;

import android.content.ContentValues;
import android.database.Cursor;
import android.text.TextUtils;
import android.util.Log;

import com.jing.dao_lib.annotion.DbField;
import com.jing.dao_lib.annotion.DbPrimaryField;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by 陈永镜 on 2017/2/26.
 */

/**
 * 数据库操作的执行者
 *
 * @param <T>
 */
public class DaoExecute<T> extends BaseDao<T> {

    private static final String TAG = "DaoExecute";

    /**
     * @param sql 执行的sql语句
     * @return
     */
    @Override
    public List<T> query(String sql, Class<T> entity) {
        return query(sql, entity, null);
    }

    /**
     * @param sql  执行语句
     * @param args 对应的sql中的条件
     * @return
     */
    @Override
    public List<T> query(String sql, Class<T> entity, String[] args) {
        List<T> result = null;
        try {
            Cursor cursor = DaoManager.getInstance().getDatabase().rawQuery(sql, args);
            result = getResult(cursor, entity);
        } catch (Exception e) {
            Log.e(TAG, "数据库语句异常，请检查！");
        }
        return result;
    }

    /**
     * @param entity 插入的类型具体数据
     */
    @Override
    public Long insert(T entity) {
        HashMap<String, String> map = getValues(entity);
        ContentValues contentValues = getContentValues(map);
        Long result = DaoManager.getInstance().getDatabase().insert(tableName, null, contentValues);
        return result;
    }

    /**
     * @param sql 插入的sql语句
     */
    @Override
    public void insert(String sql) {
        try {
            DaoManager.getInstance().getDatabase().execSQL(sql);
        } catch (Exception e) {
            Log.e(TAG, "数据库语句异常，请检查！");
        }
    }

    /**
     * @param entity 新的类型
     * @param where  更新的条件
     * @return
     */
    @Override
    public int update(T entity, T where) {

        /*HashMap<String, String> whereMap = getConditionValues(where);

        HashMap<String, String> newEntityMap = getConditionValues(newEntity);

        ContentValues contentValues = getContentValues(newEntityMap);

        Condition condition = new Condition(whereMap);

        int result = DaoManager.getInstance().getDatabase().update(tableName, contentValues, condition.getWhereCause(), condition.getWhereCauseArry());
*/


        Map<String, String> values = getValues(entity);
        Map<String, String> whereClause = getValues(where);

        Condition condition = new Condition(whereClause);

        ContentValues contentValues = getContentValues(values);

        int result = DaoManager.getInstance().getDatabase().update(tableName, contentValues, condition.whereClause, condition.whereArgs);

        return result;
    }

    /**
     * @param sql 条件语句
     */
    @Override
    public void update(String sql) {
        try {
            DaoManager.getInstance().getDatabase().execSQL(sql);
        } catch (Exception e) {
            Log.e(TAG, "数据库语句异常，请检查！");
        }
    }

    /**
     * @param sql  sql语句
     * @param args 条件语句
     */
    @Override
    public void update(String sql, String[] args) {
        try {
            DaoManager.getInstance().getDatabase().execSQL(sql, args);
        } catch (Exception e) {
            Log.e(TAG, "数据库语句异常，请检查！");
        }
    }

    /**
     * @param where 删除的条件
     * @return
     */
    @Override
    public int delete(T where) {
        Map<String, String> values = getValues(where);

        Condition condition = new Condition(values);

        int result = DaoManager.getInstance().getDatabase().delete(tableName, condition.whereClause, condition.whereArgs);

        return result;
    }

    /**
     * @param sql 条件语句
     */
    @Override
    public void delete(String sql) {
        try {
            DaoManager.getInstance().getDatabase().execSQL(sql);
        } catch (Exception e) {
            Log.e(TAG, "数据库语句异常，请检查！");
        }
    }

    /**
     * @param sql  条件语句
     * @param args 条件语句对应的值
     */
    @Override
    public void delete(String sql, String[] args) {
        try {
            DaoManager.getInstance().getDatabase().execSQL(sql, args);
        } catch (Exception e) {
            Log.e(TAG, "数据库语句异常，请检查！");
        }
    }

    /**
     * @param entity 查询的条件
     * @return
     */
    @Override
    public List<T> query(T entity) {
        return query(entity, null, null, null);
    }

    @Override
    public List query(T where, String orderBy, Integer startIndex, Integer limit) {

        Map<String, String> values = getValues(where);

        String limitStr = null;
        if (startIndex != null && limit != null)
            limitStr = startIndex + " , " + limit;

        Condition condition = new Condition(values);
        Cursor cursor = DaoManager.getInstance().getDatabase().query(tableName, null, condition.whereClause,
                condition.whereArgs, null, null, orderBy, limitStr);

        List<T> result = getResult(cursor, where);
        cursor.close();
        return result;
    }

    /**
     * @param cursor 查询语句获得游标
     * @param entity 结果的类型
     * @return
     */
    private List<T> getResult(Cursor cursor, T entity) {
        List result = new ArrayList();
        Object item;
        while (cursor.moveToNext()) {
            try {
                item = entity.getClass().newInstance();
                Iterator<Map.Entry<String, Field>> colNameIterator = cacheMap.entrySet().iterator();
                while (colNameIterator.hasNext()) {
                    Map.Entry<String, Field> entry = colNameIterator.next();
                    String colName = entry.getKey();
                    int colIndex = cursor.getColumnIndex(colName);
                    Field colField = entry.getValue();
                    Class type = colField.getType();
                    if (colIndex != -1) {
                        if (type == String.class) {
                            colField.set(item, cursor.getString(colIndex));
                        } else if (type == Double.class || type == double.class) {
                            colField.set(item, cursor.getDouble(colIndex));
                        } else if (type == Integer.class || type == int.class) {
                            colField.set(item, cursor.getInt(colIndex));
                        } else if (type == Long.class || type == long.class) {
                            colField.set(item, cursor.getLong(colIndex));
                        } else if (type == byte[].class) {
                            colField.set(item, cursor.getBlob(colIndex));
                        } else if (type == boolean.class || type == Boolean.class) {
                            String boolStr = cursor.getString(colIndex);
                            boolean b = false;
                            if ("true".equals(boolStr)) {
                                b = true;
                            }
                            colField.set(item, b);
                        } else if (type == short.class || type == Short.class) {
                            colField.set(item, cursor.getShort(colIndex));
                        } else {
                            /**
                             * 无法识别类型
                             */
                        }
                    }

                }
                result.add(item);
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    /**
     * @param cursor 查询语句获得游标
     * @param entity 结果的类型
     * @return
     */
    private List<T> getResult(Cursor cursor, Class<T> entity) {
        List result = new ArrayList();
        Object item;
        while (cursor.moveToNext()) {
            try {
                item = entity.newInstance();
                Iterator<Map.Entry<String, Field>> colNameIterator = cacheMap.entrySet().iterator();
                while (colNameIterator.hasNext()) {
                    Map.Entry<String, Field> entry = colNameIterator.next();
                    String colName = entry.getKey();
                    int colIndex = cursor.getColumnIndex(colName);
                    Field colField = entry.getValue();
                    Class type = colField.getType();
                    if (colIndex != -1) {
                        if (type == String.class) {
                            colField.set(item, cursor.getString(colIndex));
                        } else if (type == Double.class || type == double.class) {
                            colField.set(item, cursor.getDouble(colIndex));
                        } else if (type == Integer.class || type == int.class) {
                            colField.set(item, cursor.getInt(colIndex));
                        } else if (type == Long.class || type == long.class) {
                            colField.set(item, cursor.getLong(colIndex));
                        } else if (type == byte[].class) {
                            colField.set(item, cursor.getBlob(colIndex));
                        } else if (type == boolean.class || type == Boolean.class) {
                            String boolStr = cursor.getString(colIndex);
                            boolean b = false;
                            if ("true".equals(boolStr)) {
                                b = true;
                            }
                            colField.set(item, b);
                        } else if (type == short.class || type == Short.class) {
                            colField.set(item, cursor.getShort(colIndex));
                        } else {
                            /**
                             * 无法识别类型
                             */
                        }
                    }

                }
                result.add(item);
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    /**
     * @param map 表字段名和实际储存数据的缓存
     * @return
     */
    private ContentValues getContentValues(Map<String, String> map) {
        ContentValues contentValues = new ContentValues();
        Iterator<String> keyIterator = map.keySet().iterator();
        while (keyIterator.hasNext()) {
            String cacheKey = keyIterator.next();
            String cacheValue = map.get(cacheKey);
            if (cacheValue != null) {
                contentValues.put(cacheKey, cacheValue);
            }
        }
        return contentValues;
    }

    /**
     * @param entity 通过表映射缓存和传入需要存储的实际数据
     * @return
     */
    private HashMap<String, String> getValues(T entity) {
        HashMap<String, String> map = new HashMap<>();
        Iterator<Field> valueIterator = cacheMap.values().iterator();
        while (valueIterator.hasNext()) {
            String cacheKey;
            String cacheValue;
            Field colField = valueIterator.next();
            if (colField.getAnnotation(DbPrimaryField.class) != null) {
                continue;
            }
            if (colField.getAnnotation(DbField.class) != null) {
                cacheKey = colField.getAnnotation(DbField.class).value();
            } else {
                cacheKey = colField.getName();
            }
            try {
                if (null == colField.get(entity)) {
                    continue;
                }
                cacheValue = colField.get(entity).toString();
                map.put(cacheKey, cacheValue);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return map;
    }

    /**
     * 这里有个漏洞
     *
     * @param entity 通过表映射缓存和传入需要存储的实际数据 这里去掉一些特殊数据类型的默认情况
     * @return
     */
    private HashMap<String, String> getConditionValues(T entity) {
        HashMap<String, String> map = new HashMap<>();
        Iterator<Field> valueIterator = cacheMap.values().iterator();
        while (valueIterator.hasNext()) {
            String cacheKey;
            String cacheValue;
            Field colField = valueIterator.next();
            if (colField.getAnnotation(DbPrimaryField.class) != null) {
                continue;
            }
            if (colField.getAnnotation(DbField.class) != null) {
                cacheKey = colField.getAnnotation(DbField.class).value();
            } else {
                cacheKey = colField.getName();
            }
            try {
                if (null == colField.get(entity)) {
                    continue;
                }
                cacheValue = colField.get(entity).toString();
                map.put(cacheKey, cacheValue);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return map;
    }

    /**
     * 封装修改语句
     */
    class Condition {
        /**
         * 查询条件
         */
        private String whereClause;

        /**
         * 条件对应的参数的值
         */
        private String[] whereArgs;

        public Condition(Map<String, String> whereClause) {
            ArrayList<String> list = new ArrayList<>();
            StringBuilder stringBuilder = new StringBuilder();

            stringBuilder.append(" 1=1 ");

            Set<String> keys = whereClause.keySet();
            Iterator<String> iterator = keys.iterator();
            while (iterator.hasNext()) {
                String key = iterator.next();
                String value = whereClause.get(key);

                if (!TextUtils.isEmpty(value)) {
                    stringBuilder.append(" and " + key + " =?");
                    list.add(value);
                }

            }

            this.whereClause = stringBuilder.toString();
            this.whereArgs = list.toArray(new String[list.size()]);

        }
    }


    @Override
    public void executeSql(String sql) {
        try {
            DaoManager.getInstance().getDatabase().execSQL(sql);
        } catch (Exception e) {
            Log.e(TAG, "数据库语句异常，请检查！");
        }
    }

}
