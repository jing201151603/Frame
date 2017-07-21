package com.jing.imageloader.loader;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;

import com.jing.imageloader.request.BitmapRequest;
import com.jing.imageloader.utils.BitmapDecoder;
import com.jing.imageloader.utils.ImageViewHelper;

import java.io.File;

/**
 * author: 陈永镜 .
 * date: 2017/6/21 .
 * email: jing20071201@qq.com
 * <p>
 * introduce:
 */
public class LocalLoader extends AbstractLoader {
    @Override
    protected Bitmap onLoad(BitmapRequest request) {

        final String path = Uri.parse(request.getImageUrl()).getPath();

        File file = new File(path);
        if (!file.exists()){
            return null;
        }

        BitmapDecoder bitmapDecoder=new BitmapDecoder() {
            @Override
            public Bitmap decodeBitmapWithOption(BitmapFactory.Options options) {

                return BitmapFactory.decodeFile(path,options);
            }
        };

        return bitmapDecoder.decodeBitmap(ImageViewHelper.getImageViewWidth
                (request.getImageView()), ImageViewHelper.getImageViewHeight
                (request.getImageView()));
    }
}
