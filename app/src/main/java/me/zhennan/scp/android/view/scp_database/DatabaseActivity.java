package me.zhennan.scp.android.view.scp_database;

import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import me.zhennan.scp.android.R;
import me.zhennan.scp.android.base.BaseAppCompatActivity;
import me.zhennan.scp.android.model.Entry;
import me.zhennan.scp.android.model.EntryImpl;
import me.zhennan.scp.android.service.CoreService;
import me.zhennan.scp.android.view.scp_entry.EntryActivity;
import me.zhennan.toolkit.android.multitypelistadapter.MultiTypeListAdapter;

/**
 * Created by zhangzhennan on 15/5/7.
 */
public class DatabaseActivity extends BaseAppCompatActivity implements DatabaseMVP.DatabasePresenterView {

    private DatabaseMVP.DatabasePresenter presenter;

    @Override
    public DatabaseMVP.DatabasePresenter getPresenter() {
        if(null == presenter){
            presenter = new DatabasePresenterImpl();
            presenter.setView(this);
        }
        return presenter;
    }

    @Override
    public void setPresenter(DatabaseMVP.DatabasePresenter ref) {
        presenter = ref;
    }

    private ServiceConnection connection;

    @InjectView(R.id.list_view)
    protected ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_database);
        ButterKnife.inject(this);

        onSetupViewAdapter();

        listView.setEmptyView(findViewById(R.id.empty_view));
        listView.setAdapter(getPresenter().getListAdapter());
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Object object = getPresenter().getListAdapter().getItem(position);
                if(object instanceof Entry){
                    onEntryPressed((Entry)object);
                }
            }
        });

        // service connection
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

    protected void onSetupViewAdapter(){
        MultiTypeListAdapter adapter = getPresenter().getListAdapter();
        adapter.registerItemType(String.class, R.layout.li_header, new MultiTypeListAdapter.ItemRenderDelegate<String>() {
            @Override
            public void onItemRender(int position, String item, View view) {
                TextView textView = (TextView) view.findViewById(R.id.text_view);
                textView.setText(item);
            }
        });

        adapter.registerItemType(EntryImpl.class, R.layout.li_entry, new MultiTypeListAdapter.ItemRenderDelegate<EntryImpl>() {
            @Override
            public void onItemRender(int position, EntryImpl item, View view) {
                TextView textView = (TextView) view.findViewById(R.id.text_view);
                textView.setText(item.getLabel());

            }
        });
    }

    protected void onEntryPressed(Entry entry){
        Intent intent = new Intent(this, EntryActivity.class);
        intent.putExtra("entryId", entry.getUrl());
        startActivity(intent);

    }
}
