package com.jing.imageloader.cache;

import android.graphics.Bitmap;

import com.jing.imageloader.request.BitmapRequest;

/**
 * author: 陈永镜 .
 * date: 2017/6/21 .
 * email: jing20071201@qq.com
 * <p>
 * introduce:
 */
public interface BitmapCache {

    void put(BitmapRequest request, Bitmap bitmap);

    Bitmap get(BitmapRequest request);

    void remove(BitmapRequest request);

}
