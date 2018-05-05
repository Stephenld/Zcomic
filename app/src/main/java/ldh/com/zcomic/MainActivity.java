package ldh.com.zcomic;

import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import de.hdodenhof.circleimageview.CircleImageView;
import ldh.com.zcomic.base.BaseActivity;
import ldh.com.zcomic.beans.AppUser;
import ldh.com.zcomic.fragment.CategoryFragment;
import ldh.com.zcomic.fragment.HotFragment;
import ldh.com.zcomic.fragment.MainFragment;
import ldh.com.zcomic.fragment.SearchFragment;
import ldh.com.zcomic.fragment.SettingFragment;
import ldh.com.zcomic.ui.LoginActivity;

public class MainActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener {
    @BindView(R.id.fl_layout)
    FrameLayout mFlLayout;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.nav_view)
    NavigationView mNavView;
    @BindView(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;

    private ActionBarDrawerToggle mToggle;
    private MainFragment mMainFragment;
    private CategoryFragment mCategoryFragment;
    private HotFragment mHotFragment;
    private SearchFragment mSearchFragment;
    private FragmentManager mFragmentManager;
    private TextView user_name, user_email;
    private CircleImageView user_photo;

    private long waitTime = 2000;
    private long touchTime = 0;
    private static final int REQUEST_CODE = 1;
    @Override
    protected int setLayoutResID() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        mFlLayout = findViewById(R.id.fl_layout);
        mToolbar = findViewById(R.id.toolbar);
        mNavView = findViewById(R.id.nav_view);
        mDrawerLayout = findViewById(R.id.drawer_layout);
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
    }

    private void initHeaderView() {
        View view = mNavView.getHeaderView(0);
        user_name = view.findViewById(R.id.user_name);
        user_email = view.findViewById(R.id.user_email);
        user_photo = view.findViewById(R.id.user_photo);
    }

    @Override
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
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
        switch (item.getItemId()) {
            case R.id.nav_main:
                hideAllFragment(fragmentTransaction);
                if (mMainFragment == null) {
                    mMainFragment = new MainFragment();
                    fragmentTransaction.add(R.id.fl_layout, mMainFragment);
                } else {
                    fragmentTransaction.show(mMainFragment);
                }
                getSupportActionBar().setTitle("漫画阅读");
                break;
            case R.id.nav_category:
                hideAllFragment(fragmentTransaction);
                if (mCategoryFragment == null) {
                    mCategoryFragment = new CategoryFragment();
                    fragmentTransaction.add(R.id.fl_layout, mCategoryFragment);
                } else {
                    fragmentTransaction.show(mCategoryFragment);
                }
                getSupportActionBar().setTitle("分类漫画");
                break;
            case R.id.nav_hot:
                hideAllFragment(fragmentTransaction);
                if (mHotFragment == null) {
                    mHotFragment = new HotFragment();
                    fragmentTransaction.add(R.id.fl_layout, mHotFragment);
                } else {
                    fragmentTransaction.show(mHotFragment);
                }
                getSupportActionBar().setTitle("热门漫画");
                break;
            case R.id.nav_search:
                hideAllFragment(fragmentTransaction);
                if (mSearchFragment == null) {
                    mSearchFragment = new SearchFragment();
                    fragmentTransaction.add(R.id.fl_layout, mSearchFragment);
                } else {
                    fragmentTransaction.show(mSearchFragment);
                }
                getSupportActionBar().setTitle("搜索漫画");
                break;
            case R.id.setting:
                SettingFragment settingFragment = new SettingFragment();
                android.app.FragmentManager fragmentManager = getFragmentManager();
                android.app.FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.add(R.id.fl_layout, settingFragment);
                transaction.commit();
                break;
            case R.id.theme:

                break;
            case R.id.about:

                break;
        }
        mDrawerLayout.closeDrawer(GravityCompat.START);
        fragmentTransaction.commit();
        return true;
    }

    private void hideAllFragment(FragmentTransaction fragmentTransaction) {
        if (mMainFragment != null) {
            fragmentTransaction.hide(mMainFragment);
        }
        if (mCategoryFragment != null) {
            fragmentTransaction.hide(mCategoryFragment);
        }
        if (mHotFragment != null) {
            fragmentTransaction.hide(mHotFragment);
        }
        if (mSearchFragment != null) {
            fragmentTransaction.hide(mSearchFragment);
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
        if (AppUser.getCurrentUser() != null) {
            user_photo.setImageResource(R.mipmap.ic_launcher);
            user_name.setText(AppUser.getCurrentUser().getUsername());
            user_email.setVisibility(View.VISIBLE);
            user_email.setText(AppUser.getCurrentUser().getEmail());
        }
    }
}
