package me.zhennan.scp.android.view.scp_database;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

import me.zhennan.scp.android.model.Entry;
import me.zhennan.scp.android.model.EntryImpl;
import me.zhennan.scp.android.service.CoreServiceBinder;
import me.zhennan.scp.android.usecase.UseCaseCallback;
import me.zhennan.scp.android.usecase.endpoint.EndPoint;
import me.zhennan.toolkit.android.multitypelistadapter.MultiTypeListAdapter;

/**
 * Created by zhangzhennan on 15/5/7.
 */
public class DatabasePresenterImpl implements DatabaseMVP.DatabasePresenter{

    private DatabaseMVP.DatabasePresenterView view;

    @Override
    public DatabaseMVP.DatabasePresenterView getView() {
        return view;
    }

    @Override
    public void setView(DatabaseMVP.DatabasePresenterView ref) {
        view = ref;
    }

    private MultiTypeListAdapter listAdapter;

    @Override
    public MultiTypeListAdapter getListAdapter() {
        if(null == listAdapter){
            listAdapter = new MultiTypeListAdapter();
        }
        return listAdapter;
    }

    private ServiceConnection connection;

    @Override
    public ServiceConnection getServiceConnection() {
        if(null == connection){
            connection = new ServiceConnection() {
                @Override
                public void onServiceConnected(ComponentName name, IBinder service) {
                    if(service instanceof CoreServiceBinder){
                        onConnected((CoreServiceBinder)service);
                    }
                }

                @Override
                public void onServiceDisconnected(ComponentName name) {

                }
            };
        }
        return connection;
    }

    protected void onConnected(CoreServiceBinder service){
        EndPoint point = service.getEndPoint();
        if(null != point){

            point.readSeries(0, new UseCaseCallback() {
                @Override
                public void onComplete(String html) {

                    Log.e("scp", "download success");

                    Document doc = Jsoup.parse(html);
                    Elements lis = doc.select("#page-content li");

                    List<Entry> entries = new ArrayList<Entry>();

                    for(Element ele : lis){
                        String link = ele.select("a").attr("href");
                        Entry entry = new EntryImpl();
                        entry.setLabel(ele.text());
                        entry.setUrl(link);
                        entries.add(entry);
                    }

                    Log.e("scp", "doc size" + doc.childNodeSize());
                    getListAdapter().getDataSource().addAll(entries);
                    getListAdapter().notifyDataSetChanged();

                }

                @Override
                public void onFailed(int error, String message) {
                    Log.e("scp", "error("+error+")" + message);
                }
            });
        }
    }
}
