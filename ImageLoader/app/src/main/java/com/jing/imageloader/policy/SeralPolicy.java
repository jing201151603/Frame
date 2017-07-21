package com.jing.imageloader.policy;

import com.jing.imageloader.request.BitmapRequest;

/**
 * author: 陈永镜 .
 * date: 2017/6/21 .
 * email: jing20071201@qq.com
 * <p>
 * introduce:顺序
 */
public class SeralPolicy implements LoadPolicy {
    @Override
    public int compareto(BitmapRequest request1, BitmapRequest request2) {
        return request1.getSerialNo() - request2.getSerialNo();
    }
}
