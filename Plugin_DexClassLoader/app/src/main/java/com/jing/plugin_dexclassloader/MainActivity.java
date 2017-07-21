package com.jing.plugin_dexclassloader;

import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.jing.plugin_core.PluginManager;
import com.jing.plugin_core.ProxyActivity;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        PluginManager.getInstance(this);

    }

    public void load(View view) {
        PluginManager.getInstance().loadPath("/mnt/sdcard/plugin.apk");
    }

    public void jump(View view) {
        Intent intent = new Intent(this, ProxyActivity.class);
        //插件apk中的启动activity
        intent.putExtra("className", PluginManager.getInstance().getPackageInfo().activities[0].name);

        startActivity(intent);
    }

}
