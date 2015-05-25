package me.zhennan.scp.android.view.splash;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.IBinder;

import me.zhennan.scp.android.service.CoreServiceBinder;

/**
 * Created by zhangzhennan on 15/5/7.
 */
public class SplashPresenterImpl implements SplashMVP.SplashPresenter {

    private SplashMVP.SplashPresenterView view;

    @Override
    public SplashMVP.SplashPresenterView getView() {
        return view;
    }

    @Override
    public void setView(SplashMVP.SplashPresenterView ref) {
        view = ref;
    }

    private ServiceConnection connection;

    @Override
    public ServiceConnection getServiceConnection() {
        if(null == connection){
            connection = new ServiceConnection() {
                @Override
                public void onServiceConnected(ComponentName name, IBinder service) {
                    if(service instanceof CoreServiceBinder){
                        onServiceConnection((CoreServiceBinder) service);
                    }else{
                        getView().navigateToHomeWithExtras(null);
                    }
                }

                @Override
                public void onServiceDisconnected(ComponentName name) {

                }
            };
        }
        return connection;
    }

    public void onServiceConnection(CoreServiceBinder binder){
        getView().navigateToHomeWithExtras(null);
    }
}
