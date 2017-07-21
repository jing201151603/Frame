package com.jing.imageloader.config;

import com.jing.imageloader.cache.BitmapCache;
import com.jing.imageloader.cache.DoubleCache;
import com.jing.imageloader.cache.MemoryCache;
import com.jing.imageloader.policy.LoadPolicy;
import com.jing.imageloader.policy.ReversePolicy;

/**
 * author: 陈永镜 .
 * date: 2017/6/21 .
 * email: jing20071201@qq.com
 * <p>
 * introduce:
 */
public class ImageLoaderConfig {

    private BitmapCache bitmapCache=new MemoryCache();//缓存策略

    private LoadPolicy loadPolicy=new ReversePolicy();//加载策略

    //默认线程数
    private int threadCount = Runtime.getRuntime().availableProcessors();

    private DisaplayConfig disaplayConfig=new DisaplayConfig();

    private ImageLoaderConfig() {

    }

    /**
     * 建造者模式
     */
    public static class Builder {

        private ImageLoaderConfig config;

        public Builder() {
            config = new ImageLoaderConfig();
        }

        public Builder setCachePolicy(BitmapCache bitmapCache) {
            config.bitmapCache = bitmapCache;
            return this;
        }

        public Builder setLoadPolicy(LoadPolicy loadPolicy) {
            config.loadPolicy = loadPolicy;
            return this;
        }

        public Builder setThreadCount(int count) {
            config.threadCount = count;
            return this;
        }

        public Builder setLoadingImage(int resId) {
            config.disaplayConfig.loadingImage = resId;
            return this;
        }

        public Builder setFailImage(int resId) {
            config.disaplayConfig.failImage = resId;
            return this;
        }

        public ImageLoaderConfig build(){
            return config;
        }
    }

    public LoadPolicy getLoadPolicy() {
        return loadPolicy;
    }

    public int getThreadCount() {
        return threadCount;
    }

    public BitmapCache getBitmapCache() {
        return bitmapCache;
    }

    public DisaplayConfig getDisaplayConfig() {
        return disaplayConfig;
    }
}
