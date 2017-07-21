package com.jing.skin_lib;

import android.view.View;

/**
 * author: 陈永镜 .
 * date: 2017/4/18 .
 * email: jing20071201@qq.com
 * <p>
 * introduce:
 */
public abstract class SkinInterface {

    /**
     * 引用名：background
     */
    String attrName = null;
    /**
     * 引用ID
     */
    int refId=0;

    /**
     * 资源名字
     */
    String attrValueName=null;

    /**
     * 类型名字：color、drawable
     */
    String attrType=null;

    public SkinInterface(String attrName, int refId, String attrValueName, String attrType) {
        this.attrName = attrName;
        this.refId = refId;
        this.attrValueName = attrValueName;
        this.attrType = attrType;
    }

    /**
     * 将皮肤应用到view中
     * @param view
     */
    public abstract void apply(View view);

}
