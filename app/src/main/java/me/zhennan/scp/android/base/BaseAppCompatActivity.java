package me.zhennan.scp.android.base;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import me.zhennan.scp.android.R;


/**
 * Created by zhangzhennan on 15/5/7.
 */
public class BaseAppCompatActivity extends AppCompatActivity {

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(android.R.id.home == item.getItemId()){
            finish();
            return true;
        }else {
            return super.onOptionsItemSelected(item);
        }
    }
}
