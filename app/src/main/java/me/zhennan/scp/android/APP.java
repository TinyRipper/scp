package me.zhennan.scp.android;

import android.app.Application;

import com.facebook.stetho.Stetho;

/**
 * Created by zhangzhennan on 15/5/7.
 */
public class APP extends Application {


    @Override
    public void onCreate() {
        super.onCreate();

        Stetho.initialize(Stetho
                .newInitializerBuilder(this)
                .enableWebKitInspector(Stetho.defaultInspectorModulesProvider(this))
                .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
                .build());

    }

}
