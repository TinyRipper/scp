package me.zhennan.scp.android.usecase.endpoint;

import android.content.Context;
import android.os.AsyncTask;
import android.text.TextUtils;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import me.zhennan.scp.android.usecase.UseCaseCallback;

/**
 * Created by zhangzhennan on 15/5/8.
 */
public class EndPointImpl implements EndPoint {

    private String url;
    private QueryDelegate delegate;
    private List<Series> series;

    public EndPointImpl(QueryDelegate delegate, String url, List<Series> series){
        this.url = url;
        if(null != series){
            getSeries().addAll(series);
        }
        setDelegate(delegate);
    }

    public QueryDelegate getDelegate() {
        return delegate;
    }

    public void setDelegate(QueryDelegate delegate) {
        this.delegate = delegate;
    }

    public List<Series> getSeries() {
        if(null == series){
            series = new ArrayList<>();
        }
        return series;
    }

    public String getUrl() {
        return url;
    }

    @Override
    public void readEntry(String entryId, final UseCaseCallback useCaseCallback) {
        if(!TextUtils.isEmpty(entryId)){
            getHtml(getUrl() + entryId, new RequestCallback() {
                @Override
                public void onResult(EndPointResult result) {

                    if(EndPointResult.CODE_SUCCESS == result.code){
                        useCaseCallback.onComplete(result.html);
                    }else{
                        useCaseCallback.onFailed(result.code, result.errorMessage);
                    }
                }
            });
        }else{
            useCaseCallback.onFailed(-1, "empty entry id.");
        }
    }

    @Override
    public void readSeries(int index, final UseCaseCallback useCaseCallback) {
        if(0 <= index && index < getSeries().size()){
            Series series = getSeries().get(index);
            if(null != series){
                getHtml(getUrl() + series.url, new RequestCallback() {
                    @Override
                    public void onResult(EndPointResult result) {
                      if(EndPointResult.CODE_SUCCESS == result.code){
                          useCaseCallback.onComplete(result.html);
                      }else{
                          useCaseCallback.onFailed(result.code, result.errorMessage);
                      }
                    }
                });
            }
        }else{
            useCaseCallback.onFailed(-2, "no series");
        }

    }

    protected void getHtml(String url, final RequestCallback callback){
        new AsyncTask<String, Void, EndPointResult>(){
            @Override
            protected EndPointResult doInBackground(String... params) {
                EndPointResult result = new EndPointResult();
                String url = params[0];
                try {
                    result.html = getDelegate().get(url);
                    result.code = EndPointResult.CODE_SUCCESS;
                }catch (QueryDelegate.BadRequestException e){
                    result.code = e.code;
                    result.errorMessage = e.getMessage();
                }
                return result;
            }

            @Override
            protected void onPostExecute(EndPointResult result) {
                callback.onResult(result);
            }
        }.execute(url);
    }

    interface RequestCallback{
        void onResult(EndPointResult result);
    }
}
