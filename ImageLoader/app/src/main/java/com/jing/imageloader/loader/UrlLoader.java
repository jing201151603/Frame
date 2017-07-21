package com.jing.imageloader.loader;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;

import com.jing.imageloader.request.BitmapRequest;
import com.jing.imageloader.utils.BitmapDecoder;
import com.jing.imageloader.utils.ImageViewHelper;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * author: 陈永镜 .
 * date: 2017/6/21 .
 * email: jing20071201@qq.com
 * <p>
 * introduce:
 */
public class UrlLoader extends AbstractLoader {

    @Override
    protected Bitmap onLoad(final BitmapRequest request) {

        downloadImgByUrl(request.getImageUrl(), getCache(request.getImageUriMD5()));

        final BitmapDecoder bitmapDecoder = new BitmapDecoder() {
            @Override
            public Bitmap decodeBitmapWithOption(BitmapFactory.Options options) {

                return BitmapFactory.decodeFile(getCache(request.getImageUriMD5()).getAbsolutePath(), options);
            }
        };
        return bitmapDecoder.decodeBitmap(ImageViewHelper.getImageViewWidth
                (request.getImageView()), ImageViewHelper.getImageViewHeight
                (request.getImageView()));

    }

    public static boolean downloadImgByUrl(String urlStr, File file) {
        FileOutputStream fos = null;
        InputStream is = null;
        try {
            URL url = new URL(urlStr);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            is = conn.getInputStream();
            fos = new FileOutputStream(file);
            byte[] buf = new byte[512];
            int len = 0;
            while ((len = is.read(buf)) != -1) {
                fos.write(buf, 0, len);
            }
            fos.flush();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (is != null)
                    is.close();
            } catch (IOException e) {
            }

            try {
                if (fos != null)
                    fos.close();
            } catch (IOException e) {
            }
        }

        return false;

    }

    private File getCache(String unipue) {
        File file = new File(Environment.getExternalStorageDirectory(), "ImageLoader");
        if (!file.exists()) {
            file.mkdir();
        }
        return new File(file, unipue);
    }
}
