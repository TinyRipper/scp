package me.zhennan.scp.android.view.scp_entry;

import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.text.Html;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import butterknife.ButterKnife;
import butterknife.InjectView;
import me.zhennan.scp.android.R;
import me.zhennan.scp.android.base.BaseAppCompatActivity;
import me.zhennan.scp.android.service.CoreService;

/**
 * Created by zhangzhennan on 15/5/11.
 */
public class EntryActivity extends BaseAppCompatActivity implements EntryMVP.EntryPresenterView {

    private EntryMVP.EntryPresenter presenter;

    @Override
    public EntryMVP.EntryPresenter getPresenter() {
        if(null == presenter){
            presenter = new EntryPresenterImpl();
            presenter.setView(this);
        }
        return presenter;
    }

    @Override
    public void setPresenter(EntryMVP.EntryPresenter ref) {
        presenter = ref;
        presenter.setView(this);
    }

    private String entryId;

    @InjectView(R.id.text_view)
    TextView textView;

    private ServiceConnection connection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entry);
        ButterKnife.inject(this);

        ActionBar ab = getSupportActionBar();
        if(null != ab){
            ab.setHomeButtonEnabled(true);
            ab.setDisplayHomeAsUpEnabled(true);
        }

        entryId = getIntent().getStringExtra("entryId");

        connection = getPresenter().getServiceConnection();
        Intent intent = new Intent(this, CoreService.class);
        bindService(intent, connection, BIND_AUTO_CREATE);
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(null != connection){
            unbindService(connection);
        }
    }

    @Override
    public String getEntryId() {
        return entryId;
    }


    @Override
    public void showEntryContent(String html) {

        Document doc = Jsoup.parse(html);

        Elements ele = doc.select("#page-content");

        textView.setText(Html.fromHtml(ele.html()));
    }
}
