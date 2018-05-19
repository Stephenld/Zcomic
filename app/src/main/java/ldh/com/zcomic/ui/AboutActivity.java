package ldh.com.zcomic.ui;

import android.app.AlertDialog;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import ldh.com.zcomic.R;
import ldh.com.zcomic.base.BaseActivity;

/**
 * Created by allen liu on 2018/5/19.
 */

public class AboutActivity extends BaseActivity {
    @BindView(R.id.tv_statement)
    TextView tvStatement;
    @BindView(R.id.tv_version)
    TextView tvVersion;
    @BindView(R.id.toolbar_about)
    Toolbar toolbarAbout;

    @Override
    protected int setLayoutResID() {
        return R.layout.about_activity;
    }

    @Override
    protected void initView() {
        setSupportActionBar(toolbarAbout);   // 这一步要加上，很关键，否则getSupportActionBar().setTitle等会报空指针异常
        getSupportActionBar().setTitle("关于软件");
        tvVersion.setText("当前版本 V" + getVersionName());
    }
    @Override
    protected void initListener() {
        tvStatement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showStatement();
            }
        });
    }
    private void showStatement() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        AlertDialog dialog = builder.create();
        dialog.setMessage("本软件仅用于学习，未有任何商业目的。");
        dialog.show();
    }
    private String getVersionName() {
        PackageManager packageManager = getPackageManager();
        PackageInfo packInfo = null;
        try {
            packInfo = packageManager.getPackageInfo(getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        String version = packInfo.versionName;
        return version;
    }
}
