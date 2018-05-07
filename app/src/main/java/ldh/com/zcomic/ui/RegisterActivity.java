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
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v7.widget.Toolbar;

import butterknife.BindView;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import ldh.com.zcomic.R;
import ldh.com.zcomic.base.BaseActivity;
import ldh.com.zcomic.bean.AppUser;

public class RegisterActivity extends BaseActivity {
    private static final String LOG_MSG = "RegisterActivity";
    private static final int BMOB_301 = 301;
    private static final int BMOB_202 = 202;
    private static final int BMOB_9016 = 9016;

    private boolean isHidden = true;

    private AppUser appUser;

    private ImageButton titleImv;
    private TextView titleCenterTv;
    private TextView titleRightTv;
    @BindView(R.id.nickname_et)
    EditText nickNameEt;
    @BindView(R.id.email_et)
    EditText emailAddressEt;
    @BindView(R.id.passwd_et)
    EditText passwordEt;
    @BindView(R.id.reg_nickname_warning_imv)
    ImageView nickNameWarnImv;
    @BindView(R.id.reg_email_warning_imv)
    ImageView emailWarnImv;
    @BindView(R.id.reg_password_view_off_imv)
    ImageView passwordEyeImv;
    @BindView(R.id.register_btn)
    Button registerBtn;
    @BindView(R.id.toolbar_register)
    Toolbar toolbarRegister;
    // 注册信息
    private String nickNameStr;
    private String emailAddressStr;
    private String passwordStr;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.register_activity);
//        initView(); // 初始化界面控件
//        userRegister();  // 用户注册
//
//    }
    @Override
    protected int setLayoutResID() {
        return R.layout.register_activity;
    }
    protected void initView() {
        setSupportActionBar(toolbarRegister);
        getSupportActionBar().setTitle("免费注册");
    }

    @Override
    protected void initListener() {
        userRegister();  // 用户注册
    }
    /**
     * 用户注册
     */
    private void userRegister() {
        // 密码是否可见控件的处理逻辑
        passwordEyeImv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isHidden) {
                    // 当需要可见密码时
                    passwordEyeImv.setImageResource(R.drawable.ic_visibility_grey600_18dp);
                    passwordEt.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    // 当需要密码不可见时
                    passwordEyeImv.setImageResource(R.drawable.ic_visibility_off_grey600_18dp);
                    passwordEt.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }

                isHidden = !isHidden;
                passwordEt.postInvalidate();

                // 切换后将passwordEt光标置于末尾
                CharSequence charSequence = passwordEt.getText();
                if (charSequence instanceof Spannable) {
                    Spannable spannable = (Spannable) charSequence;
                    Selection.setSelection(spannable, charSequence.length());
                }
            }
        });

        // 注册按钮控件的处理逻辑
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nickNameStr = nickNameEt.getText().toString().trim();
                emailAddressStr = emailAddressEt.getText().toString().trim();
                passwordStr = passwordEt.getText().toString().trim();

                // 输入框的内容的简单校验
                if (nickNameStr.equals("")) {
                    nickNameWarnImv.setVisibility(View.VISIBLE);
                    Toast.makeText(RegisterActivity.this, "请输入昵称!", Toast.LENGTH_LONG).show();
                } else if ("".equals(emailAddressStr)) {
                    emailWarnImv.setVisibility(View.VISIBLE);
                    Toast.makeText(RegisterActivity.this, "请输入注册邮箱!", Toast.LENGTH_LONG).show();
                } else if ("".equals(passwordStr)) {
                    Toast.makeText(RegisterActivity.this, "请输入密码!", Toast.LENGTH_LONG).show();
                } else if (!"".equals(nickNameStr) && !"".equals(emailAddressStr) && !"".equals(passwordStr)) {
                    nickNameWarnImv.setVisibility(View.GONE);
                    emailWarnImv.setVisibility(View.GONE);

                    appUser = new AppUser();
                    appUser.setUsername(nickNameStr);
                    appUser.setPassword(passwordStr);
                    appUser.setEmail(emailAddressStr);
                    appUser.signUp(new SaveListener<AppUser>() {
                        @Override
                        public void done(AppUser appUser, BmobException e) {
                            if (e == null) {
                                Log.i(LOG_MSG, "$$$$$$: 注册成功");
                                Toast.makeText(RegisterActivity.this, "注册成功!", Toast.LENGTH_LONG).show();
                                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                            } else {
                                Toast.makeText(RegisterActivity.this, "注册失败! " + e.getMessage(), Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }
            }
        });
    }
}
