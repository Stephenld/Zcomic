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

import butterknife.BindView;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import ldh.com.zcomic.R;
import ldh.com.zcomic.base.BaseActivity;
import ldh.com.zcomic.bean.User;
import ldh.com.zcomic.utils.ActivityUtils;
import ldh.com.zcomic.utils.LogUtils;

public class RegisterActivity extends BaseActivity {

    private boolean isHidden = true;
    private User appUser;
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
    private ActivityUtils utils;
    @Override
    protected int setLayoutResID() {
        return R.layout.register_activity;
    }
    protected void initView() {
        utils = new ActivityUtils(this);
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
                    utils.showToast("请输入昵称！");
                } else if ("".equals(emailAddressStr)) {
                    emailWarnImv.setVisibility(View.VISIBLE);
                    utils.showToast("请输入注册邮箱!");
                } else if ("".equals(passwordStr)) {
                    utils.showToast("请输入密码!");
                } else if (!"".equals(nickNameStr) && !"".equals(emailAddressStr) && !"".equals(passwordStr)) {
                    nickNameWarnImv.setVisibility(View.GONE);
                    emailWarnImv.setVisibility(View.GONE);

                    appUser = new User();
                    appUser.setUsername(nickNameStr);
                    appUser.setPassword(passwordStr);
                    appUser.setEmail(emailAddressStr);
                    appUser.signUp(new SaveListener<User>() {
                        @Override
                        public void done(User appUser, BmobException e) {
                            if (e == null) {
                                LogUtils.i("$$$$$$: 注册成功");
                                utils.showToast("注册成功!");
                                utils.startActivity(LoginActivity.class);
                            } else {
                                utils.showToast("注册失败！"+ e.getMessage());
                            }
                        }
                    });
                }
            }
        });
    }
}
