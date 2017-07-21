package com.jing.imageloader.loader;

import android.graphics.Bitmap;
import android.widget.ImageView;

import com.jing.imageloader.cache.BitmapCache;
import com.jing.imageloader.config.DisaplayConfig;
import com.jing.imageloader.request.BitmapRequest;

/**
 * author: 陈永镜 .
 * date: 2017/6/21 .
 * email: jing20071201@qq.com
 * <p>
 * introduce:
 */
public abstract class AbstractLoader implements Loader {

    //获取缓存策略
    private BitmapCache bitmapCache = SimpleImageLoader.getInstance().getConfig().getBitmapCache();

    //获取显示策略
//    private DisaplayConfig disaplayConfig = SimpleImageLoader.getInstance().getConfig().getDisaplayConfig();

    @Override
    public void loadImage(BitmapRequest request) {
        //从缓存中获取bitmap
        Bitmap bitmap = bitmapCache.get(request);

        if (bitmap == null) {
            //显示默认图片
            showLoadingImage(request);

            bitmap = onLoad(request);

            cacheBitmap(request, bitmap);

        }
        deliveryToUiThread(request, bitmap);

    }

    private void deliveryToUiThread(final BitmapRequest request, final Bitmap bitmap) {

        ImageView imageView = request.getImageView();
        if (imageView != null) {

            imageView.post(new Runnable() {
                @Override
                public void run() {
                    updateImageView(request, bitmap);
                }
            });
        }

    }

    private void updateImageView(BitmapRequest request, Bitmap bitmap) {
        ImageView imageView = request.getImageView();

        if (bitmap != null && imageView.getTag().equals(request.getImageUrl())) {
            imageView.setImageBitmap(bitmap);
        }

        if (bitmap == null && request.getDisaplayConfig() != null && request.getDisaplayConfig().failImage != -1) {
            imageView.setImageResource(request.getDisaplayConfig().failImage);
        }

        if (request.imageListener != null) {
            request.imageListener.onComplete(imageView, bitmap, request.getImageUrl());
        }

    }

    private void cacheBitmap(BitmapRequest request, Bitmap bitmap) {

        if (request != null && bitmap != null) {
            synchronized (AbstractLoader.class) {
                bitmapCache.put(request, bitmap);
            }
        }

    }

    protected abstract Bitmap onLoad(BitmapRequest request);

    private void showLoadingImage(final BitmapRequest request) {

        if (hasLoadingPlaceHolder(request.getDisaplayConfig())) {
            final ImageView imageView = request.getImageView();
            if (imageView != null) {

                imageView.post(new Runnable() {
                    @Override
                    public void run() {
                        imageView.setImageResource(request.getDisaplayConfig().loadingImage);
                    }
                });
            }

        }

    }

    private boolean hasLoadingPlaceHolder(DisaplayConfig disaplayConfig) {

        return disaplayConfig != null && disaplayConfig.loadingImage > 0;
    }
}
