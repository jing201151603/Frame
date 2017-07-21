package com.jing.plugin_core;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.Resources;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import dalvik.system.DexClassLoader;

/**
 * author: 陈永镜 .
 * date: 2017/3/29 .
 * email: jing20071201@qq.com
 * <p>
 * introduce:
 */
public class PluginManager {

    /**
     * 加载外置卡中apk的class文件
     */
    private DexClassLoader dexClassLoader;

    private Context context;

    private Resources resources;

    private static PluginManager instance;

    private PackageInfo packageInfo;

    private PluginManager(Context context) {
        this.context = context;
    }

    public static PluginManager getInstance(Context context) {

        if (instance == null) {
            synchronized (PluginManager.class) {
                if (instance == null) {
                    instance = new PluginManager(context);
                }
            }
        }

        return instance;
    }

    public static PluginManager getInstance() {
        if (instance == null) {
            throw new RuntimeException("必须实现实例化第一个参数的构造方法");
        }
        return instance;
    }

    /**
     * 加载apk文件
     *
     * @param path apk的路径
     */
    public void loadPath(String path) {

        File dexOutFile = context.getDir("dex", Context.MODE_PRIVATE);

        dexClassLoader = new DexClassLoader(path,
                dexOutFile.getAbsolutePath(), null,
                context.getClassLoader());

        try {
            AssetManager assetManager = AssetManager.class.newInstance();
            //反射给assetManager设置路径
            Method addAssetPath=AssetManager.class.getMethod("addAssetPath",String.class);
            addAssetPath.invoke(assetManager,path);
            Resources superResource=context.getResources();

            resources = new Resources(assetManager,superResource.getDisplayMetrics(),superResource.getConfiguration());

            //获取插件中所有的activity的信息
            PackageManager packageManager=context.getPackageManager();
            packageInfo=packageManager.getPackageArchiveInfo(path,PackageManager.GET_ACTIVITIES);

        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }


    }

    public DexClassLoader getDexClassLoader() {
        return dexClassLoader;
    }

    public Resources getResources() {
        return resources;
    }

    public PackageInfo getPackageInfo() {
        return packageInfo;
    }
}
