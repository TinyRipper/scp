package me.zhennan.scp.android.utilities;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;

/**
 * Created by zhangzhennan on 15/5/8.
 */
public class MetaDataUtil {

    static public Bundle getMetaData(Context context){
        try{
            ApplicationInfo ai = context.getPackageManager().getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
            return ai.metaData;
        }catch (Exception e){
            return null;
        }
    }
}
