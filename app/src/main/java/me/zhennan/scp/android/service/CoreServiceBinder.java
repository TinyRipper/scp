package me.zhennan.scp.android.service;

import android.os.IBinder;

import me.zhennan.scp.android.usecase.config.AppConfiguration;
import me.zhennan.scp.android.usecase.endpoint.EndPoint;

/**
 * Created by zhangzhennan on 15/5/7.
 */
public interface CoreServiceBinder extends IBinder {

    AppConfiguration getAppConfiguration();

    EndPoint getEndPoint();
}
