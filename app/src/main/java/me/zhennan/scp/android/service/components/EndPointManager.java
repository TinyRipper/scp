package me.zhennan.scp.android.service.components;

import android.content.Context;
import android.os.Bundle;

import com.facebook.stetho.okhttp.StethoInterceptor;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.zhennan.scp.android.Consts;
import me.zhennan.scp.android.usecase.endpoint.EndPoint;
import me.zhennan.scp.android.usecase.endpoint.EndPointImpl;
import me.zhennan.scp.android.utilities.MetaDataUtil;

/**
 * Created by zhangzhennan on 15/5/8.
 */
public class EndPointManager implements EndPoint.QueryDelegate {

    private Context context;
    private JSONObject config;

    public EndPointManager(Context context, JSONObject config){
        validConfiguration(config);
        this.config = config;
        this.context = context;
    }

    private OkHttpClient client;
    protected OkHttpClient getClient(){
        if(null == client){
            client = new OkHttpClient();
            client.networkInterceptors().add(new StethoInterceptor());
        }
        return client;
    }

    private Map<String, EndPoint> map;
    protected Map<String ,EndPoint> getMap(){
        if(null == map){
            map = new HashMap<>();
        }
        return map;
    }

    public EndPoint getEndPoint(String lang) {

        if (getMap().containsKey(lang)) {
            return getMap().get(lang);
        } else {
            JSONObject endpoints = config.optJSONObject("endpoints");
            JSONObject config = null;

            String systemLang = AppConfigurationManager.getInstance(context).getLanguage();
            if (endpoints.has(lang)) {
                config = endpoints.optJSONObject(lang);
            } else if (endpoints.has(systemLang)) {
                lang = systemLang;
                config = endpoints.optJSONObject(systemLang);
            } else {
                Bundle metadata = MetaDataUtil.getMetaData(context);
                if (null != metadata) {
                    lang = metadata.getString(Consts.Meta.DEFAULT_ENDPOINT);
                    if (endpoints.has(lang)) {
                        config = endpoints.optJSONObject(lang);
                    }
                }
            }

            if (validEndPointConfiguration(config)) {
                String url = config.optString("url");
                JSONArray series = config.optJSONArray("database");
                List<EndPoint.Series> list = new ArrayList<>();
                for(int i = 0; i < series.length(); i++){
                    JSONObject item = series.optJSONObject(i);
                    EndPoint.Series object = new EndPoint.Series();
                    object.url = item.optString("url");
                    object.label = item.optString("label");
                    list.add(object);
                }

                EndPoint endPoint = new EndPointImpl(this, url, list);
                getMap().put(lang, endPoint);
                return endPoint;
            } else {
                return null;
            }
        }
    }



    @Override
    public String get(String url, String... params) throws BadRequestException {

        Request request = new Request.Builder()
                .url(url).build();
        try {
            Response response = getClient().newCall(request).execute();

            if(response.isSuccessful()){
                return response.body().string();
            }else{
                throw new BadRequestException(response.code(), response.message());
            }
        }catch (IOException e){
            throw new BadRequestException(-1, e.getMessage());
        }
    }

    @Override
    public String post(String url, String... params) throws BadRequestException {
        return null;
    }

    /**
     * configuration validation
     * @param configuration
     * @return
     */
    protected boolean validConfiguration(JSONObject configuration){
        return true;
    }

    /**
     * endpoint config validation
     * @param config
     * @return
     */
    protected boolean validEndPointConfiguration(JSONObject config){
        return true;
    }
}
