package com.jing.imageloader.loader;

import android.graphics.Bitmap;

import com.jing.imageloader.request.BitmapRequest;

/**
 * author: 陈永镜 .
 * date: 2017/6/21 .
 * email: jing20071201@qq.com
 * <p>
 * introduce:
 */
public class NullLoader extends AbstractLoader {

    @Override
    protected Bitmap onLoad(BitmapRequest request) {
        return null;
    }
}
