package ldh.com.zcomic.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import butterknife.ButterKnife;
import ldh.com.zcomic.R;
import ldh.com.zcomic.entity.Constants;
import ldh.com.zcomic.utils.SharedPreUtils;
import ldh.com.zcomic.utils.VersionUtils;
import ldh.com.zcomic.utils.ViewUtil;

/**
 * Created by allen liu on 2018/5/2.
 */

public abstract class BaseActivity extends AppCompatActivity {
    protected Activity mContext;
    protected  boolean enableNightMode ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mContext = this;
        int theme_id = SharedPreUtils.getInt(this,"theme_id", R.style.AppTheme);
        setTheme(theme_id);
        setContentView(setLayoutResID());
        ButterKnife.bind(mContext);
        initView();
        init();
        initListener();
    }
    protected abstract int setLayoutResID();
    protected abstract void initView();
    protected abstract void initListener();
    //初始化，放在initView（）后面
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

    @Override
    protected void onStart() {
        super.onStart();
        //屏幕亮度
        Constants.osScreenBrightValue = ViewUtil.getScreenBrightness(this);
        if (Constants.osNightModel){
            ViewUtil.setScreenBrightness(this, 10);
        }else {
            ViewUtil.setScreenBrightness(this, Constants.osScreenBrightValue);
        }
    }
}
