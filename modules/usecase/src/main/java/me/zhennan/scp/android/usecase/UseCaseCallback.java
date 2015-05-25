package me.zhennan.scp.android.usecase;

/**
 * Created by zhangzhennan on 15/5/8.
 */
public interface UseCaseCallback {
    void onComplete(String html);
    void onFailed(int error, String message);
}
