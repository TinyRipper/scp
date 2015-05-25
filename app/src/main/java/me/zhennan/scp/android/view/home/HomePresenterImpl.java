package me.zhennan.scp.android.view.home;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.database.DataSetObserver;
import android.os.IBinder;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.WrapperListAdapter;

import me.zhennan.scp.android.service.CoreServiceBinder;

/**
 * Created by zhangzhennan on 15/5/7.
 */
public class HomePresenterImpl implements HomeMVP.HomePresenter {

    private HomeMVP.HomePresenterView view;

    @Override
    public HomeMVP.HomePresenterView getView() {
        return view;
    }

    @Override
    public void setView(HomeMVP.HomePresenterView ref) {
        view = ref;
    }

    private ListAdapter adapter;

    @Override
    public ListAdapter getListAdapter() {
        if(null == adapter){

        }
        return adapter;
    }

    private ServiceConnection connection;

    @Override
    public ServiceConnection getServiceConnection() {
        if(null == connection){
            connection = new ServiceConnection() {
                @Override
                public void onServiceConnected(ComponentName name, IBinder service) {
                    if(service instanceof CoreServiceBinder){
                        onConnected((CoreServiceBinder)service);
                    }else{
                        getView().showServiceConnectionFailedErrorMessage();
                    }
                }

                @Override
                public void onServiceDisconnected(ComponentName name) {

                }
            };
        }
        return connection;
    }

    void onConnected(CoreServiceBinder binder){

    }

}
