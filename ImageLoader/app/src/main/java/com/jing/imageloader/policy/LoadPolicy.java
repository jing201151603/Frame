package com.jing.imageloader.policy;

import com.jing.imageloader.request.BitmapRequest;

/**
 * author: 陈永镜 .
 * date: 2017/6/21 .
 * email: jing20071201@qq.com
 * <p>
 * introduce:
 */
public interface LoadPolicy {

    /**
     * 两个BitmapRequest优先级的比较
     *
     * @param request1
     * @param request2
     * @return
     */
    int compareto(BitmapRequest request1, BitmapRequest request2);

}
