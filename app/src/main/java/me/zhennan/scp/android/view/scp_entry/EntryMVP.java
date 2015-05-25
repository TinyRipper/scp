package me.zhennan.scp.android.view.scp_entry;

import android.content.ServiceConnection;

import me.zhennan.toolkit.android.design_pattern.mvp.MVP;

/**
 * Created by zhangzhennan on 15/5/11.
 */
public interface EntryMVP {

    interface EntryPresenter extends MVP.Presenter<EntryPresenterView>{
        ServiceConnection getServiceConnection();
    }

    interface EntryPresenterView extends MVP.PresenterView<EntryPresenter>{
        void showEntryContent(String html);

        String getEntryId();
    }
}
