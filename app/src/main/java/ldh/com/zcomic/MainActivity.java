package ldh.com.zcomic;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.blankj.utilcode.utils.FileUtils;

import java.lang.ref.WeakReference;

import butterknife.BindView;
import de.hdodenhof.circleimageview.CircleImageView;
import ldh.com.zcomic.bean.User;
import ldh.com.zcomic.fragment.ClassifyFragment;
import ldh.com.zcomic.fragment.CollectionFragment;
import ldh.com.zcomic.fragment.HotFragment;
import ldh.com.zcomic.fragment.NewsFragment;
import ldh.com.zcomic.ui.LoginActivity;
import ldh.com.zcomic.ui.SearchActivity;
import ldh.com.zcomic.utils.ActivityUtils;
import ldh.com.zcomic.utils.SharedPreUtils;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,View.OnClickListener,Toolbar.OnMenuItemClickListener {
    @BindView(R.id.fl_layout)
    FrameLayout mFlLayout;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.nav_view)
    NavigationView mNavView;
    @BindView(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;

    private ActionBarDrawerToggle mToggle;
    private NewsFragment mNewsFragment;
    private ClassifyFragment mClassifyFragment;
    private HotFragment mHotFragment;
    private CollectionFragment mCollectionFragment;
    private FragmentManager mFragmentManager;
    private TextView user_name, user_email;
    private CircleImageView user_photo;

    private long waitTime = 2000;
    private long touchTime = 0;
    private static final int REQUEST_CODE = 1;
    private AlertDialog.Builder builder;
    private String cacheSize = "";//内部缓存
    private String Size = "";//外部缓存
    private ActivityUtils utils;
    private MyHandler mHandler;
    public static final int SUCCESS = 0;
    public static final int FAILED = 1;

    class MyHandler extends Handler {
        WeakReference<MainActivity> mainActivity;

        public MyHandler(MainActivity activity) {
            mainActivity = new WeakReference(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SUCCESS:
                    utils.showToast("清理成功");
                    break;
                case FAILED:
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int theme_id = SharedPreUtils.getInt(this, "theme_id", R.style.AppTheme);
        setTheme(theme_id);
        setContentView(R.layout.activity_main);
        initData();
        initView();
        initListener();
    }

    private void initData() {
        mFragmentManager = getSupportFragmentManager();
            mClassifyFragment = new ClassifyFragment();
//            getSupportActionBar().setTitle("分类漫画");
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.fl_layout, mClassifyFragment);
        fragmentTransaction.commit();
    }

    protected void initView() {

        mFlLayout = findViewById(R.id.fl_layout);
        mToolbar = findViewById(R.id.toolbar);
        mNavView = findViewById(R.id.nav_view);
        mDrawerLayout = findViewById(R.id.drawer_layout);
        mHandler = new MyHandler(this);
        utils = new ActivityUtils(this);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mFragmentManager = getSupportFragmentManager();
        mToggle = new ActionBarDrawerToggle
                (this, mDrawerLayout, mToolbar, 0, 0);
        mDrawerLayout.addDrawerListener(mToggle);
        /*同步drawerlayout的状态*/
        mToggle.syncState();
        initHeaderView();
        initUserViews();

        new Thread(new Runnable() {
            @Override
            public void run() {

                cacheSize = FileUtils.getDirSize(getCacheDir());
                if (Environment.getExternalStorageState().equals(//getCacheDir()内部缓存目录(api>=24) getExternalCacheDir SDcard外部关联目录
                        Environment.MEDIA_MOUNTED)) {
                    Size = FileUtils.getDirSize(getExternalCacheDir());

                    Log.i("Zcomic", "run: --------------------------" + cacheSize + Size);

                }
            }
        }).start();
    }

    private void initHeaderView() {
        View view = mNavView.getHeaderView(0);
        user_name = view.findViewById(R.id.user_name);
        user_email = view.findViewById(R.id.user_email);
        user_photo = view.findViewById(R.id.user_photo);
    }

    protected void initListener() {
        mNavView.setNavigationItemSelectedListener(this);
        mNavView.setItemIconTintList(null);
        user_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
            }
        });
        user_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
            }
        });
        mToolbar.setOnMenuItemClickListener(this);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
        switch (item.getItemId()) {
            case R.id.nav_classify:
//                hideAllFragment(fragmentTransaction);
//                if (mClassifyFragment == null) {
//                    mClassifyFragment = new ClassifyFragment();
//                    fragmentTransaction.add(R.id.fl_layout, mClassifyFragment);
//                } else {
//                    fragmentTransaction.show(mClassifyFragment);
//                }
//                getSupportActionBar().setTitle("分类漫画");
                mClassifyFragment = new ClassifyFragment();
                fragmentTransaction.replace(R.id.fl_layout, mClassifyFragment);
                getSupportActionBar().setTitle("分类漫画");
                break;
            case R.id.nav_hot:
//                hideAllFragment(fragmentTransaction);
//                if (mHotFragment == null) {
//                    mHotFragment = new HotFragment();
//                    fragmentTransaction.add(R.id.fl_layout, mHotFragment);
//                } else {
//                    fragmentTransaction.show(mHotFragment);
//                }
//                getSupportActionBar().setTitle("热门漫画");
              if (mHotFragment == null) {
                    mHotFragment = new HotFragment();
                }
                fragmentTransaction.replace(R.id.fl_layout, mHotFragment);
                getSupportActionBar().setTitle("热门漫画");
                break;
            case R.id.nav_news:
//                hideAllFragment(fragmentTransaction);
//                if (mNewsFragment == null ) {
//                    mNewsFragment = new NewsFragment();
//                    fragmentTransaction.add(R.id.fl_layout, mNewsFragment);
//                } else {
//                    fragmentTransaction.show(mNewsFragment);
//                }
//                getSupportActionBar().setTitle("漫画资讯");
              if (mNewsFragment == null ) {
                    mNewsFragment = new NewsFragment();
                }
                fragmentTransaction.replace(R.id.fl_layout, mNewsFragment);
                getSupportActionBar().setTitle("漫画资讯");
                break;
            case R.id.nav_collect:
//                hideAllFragment(fragmentTransaction);
//                if (mCollectionFragment == null) {
//                    mCollectionFragment = new CollectionFragment();
//                }
//                fragmentTransaction.replace(R.id.fl_layout, mCollectionFragment);
//                getSupportActionBar().setTitle("我的收藏");
                if (mCollectionFragment == null) {
                    mCollectionFragment = new CollectionFragment();
                }
                fragmentTransaction.replace(R.id.fl_layout, mCollectionFragment);
                getSupportActionBar().setTitle("我的收藏");
                break;

            case R.id.nav_clear_cache:
                clearCaches();
                break;
            case R.id.nav_version_update:
//                VersionUtils.versionUpdate(MainActivity.this,);
                Toast.makeText(this, "暂无更新版本",Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_change_theme:
                alertChangeTheme();
                break;
            case R.id.nav_day_night:

                if (SharedPreUtils.getInt(this, "theme_id", R.style.AppTheme) != R.style.AppTheme_Night) {
                    changeTheme(9);
                } else {
                    Intent intent = getIntent();
                    SharedPreUtils.putInt(this, "theme_id", R.style.AppTheme);
                    overridePendingTransition(R.anim.design_snackbar_in, R.anim.design_snackbar_out);
                    finish();
                    overridePendingTransition(R.anim.design_snackbar_in, R.anim.design_snackbar_out);
                    startActivity(intent);
                }
                break;
            case R.id.nav_exit:
                new AlertDialog.Builder(this).setTitle("是否退出当前账户？")
                        .setNegativeButton("取消", null)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                User.logOut();
                                user_email.setText("");
                                user_name.setText("登录/注册");
                                user_photo.setImageResource(R.drawable.default_icon);
                            }
                        }).show();
                break;
        }
        mDrawerLayout.closeDrawer(GravityCompat.START);
        fragmentTransaction.commit();
        return true;
    }

    private void clearCaches() {

        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        if ("0.00B".equals(Size) || Size.isEmpty() || Size.length() == 0) {
            dialog.setTitle("确定要清除缓存？").setMessage("缓存大小:" + Size).setPositiveButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    new Thread(new Runnable() {
                        @RequiresApi(api = Build.VERSION_CODES.N)
                        @Override
                        public void run() {
                            FileUtils.deleteDir(getCacheDir());
                            if (Environment.getExternalStorageDirectory().equals(Environment.MEDIA_MOUNTED)) {
                                FileUtils.deleteDir(getExternalCacheDir());
                            }
                            mHandler.sendEmptyMessage(SUCCESS);
                        }
                    }).start();
                }
            }).setNegativeButton("取消", null).show();
        } else {
            dialog.setTitle("确定要清除缓存？").setMessage("内部缓存:" + cacheSize).setPositiveButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    new Thread(new Runnable() {
                        @RequiresApi(api = Build.VERSION_CODES.N)
                        @Override
                        public void run() {
                            FileUtils.deleteDir(getCacheDir());
                            FileUtils.deleteFile(getCacheDir());
                            if (Environment.getExternalStorageDirectory().equals(Environment.MEDIA_MOUNTED)) {
                                FileUtils.deleteDir(getExternalCacheDir());
                                FileUtils.deleteFile(getExternalCacheDir());
                            }
                            mHandler.sendEmptyMessage(SUCCESS);
                        }
                    }).start();
                }
            }).setNegativeButton("取消", null).show();
        }

    }

    private void alertChangeTheme() {
        View view = View.inflate(MainActivity.this, R.layout.item_change_theme, null);
        builder = new AlertDialog.Builder(this);
        builder.setView(view).show();
        view.findViewById(R.id.tv_red).setOnClickListener(this);
        view.findViewById(R.id.tv_green).setOnClickListener(this);
        view.findViewById(R.id.tv_blue).setOnClickListener(this);
        view.findViewById(R.id.tv_orange).setOnClickListener(this);
        view.findViewById(R.id.tv_pink).setOnClickListener(this);
        view.findViewById(R.id.tv_sky).setOnClickListener(this);
        view.findViewById(R.id.tv_purple).setOnClickListener(this);
        view.findViewById(R.id.tv_pp).setOnClickListener(this);
        view.findViewById(R.id.tv_yellow).setOnClickListener(this);
    }

    private void hideAllFragment(FragmentTransaction fragmentTransaction) {
        if (mNewsFragment != null) {
            fragmentTransaction.hide(mNewsFragment);
        }
        if (mClassifyFragment != null) {
            fragmentTransaction.hide(mClassifyFragment);
        }
        if (mHotFragment != null) {
            fragmentTransaction.hide(mHotFragment);
        }
        if (mCollectionFragment != null) {
            fragmentTransaction.hide(mCollectionFragment);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN && KeyEvent.KEYCODE_BACK == keyCode) {
            long currentTime = System.currentTimeMillis();
            if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
                mDrawerLayout.closeDrawer(GravityCompat.START);
            } else if ((currentTime - touchTime) >= waitTime) {
                //让Toast的显示时间和等待时间相同
                Toast.makeText(this, "再按一次退出", Toast.LENGTH_SHORT).show();
                touchTime = currentTime;
            } else {
                MainActivity.this.finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void initUserViews() {
        if (User.getCurrentUser() != null) {
            user_photo.setImageResource(R.mipmap.ic_launcher);
            user_name.setText(User.getCurrentUser().getUsername());
            user_email.setVisibility(View.VISIBLE);
            user_email.setText(User.getCurrentUser().getEmail());
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_sky:
                changeTheme(0);
                break;
            case R.id.tv_blue:
                changeTheme(1);
                break;
            case R.id.tv_green:
                changeTheme(2);
                break;
            case R.id.tv_orange:
                changeTheme(3);
                break;
            case R.id.tv_pink:
                changeTheme(4);
                break;
            case R.id.tv_red:
                changeTheme(5);
                break;
            case R.id.tv_purple:
                changeTheme(6);
                break;
            case R.id.tv_pp:
                changeTheme(7);
                break;
            case R.id.tv_yellow:
                changeTheme(8);
                break;
        }
    }

    private void changeTheme(int index) {
        int[] themes = new int[]{R.style.AppTheme, R.style.AppTheme_Blue, R.style.AppTheme_Green,
                R.style.AppTheme_Orange, R.style.AppTheme_Pink, R.style.AppTheme_Red,
                R.style.AppTheme_Purple, R.style.AppTheme_PP, R.style.AppTheme_Yellow,
                R.style.AppTheme_Night};
        SharedPreUtils.putInt(this, "theme_id", themes[index]);
        Intent intent = getIntent();
        overridePendingTransition(R.anim.abc_popup_enter, R.anim.abc_popup_exit);
        finish();
        overridePendingTransition(R.anim.abc_fade_in, R.anim.abc_fade_out);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_toolbar, menu);
        return true;
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.toolbar_menu_search:
                Intent mIntent = new Intent(MainActivity.this, SearchActivity.class);
                startActivity(mIntent);
                break;
        }
        return false;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 789) {
            Bundle bundle = data.getExtras();
            int tabPosition = bundle.getInt("NewTabPostion");
            mClassifyFragment.setCurrentChannel(tabPosition);
            mClassifyFragment.notifyChannelChange();
        }
    }
//    @Override
//    public void onAttachFragment(android.support.v4.app.Fragment fragment) {
//        super.onAttachFragment(fragment);
//        if (mNewsFragment == null && fragment instanceof NewsFragment) {
//            mNewsFragment = (NewsFragment) fragment;
//        } else if (mClassifyFragment == null && fragment instanceof ClassifyFragment) {
//            mClassifyFragment = (ClassifyFragment) fragment;
//        } else if (mHotFragment == null && fragment instanceof HotFragment) {
//            mHotFragment = (HotFragment) fragment;
//        } else if (mCollectionFragment == null && fragment instanceof CollectionFragment) {
//            mCollectionFragment = (CollectionFragment) fragment;
//        }
//    }
}
