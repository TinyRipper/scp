package me.zhennan.scp.android.view.splash;

import android.content.ServiceConnection;
import android.os.Bundle;

import me.zhennan.toolkit.android.design_pattern.mvp.MVP;


/**
 * Created by zhangzhennan on 15/5/7.
 */
public interface SplashMVP {

    interface SplashPresenter extends MVP.Presenter<SplashPresenterView>{

        ServiceConnection getServiceConnection();

    }

    interface SplashPresenterView extends MVP.PresenterView<SplashPresenter>{

        void navigateToHomeWithExtras(Bundle extras);

    }

}
