package ldh.com.zcomic.ui;

import android.support.v7.widget.Toolbar;
import android.text.Selection;
import android.text.Spannable;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import ldh.com.zcomic.MainActivity;
import ldh.com.zcomic.R;
import ldh.com.zcomic.base.BaseActivity;
import ldh.com.zcomic.bean.User;
import ldh.com.zcomic.utils.ActivityUtils;
import ldh.com.zcomic.utils.LogUtils;

/**
 * Created by allen liu on 2018/5/3.
 */

public class LoginActivity extends BaseActivity {
    private static final String LOG_MSG = "LoginActivity";
    private static final int BMOB_101 = 101;
    private static final int BMOB_9016 = 9016;

    private boolean isHidden = true;
    @BindView(R.id.login_email_et)
    EditText userEmailEt;
    @BindView(R.id.login_passwd_et)
    EditText userPasswordEt;
    @BindView(R.id.login_email_warn_imv)
    ImageView emailWarnImv;
    @BindView(R.id.login_password_view_off_imv)
    ImageView passwordEyeImv;
    @BindView(R.id.login_btn)
    Button loginBtn;
    @BindView(R.id.login_forget_passwd_tv)
    TextView forgetPasswdTv;
    @BindView(R.id.register_btn)
    TextView registerBtn;
    @BindView(R.id.toolbar_login)
    Toolbar toolbarLogin;
    // 登录信息
    private String emailAddressStr;
    private String passwordStr;
    private ActivityUtils utils;

    @Override
    protected int setLayoutResID() {
        return R.layout.login_activity;
    }
    @Override
    protected void initView() {
         utils = new ActivityUtils(this);
         setSupportActionBar(toolbarLogin);   // 这一步要加上，很关键，否则getSupportActionBar().setTitle等会报空指针异常
         getSupportActionBar().setTitle("用户登录");
    }
    @Override
    protected void initListener() {
        userLogin(); // 用户登录
    }

    /**
     * 用户登录
     */
    private void userLogin() {
        // 密码是否可见控件的处理逻辑
        passwordEyeImv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isHidden) {
                    // 当需要可见密码时
                    passwordEyeImv.setImageResource(R.drawable.ic_visibility_grey600_18dp);
                    userPasswordEt.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    // 当需要密码不可见时
                    passwordEyeImv.setImageResource(R.drawable.ic_visibility_off_grey600_18dp);
                    userPasswordEt.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
                isHidden = !isHidden;
                userPasswordEt.postInvalidate();

                // 切换后将passwordEt光标置于末尾
                CharSequence charSequence = userPasswordEt.getText();
                if (charSequence instanceof Spannable) {
                    Spannable spannable = (Spannable) charSequence;
                    Selection.setSelection(spannable, charSequence.length());
                }
            }
        });

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                emailAddressStr = userEmailEt.getText().toString().trim();
                passwordStr = userPasswordEt.getText().toString().trim();

                // 输入框的内容的简单校验
                if ("".equals(emailAddressStr)) {
                    emailWarnImv.setVisibility(View.VISIBLE);
                    utils.showToast("请输入注册的邮箱地址！");
                } else if ("".equals(passwordStr)) {
                    utils.showToast("请输入您的密码！");
                } else if (!"".equals(emailAddressStr) && !"".equals(passwordStr)) {
                    emailWarnImv.setVisibility(View.GONE);
                    User appUser = new User();
                    appUser.setUsername(emailAddressStr);
                    appUser.setPassword(passwordStr);
                    appUser.login(new SaveListener<User>() {
                        @Override
                        public void done(User appUser, BmobException e) {
                            if (appUser != null) {
                                LogUtils.i("$$$$$$: 用户登陆成功!");
                                utils.showToast("登录成功，欢迎使用！");
                                utils.startActivity(MainActivity.class);
                            } else {
                                LogUtils.i( ">>>>>> 登录失败: " + e.getErrorCode() + ",  " + e.getMessage());
                                if (BMOB_101 == e.getErrorCode()) {
                                    utils.showToast("用户名或密码错误！");
                                } else if (BMOB_9016 == e.getErrorCode()) {
                                    utils.showToast("网络不可用，请检查您的网络设置！");
                                } else {
                                    utils.showToast("登录失败！");
                                }
                            }
                        }
                    });
                }
            }
        });

        // 忘记密码控件的点击处理
        forgetPasswdTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                utils.startActivity(ResetPasswordActivity.class);
            }
        });
        // 注册
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                utils.startActivity(RegisterActivity.class);
            }
        });
    }
}
