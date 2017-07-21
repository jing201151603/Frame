package com.jing.skin_lib;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * author: 陈永镜 .
 * date: 2017/4/18 .
 * email: jing20071201@qq.com
 * <p>
 * introduce:
 */
public class SkinManager {

    /**
     * 插件的皮肤资源
     */
    private Resources skinResource;

    private static final SkinManager ourInstance = new SkinManager();

    private Context context;

    private String skinPkg;

    public void init(Context context) {
        this.context = context;
    }

    public static SkinManager getInstance() {
        return ourInstance;
    }

    private SkinManager() {
    }

    public void loadSkin(String path) {

        try {
            //获取插件apk的包名
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageArchiveInfo(path, PackageManager.GET_ACTIVITIES);
            skinPkg = packageInfo.packageName;

            AssetManager assetManager = AssetManager.class.newInstance();
            Method addAssetPath = assetManager.getClass().getMethod("addAssetPath", String.class);
            addAssetPath.invoke(assetManager,path);

            Resources appResource = context.getResources();
            skinResource = new Resources(assetManager, appResource.getDisplayMetrics(), appResource.getConfiguration());

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

    /**
     * 通过该方法获取皮肤资源
     *
     * @param resId
     * @return
     */
    public int getColor(int resId) {
        if (skinResource == null) {
            //直接获取本地apk的资源
            return resId;
        }

        //通过当前ID找对应的名字
        String resName = context.getResources().getResourceEntryName(resId);

        int realId = skinResource.getIdentifier(resName, "color", skinPkg);

        int color = skinResource.getColor(realId);

        return color;
    }

    public Drawable getDrawable(int resId) {
        Drawable drawable = ContextCompat.getDrawable(context, resId);

        if (skinResource == null) return drawable;

        String resName = context.getResources().getResourceEntryName(resId);

        int realId = skinResource.getIdentifier(resName, "drawable", skinPkg);

        Drawable realDrawable = skinResource.getDrawable(realId);

        return realDrawable;
    }

}
