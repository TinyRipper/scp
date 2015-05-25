package me.zhennan.toolkit.android.design_pattern.mvp;

/**
 * Created by zhangzhennan on 15/5/7.
 */
public interface MVP {

    /**
     * Presenter is a proxy to handle interaction between user and PresenterView
     * @param <T>
     */
    interface Presenter<T extends PresenterView>{
        T getView();
        void setView(T ref);

    }

    /**
     * PresenterView is abstract Interface for user interactive with
     * @param <T>
     */
    interface PresenterView<T extends Presenter>{
        T getPresenter();
        void setPresenter(T ref);
    }
}
