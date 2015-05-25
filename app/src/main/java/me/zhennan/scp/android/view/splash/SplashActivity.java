package me.zhennan.scp.android.view.splash;

import android.content.Intent;
import android.content.ServiceConnection;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import me.zhennan.scp.android.R;
import me.zhennan.scp.android.base.BaseAppCompatActivity;
import me.zhennan.scp.android.service.CoreService;
import me.zhennan.scp.android.view.home.HomeActivity;
import me.zhennan.scp.android.view.scp_database.DatabaseActivity;


public class SplashActivity extends BaseAppCompatActivity implements SplashMVP.SplashPresenterView {


    private SplashMVP.SplashPresenter presenter;

    @Override
    public SplashMVP.SplashPresenter getPresenter() {
        if(null == presenter){
            presenter = new SplashPresenterImpl();
            presenter.setView(this);
        }
        return presenter;
    }

    @Override
    public void setPresenter(SplashMVP.SplashPresenter ref) {
        presenter = ref;
    }

    private ServiceConnection connection;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        ActionBar ab = getSupportActionBar();
        if(null != ab){
            ab.hide();
        }

        // get connection reference
        connection = getPresenter().getServiceConnection();
        Intent intent = new Intent(this, CoreService.class);
        bindService(intent, connection, BIND_AUTO_CREATE);
        startService(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(null != connection){
            unbindService(connection);
        }
    }

    @Override
    public void navigateToHomeWithExtras(Bundle extras) {
//        Intent intent = new Intent(this, HomeActivity.class);
        Intent intent = new Intent(this, DatabaseActivity.class);
        if(null != extras){
            intent.putExtras(extras);
        }

        startActivity(intent);
        finish();
    }

}
