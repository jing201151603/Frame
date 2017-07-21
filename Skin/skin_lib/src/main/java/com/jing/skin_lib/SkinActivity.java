package com.jing.skin_lib;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.LayoutInflaterCompat;

/**
 * author: 陈永镜 .
 * date: 2017/4/18 .
 * email: jing20071201@qq.com
 * <p>
 * introduce:
 */
public class SkinActivity extends Activity {

    private SkinFactory skinFactory;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SkinManager.getInstance().init(getApplicationContext());

        skinFactory = new SkinFactory();
        LayoutInflaterCompat.setFactory(getLayoutInflater(), skinFactory);

    }

    public void update() {
        skinFactory.update();
    }

}
