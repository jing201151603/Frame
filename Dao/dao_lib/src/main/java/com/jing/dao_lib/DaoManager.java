package com.jing.dao_lib;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;

import com.jing.dao_lib.util.ExceptionUtils;
import com.jing.dao_lib.util.FileUtils;

import java.io.File;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 数据库的管理类，入口
 */
public class DaoManager {
    private String sqliteDatabasePath;

    private SQLiteDatabase sqliteDatabas;

    private static DaoManager instance;

    private final String dbName = "myselft.db";

    private static Lock lock = new ReentrantLock();

    private Map<String, DaoExecute> executeCache = Collections.synchronizedMap(new HashMap<String, DaoExecute>());


    private DaoManager() {

    }

    public void init(String dbPath, String dbName, int version) {

    }

    /**
     * @param entityClass
     * @param <T>
     * @param <M>
     * @return
     */
    public synchronized <T extends BaseDao<M>, M> T getExecuter(Context context, Class<M> entityClass) {
        if (TextUtils.isEmpty(dbName)) {
            ExceptionUtils.throwExcep("dbName can not null!!!");
        }


        File file = new File(FileUtils.getRootPath(context));
        if (!file.exists()) {
            file.mkdirs();
        }
        String newSqlitePath = file.getAbsolutePath() + File.separator + dbName;

        //不同数据库切换，需要先关闭上个数据库
        if (sqliteDatabasePath != null && !sqliteDatabasePath.equals(newSqlitePath)) {
            if (sqliteDatabas != null && sqliteDatabas.isOpen()) {
                sqliteDatabas.close();
            }
        }
        sqliteDatabasePath = newSqlitePath;
        sqliteDatabas = SQLiteDatabase.openOrCreateDatabase(sqliteDatabasePath, null);


        DaoExecute daoExecute = null;
        String key = entityClass.getSimpleName();
        daoExecute = executeCache.get(key);
        if (daoExecute == null) {
            daoExecute = new DaoExecute();
            daoExecute.init(entityClass);
        }


        return (T) daoExecute;
    }

    protected SQLiteDatabase getDatabase() {
        return sqliteDatabas;
    }

    /**
     * 采用DLC单利，线程安全
     *
     * @return
     */
    public static DaoManager getInstance() {
        if (instance == null) {
            lock.lock();
            if (instance == null) {
                instance = new DaoManager();
            }
            lock.unlock();
        }
        return instance;
    }

    /**
     * 清除所有数据库连接
     */
    public void close() {
        if (sqliteDatabas != null) sqliteDatabas.close();
    }


}
