package me.zhennan.scp.android.service.components;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import java.util.Locale;

import me.zhennan.scp.android.usecase.config.AppConfiguration;

/**
 * Created by zhangzhennan on 15/5/8.
 */
public class AppConfigurationManager implements AppConfiguration {

    static private AppConfiguration instance;

    final static private String SHARED_PREFERENCE_NAME = "appConfiguration";

    static public AppConfiguration getInstance(Context context){
        if(null == instance){
            instance = new AppConfigurationManager(context.getApplicationContext());
        }
        return instance;
    }

    private Context context;

    public AppConfigurationManager(Context context){
        this.context = context;
    }

    protected SharedPreferences sharedPreferences;
    protected SharedPreferences getSharedPreferences(){
        if(null == sharedPreferences){
            sharedPreferences = context.getSharedPreferences(SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE);
        }
        return sharedPreferences;
    }

    public String getDefaultLanguage(){
        return Locale.getDefault().getLanguage();
    }

    @Override
    public String getLanguage() {
        return getSharedPreferences().getString("lang", getDefaultLanguage());
    }

    @Override
    public void setLanguage(String lang) {
        if(!TextUtils.isEmpty(lang)) {
            getSharedPreferences().edit().putString("lang", lang).apply();
        }else{
            getSharedPreferences().edit().remove("lang").apply();
        }
    }
}
