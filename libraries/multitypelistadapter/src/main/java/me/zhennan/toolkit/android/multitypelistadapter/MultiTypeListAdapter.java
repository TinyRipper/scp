package me.zhennan.toolkit.android.multitypelistadapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhangzhennan on 15/5/7.
 */
public class MultiTypeListAdapter extends BaseAdapter implements ListAdapter {

    private List<Object> dataSource;
    public List<Object> getDataSource(){
        if(null == dataSource){
            dataSource = new ArrayList<>();
        }
        return dataSource;
    }



    private Map<String, Integer> resMap;
    protected Map<String, Integer> getResMap(){
        if(null == resMap){
            resMap = new HashMap<>();
        }
        return resMap;
    }

    private Map<String, ItemRenderDelegate> delegateMap;
    protected Map<String, ItemRenderDelegate> getDelegateMap(){
        if(null == delegateMap){
            delegateMap = new HashMap<>();
        }
        return delegateMap;
    }

    protected Map<String, Boolean> disableMap;
    protected Map<String, Boolean> getDisableMap(){
        if(null == disableMap){
            disableMap = new HashMap<>();
        }
        return disableMap;
    }

    private List<String> types;
    protected List<String> getTypes(){
        if(null == types){
            types = new ArrayList<>();
        }
        return types;
    }

    public void registerItemType(Class type, int viewResId, ItemRenderDelegate delegate){
        registerItemType(type, viewResId, delegate, false);
    }

    public void registerItemType(Class type, int viewResId, ItemRenderDelegate delegate, boolean isDisabled){
        String name = type.getName();
        getTypes().add(name);
        getResMap().put(name, viewResId);
        getDelegateMap().put(name, delegate);

        if(isDisabled) {
            getDisableMap().put(name, true);
        }
    }

    public void unregisterItemType(Class type){
        String name = type.getName();
        int index = getTypes().indexOf(name);
        if(-1 < index){
            getTypes().set(index, "");
            getResMap().remove(name);
            getDelegateMap().remove(name);
            getDisableMap().remove(name);
        }
    }

    @Override
    public int getCount() {
        return getDataSource().size();
    }

    @Override
    public Object getItem(int position) {
        return getDataSource().get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {

        Object item = getItem(position);
        String name = item.getClass().getName();
        return getTypes().indexOf(name);
    }

    @Override
    public int getViewTypeCount() {
        int size = getTypes().size();
        return 1>=size? 1 : size;
    }

    @Override
    public boolean areAllItemsEnabled() {
        return 0 == getDisableMap().size();
    }

    @Override
    public boolean isEnabled(int position) {
        return super.isEnabled(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Object item = getItem(position);
        String type = item.getClass().getName();
        if(null == convertView){
            int resId = getResMap().get(type);
            convertView = LayoutInflater.from(parent.getContext()).inflate(resId, parent, false);
        }

        ItemRenderDelegate delegate = getDelegateMap().get(type);

        if(null != delegate){
            delegate.onItemRender(position, item, convertView);
        }

        return convertView;
    }

    public interface ItemRenderDelegate<Type>{
        void onItemRender(int position, Type item, View view);
    }

}
