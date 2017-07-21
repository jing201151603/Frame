package com.jing.dao_lib.util;

import android.content.Context;
import android.os.Environment;

/**
 * author: 陈永镜 .
 * date: 2017/3/31 .
 * email: jing20071201@qq.com
 * <p>
 * introduce:
 */
public class FileUtils {

    public static String getRootPath(Context context) {
//        return  "/data/user/0/" + context.getPackageName() + "/jcache";
        return Environment.getExternalStorageDirectory().getAbsolutePath() + "/dao_test";
    }

}
