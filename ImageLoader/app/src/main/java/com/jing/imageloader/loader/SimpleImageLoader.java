package com.jing.imageloader.loader;

import android.graphics.Bitmap;
import android.widget.ImageView;

import com.jing.imageloader.cache.BitmapCache;
import com.jing.imageloader.config.DisaplayConfig;
import com.jing.imageloader.config.ImageLoaderConfig;
import com.jing.imageloader.request.BitmapRequest;
import com.jing.imageloader.request.RequestQueue;

/**
 * author: 陈永镜 .
 * date: 2017/6/21 .
 * email: jing20071201@qq.com
 * <p>
 * introduce:
 */
public class SimpleImageLoader {

    private ImageLoaderConfig config;//配置

    private RequestQueue mRequestQueue;//队列

    private static volatile SimpleImageLoader mInstance;

    private SimpleImageLoader() {

    }

    private SimpleImageLoader(ImageLoaderConfig imageLoaderConfig) {
        this.config = imageLoaderConfig;
        mRequestQueue = new RequestQueue(config.getThreadCount());

        //开启请求
        mRequestQueue.start();
    }

    public final static SimpleImageLoader getInstance(ImageLoaderConfig config) {
        if (mInstance == null) {
            synchronized (SimpleImageLoader.class) {
                if (mInstance == null) {
                    mInstance = new SimpleImageLoader(config);
                }
            }
        }
        return mInstance;
    }

    public static final SimpleImageLoader getInstance() {
        if (mInstance == null) {
            throw new UnsupportedOperationException("没有初始化");
        }
        return mInstance;
    }

    public void displayImage(ImageView imageView, String uri) {
        displayImage(imageView, uri, null, null);
    }

    public void displayImage(ImageView imageView, String uri,
                             DisaplayConfig disaplayConfig, ImageListener imageListener) {
        //实例化一个请求
        BitmapRequest bitmapRequest = new BitmapRequest(imageView, uri, disaplayConfig, imageListener);
        //添加到队列中
        mRequestQueue.addRequest(bitmapRequest);


    }

    public static interface ImageListener {
        void onComplete(ImageView imageView, Bitmap bitmap, String uri);
    }

    public ImageLoaderConfig getConfig() {
        return config;
    }


}
