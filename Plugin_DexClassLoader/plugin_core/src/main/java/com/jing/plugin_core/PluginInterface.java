package com.jing.plugin_core;

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;

/**
 * author: 陈永镜 .
 * date: 2017/3/29 .
 * email: jing20071201@qq.com
 * <p>
 * introduce: 插件化开发的标准，通过该标准加载activity
 */
public interface PluginInterface {

    void attach(Activity proxyActivity);

    public void onCreate(Bundle saveInstanceState);

    void onStart();

    void onResume();

    void onPause();

    void onStop();

    void onDestroy();

    void onSaveInstanceState(Bundle outState);

    boolean onTouchEvent(MotionEvent event);

    void onBackPressed();

}
