package com.jing.imageloader.loader;

import com.jing.imageloader.request.BitmapRequest;

/**
 * author: 陈永镜 .
 * date: 2017/6/21 .
 * email: jing20071201@qq.com
 * <p>
 * introduce:
 */
public interface Loader {

    /**
     * 加载图片
     *
     * @param request
     */
    void loadImage(BitmapRequest request);

}
