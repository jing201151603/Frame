package com.jing.imageloader.request;

import android.text.TextUtils;

import com.jing.imageloader.loader.Loader;
import com.jing.imageloader.loader.LoaderManager;

import java.util.concurrent.BlockingQueue;

/**
 * author: 陈永镜 .
 * date: 2017/6/21 .
 * email: jing20071201@qq.com
 * <p>
 * introduce:转发器，请求转发线程，不断从请求队列获取请求
 */
public class RequestDispatcher extends Thread {

    private BlockingQueue<BitmapRequest> mRequestQueue;

    public RequestDispatcher(BlockingQueue<BitmapRequest> mRequestQueue) {
        this.mRequestQueue = mRequestQueue;
    }

    @Override
    public void run() {

        while (!isInterrupted()) {

            try {
                BitmapRequest request = mRequestQueue.take();

                String schema = parseSchema(request.getImageUrl());

                Loader loader = LoaderManager.getInstance().getLoader(schema);

                loader.loadImage(request);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    private String parseSchema(String imageUrl) {

        if (!TextUtils.isEmpty(imageUrl) && imageUrl.contains("://")) {
            return imageUrl.split("://")[0];
        } else {//不支持的类型

        }
        return null;
    }
}
