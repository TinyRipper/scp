package me.zhennan.scp.android.view.scp_database;

import android.content.ServiceConnection;
import android.widget.ListAdapter;

import me.zhennan.toolkit.android.design_pattern.mvp.MVP;
import me.zhennan.toolkit.android.multitypelistadapter.MultiTypeListAdapter;

/**
 * Created by zhangzhennan on 15/5/7.
 */
public interface DatabaseMVP {

    interface DatabasePresenter extends MVP.Presenter<DatabasePresenterView>{
        MultiTypeListAdapter getListAdapter();

        ServiceConnection getServiceConnection();
    }

    interface DatabasePresenterView extends MVP.PresenterView<DatabasePresenter>{
        
    }
}
