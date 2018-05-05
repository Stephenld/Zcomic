package ldh.com.zcomic.ui;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import butterknife.BindView;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;
import ldh.com.zcomic.R;
import ldh.com.zcomic.base.BaseActivity;

public class ResetPasswordActivity extends BaseActivity {

    private String resetEmailStr;
    @BindView(R.id.reset_email_et)
    EditText resetEmailEt;
    @BindView(R.id.reset_email_warn_imv)
    ImageView resetWarnImv;
    @BindView(R.id.reset_passwd_btn)
    Button resetPasswdBtn;
    @Override
    protected int setLayoutResID() {
        return R.layout.main_reset_activy;
    }

    /**
     * 初始化页面
     */
    protected void initView() {
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
                    Toast.makeText(ResetPasswordActivity.this, "请输入登录邮箱!", Toast.LENGTH_LONG).show();
                } else {
                    resetWarnImv.setVisibility(View.GONE);
                    BmobUser.resetPasswordByEmail(resetEmailStr, new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            if (e == null) {
                                Toast.makeText(ResetPasswordActivity.this, "重置密码请求成功，请到" + resetEmailStr + "邮箱进行密码重置操作", Toast.LENGTH_LONG).show();
                                ResetPasswordActivity.this.finish();
                            } else {
                                Toast.makeText(ResetPasswordActivity.this, "重置密码失败!" + e.getMessage(), Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }
            }
        });
    }
}
