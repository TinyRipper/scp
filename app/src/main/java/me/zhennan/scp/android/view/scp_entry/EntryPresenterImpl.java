package me.zhennan.scp.android.view.scp_entry;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.util.Log;

import me.zhennan.scp.android.service.CoreService;
import me.zhennan.scp.android.service.CoreServiceBinder;
import me.zhennan.scp.android.usecase.UseCaseCallback;
import me.zhennan.scp.android.usecase.endpoint.EndPoint;

/**
 * Created by zhangzhennan on 15/5/11.
 */
public class EntryPresenterImpl implements EntryMVP.EntryPresenter {


    private EntryMVP.EntryPresenterView view;
    @Override
    public EntryMVP.EntryPresenterView getView() {
        return view;
    }

    @Override
    public void setView(EntryMVP.EntryPresenterView ref) {
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
                        onConnected((CoreServiceBinder) service);
                    }
                }

                @Override
                public void onServiceDisconnected(ComponentName name) {

                }
            };
        }
        return connection;
    }

    protected void onConnected(CoreServiceBinder service){
        EndPoint endPoint = service.getEndPoint();
        if(null != endPoint){
            endPoint.readEntry(getView().getEntryId(), new UseCaseCallback() {
                @Override
                public void onComplete(String html) {
                    getView().showEntryContent(html);
                }

                @Override
                public void onFailed(int error, String message) {
                    Log.e("scp", "("+error+")"+message);
                }
            });
        }
    }
}
