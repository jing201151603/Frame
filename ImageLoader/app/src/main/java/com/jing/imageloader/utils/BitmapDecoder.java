package com.jing.imageloader.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/**
 * author: 陈永镜 .
 * date: 2017/6/21 .
 * email: jing20071201@qq.com
 * <p>
 * introduce:
 */
public abstract class BitmapDecoder {

    public Bitmap decodeBitmap(int reqWidth, int reqHeight) {

        //初始化options
        BitmapFactory.Options options = new BitmapFactory.Options();

        //不加载图片内存，只获取图片的宽高
        options.inJustDecodeBounds = true;

        decodeBitmapWithOption(options);

        //计算图片缩放比例
        caculateSampleSizeWithOption(options,reqWidth,reqHeight);

        return decodeBitmapWithOption(options);

    }

    private void caculateSampleSizeWithOption(BitmapFactory.Options options, int reqWidth, int reqHeight) {

        int width = options.outWidth;
        int height = options.outHeight;

        int inSampleSize = 1;

        if (width > reqWidth || height > reqHeight) {
            //宽高缩放比例
            int heightRatio = Math.round(height / reqHeight);
            int widthtRatio = Math.round(width / reqWidth);

            inSampleSize = Math.max(heightRatio, widthtRatio);
        }

        //当inSampleSize为2，图片的宽和高变成原来的1/2
        options.inSampleSize = inSampleSize;

        //每个像素2个字节
        options.inPreferredConfig = Bitmap.Config.RGB_565;

        //bitmap占内存
        options.inJustDecodeBounds = false;

        //当系统内存不足时可以回收bitmap
        options.inPurgeable = true;
        options.inInputShareable = true;


    }

    public abstract Bitmap decodeBitmapWithOption(BitmapFactory.Options options);
}
