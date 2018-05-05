package ldh.com.zcomic.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import butterknife.ButterKnife;
import ldh.com.zcomic.utils.VersionUtils;

/**
 * Created by allen liu on 2018/5/2.
 */

public abstract class BaseActivity extends AppCompatActivity {
    protected Activity mContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mContext = this;
        setContentView(setLayoutResID());
        ButterKnife.bind(mContext);
        initView();
        init();
        initListener();
    }
    protected abstract int setLayoutResID();
    protected abstract void initView();
    protected abstract void initListener();
    //初始化
    private void init() {
        //设置显示返回键
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //判断版本
        if (VersionUtils.isLollipop()) {
            //取消阴影
            getSupportActionBar().setElevation(0);
        }

    }

    //返回键的响应
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
