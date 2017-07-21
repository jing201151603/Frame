package com.jing.plugin_core;

import android.app.Activity;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * author: 陈永镜 .
 * date: 2017/3/29 .
 * email: jing20071201@qq.com
 * <p>
 * introduce:
 */
public class ProxyActivity extends Activity {

    //替换插件apk中的activity的全类名
    String className;
    private PluginInterface pluginInterface;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        className = getIntent().getStringExtra("className");
        lauchActivity();

    }

    /**
     * 通过反射加载外置卡的apk的activity的class文件
     */
    private void lauchActivity() {

        try {
            //通过dexClassLoader加载外置卡的class
            Class<?> loadClass = PluginManager.getInstance().getDexClassLoader().loadClass(className);

            Constructor constructor = loadClass.getConstructor(new Class[]{});//获取空参数的构造方法
            //发射得到activity的实例
            Object instance = constructor.newInstance(new Object[]{});

            //利用标准的接口将插件apk中的class强转成PluginInterface
            pluginInterface = (PluginInterface) instance;
            pluginInterface.attach(this);

            Bundle bundle = new Bundle();

            pluginInterface.onCreate(bundle);

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void onStart() {
        pluginInterface.onStart();
        super.onStart();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        pluginInterface.onDestroy();
    }

    /**
     * 必须重写该方法
     * 获取插件的所有的资源
     *
     * @return
     */
    @Override
    public Resources getResources() {
        return PluginManager.getInstance().getResources();
    }
}
