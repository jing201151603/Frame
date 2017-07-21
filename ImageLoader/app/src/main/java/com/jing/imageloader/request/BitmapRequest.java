package com.jing.imageloader.request;

import android.support.annotation.NonNull;
import android.widget.ImageView;

import com.jing.imageloader.config.DisaplayConfig;
import com.jing.imageloader.loader.SimpleImageLoader;
import com.jing.imageloader.policy.LoadPolicy;
import com.jing.imageloader.utils.MD5Utils;

import java.lang.ref.SoftReference;

/**
 * author: 陈永镜 .
 * date: 2017/6/21 .
 * email: jing20071201@qq.com
 * <p>
 * introduce:请求队列的排序
 */
public class BitmapRequest implements Comparable<BitmapRequest> {

    //持有imageView的软引用
    private SoftReference<ImageView> imageViewSoft;

    //图片路径
    private String imageUrl;

    //MD5的图片路径
    private String imageUriMD5;

    /**
     * 图片下载完成的监听
     */
    public SimpleImageLoader.ImageListener imageListener;

    private DisaplayConfig disaplayConfig;

    public BitmapRequest(ImageView imageView, String imageUrl, DisaplayConfig disaplayConfig,
                         SimpleImageLoader.ImageListener imageListener) {
        this.imageViewSoft = new SoftReference<ImageView>(imageView);
        imageView.setTag(imageUrl);//使用图片路径作为imageView的tag
        this.imageUrl = imageUrl;
        this.imageUriMD5 = MD5Utils.toMD5(imageUrl);
        if (disaplayConfig != null)
            this.disaplayConfig = disaplayConfig;
        this.imageListener = imageListener;

    }


    private LoadPolicy loadPolicy = SimpleImageLoader.getInstance().getConfig().getLoadPolicy();//加载策略

    private int serialNo;//优先级编号

    public int getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(int serialNo) {
        this.serialNo = serialNo;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BitmapRequest that = (BitmapRequest) o;

        if (serialNo != that.serialNo) return false;
        return loadPolicy != null ? loadPolicy.equals(that.loadPolicy) : that.loadPolicy == null;

    }

    @Override
    public int hashCode() {
        int result = loadPolicy != null ? loadPolicy.hashCode() : 0;
        result = 31 * result + serialNo;
        return result;
    }

    public ImageView getImageView() {
        return imageViewSoft.get();
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getImageUriMD5() {
        return imageUriMD5;
    }

    public DisaplayConfig getDisaplayConfig() {
        return disaplayConfig;
    }

    public LoadPolicy getLoadPolicy() {
        return loadPolicy;
    }
    /**
     * 处理优先级
     *
     * @return
     */
    @Override
    public int compareTo(@NonNull BitmapRequest o) {
        return loadPolicy.compareto(o,this);

    }
}
