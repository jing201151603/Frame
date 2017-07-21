package com.jing.skin_lib;

import android.view.View;

/**
 * author: 陈永镜 .
 * date: 2017/4/18 .
 * email: jing20071201@qq.com
 * <p>
 * introduce:
 */
public class BackgroundSkin extends SkinInterface {

    public BackgroundSkin(String attrName, int refId, String attrValueName, String attrType) {
        super(attrName, refId, attrValueName, attrType);
    }

    @Override
    public void apply(View view) {
        if ("color".equals(attrType)) {
            view.setBackgroundColor(SkinManager.getInstance().getColor(refId));
        } else if ("drawable".equals(attrType)) {
            view.setBackgroundDrawable(SkinManager.getInstance().getDrawable(refId));
        }
    }
}
