package me.zhennan.scp.android.view.home;

import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.widget.ListView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import me.zhennan.scp.android.R;
import me.zhennan.scp.android.base.BaseAppCompatActivity;
import me.zhennan.scp.android.service.CoreService;

/**
 * Created by zhangzhennan on 15/5/7.
 */
public class HomeActivity extends BaseAppCompatActivity implements HomeMVP.HomePresenterView {

    private HomeMVP.HomePresenter presenter;

    @Override
    public HomeMVP.HomePresenter getPresenter() {
        if(null == presenter){
            presenter = new HomePresenterImpl();
            presenter.setView(this);
        }
        return presenter;
    }

    @Override
    public void setPresenter(HomeMVP.HomePresenter ref) {
        presenter = ref;
    }

    private ServiceConnection connection;

    @InjectView(R.id.list_view)
    protected ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.inject(this);

        // connection
        connection = getPresenter().getServiceConnection();
        Intent intent = new Intent(this, CoreService.class);
        bindService(intent, connection, BIND_AUTO_CREATE);

        // list adapter
        listView.setAdapter(getPresenter().getListAdapter());

//        getPresenter().getListAdapter();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(null != connection){
            unbindService(connection);
        }
    }

    @Override
    public void pressDrawer() {

    }

    @Override
    public void showServiceConnectionFailedErrorMessage() {
        new AlertDialog.Builder(this)
                .setMessage(R.string.e_service_connection)
                .show();
    }
}
