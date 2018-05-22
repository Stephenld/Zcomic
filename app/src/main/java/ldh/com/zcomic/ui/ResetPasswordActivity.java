package ldh.com.zcomic.ui;

import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import butterknife.BindView;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;
import ldh.com.zcomic.R;
import ldh.com.zcomic.base.BaseActivity;
import ldh.com.zcomic.utils.ActivityUtils;

public class ResetPasswordActivity extends BaseActivity {

    private ActivityUtils utils;
    private String resetEmailStr;
    @BindView(R.id.reset_email_et)
    EditText resetEmailEt;
    @BindView(R.id.reset_email_warn_imv)
    ImageView resetWarnImv;
    @BindView(R.id.reset_passwd_btn)
    Button resetPasswdBtn;
    @BindView(R.id.toolbar_reset)
    Toolbar toolbarReset;

    @Override
    protected int setLayoutResID() {
        return R.layout.main_reset_activy;
    }

    /**
     * 初始化页面
     */
    protected void initView() {
        utils = new ActivityUtils(this);
        setSupportActionBar(toolbarReset);
        getSupportActionBar().setTitle("重置密码");
    }

    @Override
    protected void initListener() {
        userResetPassword();  // 重置密码业务逻辑
    }

    /**
     * 重置密码业务逻辑
     */
    private void userResetPassword() {
        resetPasswdBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetEmailStr = resetEmailEt.getText().toString().trim();
                if ("".equals(resetEmailStr)) {
                    resetWarnImv.setVisibility(View.VISIBLE);
                    utils.showToast("请输入登录邮箱");
                } else {
                    resetWarnImv.setVisibility(View.GONE);
                    BmobUser.resetPasswordByEmail(resetEmailStr, new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            if (e == null) {
                                utils.showToast("重置密码请求成功，请到"+resetEmailStr+"邮箱进行密码重置操作");
                                ResetPasswordActivity.this.finish();
                            } else {
                                utils.showToast("重置密码失败！"+ e.getMessage());
                            }
                        }
                    });
                }
            }
        });
    }
}
