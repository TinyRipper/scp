package me.zhennan.scp.android.model;

/**
 * Created by zhangzhennan on 15/5/11.
 */
public class EntryImpl implements Entry {

    private String label;
    private String url;

    @Override
    public String getLabel() {
        return label;
    }

    @Override
    public void setLabel(String label) {
        this.label = label;
    }

    @Override
    public String getUrl() {
        return url;
    }

    @Override
    public void setUrl(String url) {
        this.url = url;
    }
}
