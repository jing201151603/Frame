package com.jing.imageloader.loader;

import java.util.HashMap;
import java.util.Map;

/**
 * author: 陈永镜 .
 * date: 2017/6/21 .
 * email: jing20071201@qq.com
 * <p>
 * introduce:
 */
public class LoaderManager {

    //缓存所有支持的Loader类型
    private Map<String, Loader> mLoaderMap = new HashMap<>();

    private static final LoaderManager mInstance = new LoaderManager();

    public static LoaderManager getInstance() {
        return mInstance;
    }

    private LoaderManager() {
        register("http", new UrlLoader());
        register("https", new UrlLoader());
        register("file", new LocalLoader());
    }

    private void register(String schema, Loader urlLoader) {

        mLoaderMap.put(schema, urlLoader);
    }

    public Loader getLoader(String schema) {
        if (mLoaderMap.containsKey(schema))
            return mLoaderMap.get(schema);

        return new NullLoader();
    }

}
