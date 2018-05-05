package ldh.com.zcomic.ui;

import android.content.Intent;
import android.text.Selection;
import android.text.Spannable;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import ldh.com.zcomic.MainActivity;
import ldh.com.zcomic.R;
import ldh.com.zcomic.base.BaseActivity;
import ldh.com.zcomic.beans.AppUser;

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
    // 登录信息
    private String emailAddressStr;
    private String passwordStr;

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.login_activity);
//        initView();
//        userLogin(); // 用户登录
//    }

    @Override
    protected int setLayoutResID() {
        return R.layout.login_activity;
    }
    protected void initView() {
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
                    Toast.makeText(LoginActivity.this, "请输入注册的邮箱地址!", Toast.LENGTH_LONG).show();
                } else if ("".equals(passwordStr)) {
                    Toast.makeText(LoginActivity.this, "请输入您的密码!", Toast.LENGTH_LONG).show();
                } else if (!"".equals(emailAddressStr) && !"".equals(passwordStr)) {
                    emailWarnImv.setVisibility(View.GONE);
                    AppUser appUser = new AppUser();
                    appUser.setUsername(emailAddressStr);
                    appUser.setPassword(passwordStr);
                    appUser.login(new SaveListener<AppUser>() {
                        @Override
                        public void done(AppUser appUser, BmobException e) {
                            if (appUser != null) {
                                Log.i(LOG_MSG, "$$$$$$: 用户登陆成功!");
                                Toast.makeText(LoginActivity.this, "登录成功, 欢迎使用!", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                            } else {
                                Log.i(LOG_MSG, ">>>>>> 登录失败: " + e.getErrorCode() + ",  " + e.getMessage());
                                if (BMOB_101 == e.getErrorCode()) {
                                    Toast.makeText(LoginActivity.this, "用户名或密码错误!", Toast.LENGTH_LONG).show();
                                } else if (BMOB_9016 == e.getErrorCode()) {
                                    Toast.makeText(LoginActivity.this, "网络不可用, 请检查您的网络设置!", Toast.LENGTH_LONG).show();
                                } else {
                                    Toast.makeText(LoginActivity.this, "登录失败!", Toast.LENGTH_LONG).show();
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
                startActivity(new Intent(LoginActivity.this, ResetPasswordActivity.class));
            }
        });
        // 注册
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });
    }
}
