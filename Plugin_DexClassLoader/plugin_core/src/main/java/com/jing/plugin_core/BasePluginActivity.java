package com.jing.plugin_core;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.StringDef;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

/**
 * author: 陈永镜 .
 * date: 2017/3/29 .
 * email: jing20071201@qq.com
 * <p>
 * introduce:
 */
public class BasePluginActivity extends Activity implements PluginInterface {

    protected Activity that;

    @Override
    public void attach(Activity proxyActivity) {
        that=proxyActivity;

    }

    @Override
    public void setContentView(View view) {
        that.setContentView(view);
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        that.setContentView(layoutResID);
    }

    @Override
    public void setContentView(View view, ViewGroup.LayoutParams params) {
        that.setContentView(view, params);
    }

    @Override
    public void onCreate(Bundle saveInstanceState) {

    }

    @Override
    public View findViewById(@IdRes int id) {
        return that.findViewById(id);
    }

    @Override
    public Intent getIntent() {
        return that.getIntent();
    }

    @Override
    public ClassLoader getClassLoader() {
        return that.getClassLoader();
    }

    @Override
    public Resources getResources() {
        return that.getResources();
    }

    @NonNull
    @Override
    public LayoutInflater getLayoutInflater() {
        return that.getLayoutInflater();
    }

    @NonNull
    @Override
    public MenuInflater getMenuInflater() {
        return that.getMenuInflater();
    }

    @Override
    public void onStart() {

    }

    @Override
    public SharedPreferences getSharedPreferences(String name, int mode) {
        return that.getSharedPreferences(name, mode);
    }

    @Override
    public Context getApplicationContext() {
        return that.getApplicationContext();
    }

    @Override
    public Window getWindow() {
        return that.getWindow();
    }

    @Override
    public Object getSystemService(String name) {
        return that.getSystemService(name);
    }

    @Override
    public void finish() {
        that.finish();
    }



    @Override
    public void onResume() {

    }

    @Override
    public void onPause() {

    }

    @Override
    public void onStop() {

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {

    }
}
