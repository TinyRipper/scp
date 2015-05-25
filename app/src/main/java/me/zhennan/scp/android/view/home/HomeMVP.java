package me.zhennan.scp.android.view.home;

import android.content.ServiceConnection;
import android.widget.ListAdapter;

import me.zhennan.toolkit.android.design_pattern.mvp.MVP;

/**
 * Created by zhangzhennan on 15/5/7.
 */
public interface HomeMVP {

    interface HomePresenter extends MVP.Presenter<HomePresenterView>{
        ServiceConnection getServiceConnection();

        ListAdapter getListAdapter();
    }

    interface HomePresenterView extends MVP.PresenterView<HomePresenter>{
        void pressDrawer();

        void showServiceConnectionFailedErrorMessage();
    }

}
