package com.jing.skin;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.jing.skin_lib.SkinActivity;
import com.jing.skin_lib.SkinManager;

import java.io.File;

public class MainActivity extends SkinActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void jump(View view) {
        String path = "/mnt/sdcard/skin.apk";
        File file = new File(path);

        Log.w(getClass().getName(), "file=" + file.exists());

        SkinManager.getInstance().loadSkin(path);
        update();
    }

}
