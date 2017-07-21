package com.jing.pluginapk;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.jing.plugin_core.BasePluginActivity;

public class MainActivity extends BasePluginActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.test).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(that, "吐司吐司", Toast.LENGTH_SHORT).show();

            }
        });


    }

}
