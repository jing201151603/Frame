package com.jing.skin_lib;

import android.content.Context;
import android.support.v4.view.LayoutInflaterFactory;
import android.util.AttributeSet;
import android.view.View;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * author: 陈永镜 .
 * date: 2017/4/18 .
 * email: jing20071201@qq.com
 * <p>
 * introduce: 用于解析XML
 */
public class SkinFactory implements LayoutInflaterFactory {

    private static final String[] prefixList = {
            "android.widget.",
            "android.view.",
            "android.webkit"
    };

    /**
     * 保存需要换肤的控件
     */
    private Map<View, SkinItem> map = new HashMap<>();

    @Override
    public View onCreateView(View parent, String name, Context context, AttributeSet attrs) {
        View view = null;
        /**
         * 系统控件
         */
        if (name.indexOf(".") == -1) {
            for (String prix : prefixList) {
                view = createView(context, attrs, prix + name);
                if (view != null) break;
            }
        } else {
            view = createView(context, attrs, name);
        }

        if (view != null) {
            parseSkinView(view, context, attrs);
        }

        return view;
    }

    private void parseSkinView(View view, Context context, AttributeSet attrs) {

        List<SkinInterface> attrList = new ArrayList<>();
        int count = attrs.getAttributeCount();
        for (int i = 0; i < count; i++) {
            //获取属性名
            String attrName = attrs.getAttributeName(i);
            String attrValue = attrs.getAttributeValue(i);

            int id = -1;
            String entryName;
            String typeName;

            SkinInterface skinInterface = null;
            if ("background".equals(attrName)) {

                id = Integer.parseInt(attrValue.substring(1));
                entryName = context.getResources().getResourceEntryName(id);
                typeName = context.getResources().getResourceTypeName(id);
                skinInterface = new BackgroundSkin(attrName, id, entryName, typeName);
            } else if ("textColor".equals(attrName)) {

                id = Integer.parseInt(attrValue.substring(1));
                entryName = context.getResources().getResourceEntryName(id);
                typeName = context.getResources().getResourceTypeName(id);
                skinInterface = new TextSkin(attrName, id, entryName, typeName);
            }

            if (skinInterface != null) attrList.add(skinInterface);

        }

        SkinItem skinItem = new SkinItem(attrList, view);
        map.put(view, skinItem);
        //执行换肤操作
//        skinItem.apply();

    }

    private View createView(Context context, AttributeSet attrs, String name) {
        try {
            //实例化控件
            Class clazz = context.getClassLoader().loadClass(name);
            Constructor<? extends View> constructor =
                    clazz.getConstructor(new Class[]{Context.class, AttributeSet.class});
            constructor.setAccessible(true);


            return constructor.newInstance(context, attrs);

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

        return null;
    }

    public void update() {
        for (View view : map.keySet()) {
            if (view == null) continue;
            map.get(view).apply();
        }
    }

    static class SkinItem {
        /**
         * 皮肤资源
         */
        public List<SkinInterface> attrList;
        /**
         * 需要换肤的控件
         */
        public View view;

        public SkinItem(List<SkinInterface> attrList, View view) {
            this.attrList = attrList;
            this.view = view;
        }

        public void apply() {
            for (SkinInterface skinInterface : attrList) {
                skinInterface.apply(view);
            }
        }

    }

}
