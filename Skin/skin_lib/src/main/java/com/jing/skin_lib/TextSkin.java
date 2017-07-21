package com.jing.skin_lib;

import android.view.View;
import android.widget.TextView;

/**
 * author: 陈永镜 .
 * date: 2017/4/18 .
 * email: jing20071201@qq.com
 * <p>
 * introduce:
 */
public class TextSkin extends SkinInterface {


    public TextSkin(String attrName, int refId, String attrValueName, String attrType) {
        super(attrName, refId, attrValueName, attrType);
    }

    @Override
    public void apply(View view) {

        if (view instanceof TextView) {

            TextView textView = (TextView) view;
            textView.setTextColor(SkinManager.getInstance().getColor(refId));
        }

    }
}
