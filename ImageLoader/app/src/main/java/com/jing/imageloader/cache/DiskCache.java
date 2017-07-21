package com.jing.imageloader.cache;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;

import com.jing.imageloader.disk.DiskLruCache;
import com.jing.imageloader.disk.IOUtil;
import com.jing.imageloader.request.BitmapRequest;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * author: 陈永镜 .
 * date: 2017/6/21 .
 * email: jing20071201@qq.com
 * <p>
 * introduce:
 */
public class DiskCache implements BitmapCache {

    private static DiskCache mDiskCache;

    //缓存路径
    private String mCacheDir = "Image";

    private static final int MB = 1024 * 1024;

    private DiskLruCache mDiskLruCache;

    private DiskCache(Context context) {

        initDiskCache(context, 1);
    }

    private void initDiskCache(Context context, int appVersion) {

        File directory = getDiskCache(mCacheDir, context);
        if (!directory.exists())
            directory.mkdir();

        try {
            mDiskLruCache = DiskLruCache.open(directory, appVersion, 1, 50 * MB);


        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private File getDiskCache(String mCacheDir, Context context) {

        String cachePath = "";

        return new File(Environment.getExternalStorageDirectory(), mCacheDir);
    }

    @Override
    public void put(BitmapRequest request, Bitmap bitmap) {
        DiskLruCache.Editor editor = null;
        OutputStream outputStream = null;

        try {
            editor = mDiskLruCache.edit(request.getImageUriMD5());

            outputStream = editor.newOutputStream(0);

            if (persisBitmap2Disk(bitmap, outputStream)) {
                editor.commit();
            } else {
                editor.abort();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private boolean persisBitmap2Disk(Bitmap bitmap, OutputStream outputStream) {
        BufferedOutputStream bos = new BufferedOutputStream(outputStream);
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
        try {
            bos.flush();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } finally {
            IOUtil.closeQuietly(bos);

        }

        return true;
    }

    @Override
    public Bitmap get(BitmapRequest request) {

        try {
            DiskLruCache.Snapshot snapshot = mDiskLruCache.get(request.getImageUriMD5());
            if (snapshot != null) {

                InputStream inputStream = snapshot.getInputStream(0);

                return BitmapFactory.decodeStream(inputStream);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public void remove(BitmapRequest request) {
        try {
            mDiskLruCache.remove(request.getImageUriMD5());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static DiskCache getInstance(Context context) {
        if (mDiskCache == null) {

            synchronized (DiskCache.class) {
                if (mDiskCache == null) {
                    mDiskCache = new DiskCache(context);
                }
            }
        }

        return mDiskCache;
    }
}
