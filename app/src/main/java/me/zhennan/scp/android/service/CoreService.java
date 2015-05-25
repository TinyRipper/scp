package me.zhennan.scp.android.service;

import android.app.Service;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Binder;
import android.os.IBinder;

import org.json.JSONObject;

import java.io.InputStream;
import java.util.Locale;

import me.zhennan.scp.android.service.components.EndPointManager;
import me.zhennan.scp.android.usecase.config.AppConfiguration;
import me.zhennan.scp.android.usecase.endpoint.EndPoint;
import me.zhennan.scp.android.service.components.AppConfigurationManager;

/**
 * Created by zhangzhennan on 15/5/7.
 */
public class CoreService extends Service {

    private CoreBinder binder;

    @Override
    public IBinder onBind(Intent intent) {
        if(null == binder){
            binder = new CoreBinder();
        }
        return binder;
    }

    private EndPointManager endPointManager;
    protected EndPointManager getEndPointManagerInstance(){
        if(null == endPointManager){
            try {
                InputStream is = getAssets().open("endpoints.json", AssetManager.ACCESS_BUFFER);
                byte[] bytes = new byte[is.available()];
                is.read(bytes);
                is.close();
                String raw = new String(bytes, "UTF-8");
                JSONObject json = new JSONObject(raw);
                endPointManager = new EndPointManager(this, json);
            }catch (Throwable e){

            }
        }
        return endPointManager;
    }

    private AppConfiguration appConfiguration;
    protected AppConfiguration getAppConfigurationInstance(){
        if(null == appConfiguration){
            appConfiguration = AppConfigurationManager.getInstance(this);
        }
        return appConfiguration;
    }

    class CoreBinder extends Binder implements CoreServiceBinder{

        @Override
        public EndPoint getEndPoint() {
            String lang = Locale.CHINA.getLanguage();
//            String lang = Locale.getDefault().getLanguage();
            return getEndPointManagerInstance().getEndPoint(lang);
        }

        @Override
        public AppConfiguration getAppConfiguration() {
            return getAppConfigurationInstance();
        }
    }

}
