package ldh.com.zcomic.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import butterknife.BindView;
import ldh.com.channelmanager.APPConst;
import ldh.com.channelmanager.ComicSource;
import ldh.com.zcomic.R;
import ldh.com.zcomic.adapter.FixedPagerAdapter;
import ldh.com.zcomic.base.BaseFragment;
import ldh.com.zcomic.ui.ChannelManagerActivity;
import ldh.com.zcomic.utils.DepthPageTransformer;
import ldh.com.zcomic.utils.ListDataSave;

/**
 * Created by allen liu on 2018/5/2.
 */

public class ClassifyFragment extends BaseFragment {
    @BindView(R.id.fragment_classify_tab)
    TabLayout mClassifyTab;
    @BindView(R.id.iv_add)
    ImageView mAddmore;
    @BindView(R.id.viewpager_classify)
    ViewPager mViewpager;
    List<Fragment> mFragmentList;
    List<String> mTitleList;
    private List<ComicSource> myChannelList;
    private List<ComicSource> moreChannelList;
    private FixedPagerAdapter fixedPagerAdapter;
    private SharedPreferences sharedPreferences;
    private ListDataSave listDataSave;
    private boolean isFirst;
    private ClassifyPagerFragment fragment;
    //当前位置
    private int tabPosition;
//    private String[] mStrings = {
//            "全部", "爆笑", "热血", "冒险", "恐怖", "科幻", "魔幻",
//            "玄幻", "校园", "悬疑", "推理", "萌系"};

    @Override
    protected int getResRootViewId() {
        return R.layout.fragment_classify;
    }

    @Override
    protected void initData() {
//        mTitleList = new ArrayList<>();
        mFragmentList = new ArrayList<>();
        sharedPreferences = getActivity().getSharedPreferences("Setting", Context.MODE_PRIVATE); //初始化
        listDataSave = new ListDataSave(getActivity(), "channel");//初始化
//        for (int i = 0; i < 12; i++) {
//            ClassifyPagerFragment fragment = ClassifyPagerFragment.newInstance(getMyChannel().get(i).getThemeId());
//            mFragmentList.add(fragment);
////            Log.i("class","fragment");
//            mTitleList.add(getMyChannel().get(i).getTitle());
//            mClassifyTab.addTab(mClassifyTab.newTab().setText(mTitleList.get(i)));
//        }
//        mViewpager.setAdapter(new ClassifyAdapter(getChildFragmentManager()));
//        mViewpager.setOffscreenPageLimit(mTitleList.size());
//        mViewpager.setPageTransformer(true, new DepthPageTransformer());
        fixedPagerAdapter = new FixedPagerAdapter(getChildFragmentManager());
        mClassifyTab.setupWithViewPager(mViewpager);
        bindData();
    }

    private void bindData() {
        getDataFromSharedPreference();
        fixedPagerAdapter.setChannelBean(myChannelList);
        fixedPagerAdapter.setFragments(mFragmentList);
        mViewpager.setPageTransformer(true, new DepthPageTransformer());
        mViewpager.setAdapter(fixedPagerAdapter);
    }
    /**
     * 判断是否第一次进入程序
     * 如果第一次进入，直接获取设置好的频道
     * 如果不是第一次进入，则从sharedPrefered中获取设置好的频道
     */
    private void getDataFromSharedPreference() {
        isFirst = sharedPreferences.getBoolean("isFirst", true);
        if (isFirst) {
            myChannelList = getMyChannel();
            moreChannelList = getMoreChannel();
            myChannelList = setType(myChannelList);
            moreChannelList = setType(moreChannelList);
            listDataSave.setDataList("myChannel", myChannelList);
            listDataSave.setDataList("moreChannel", moreChannelList);
            SharedPreferences.Editor edit = sharedPreferences.edit();
            edit.putBoolean("isFirst", false);
            edit.commit();
        } else {
            myChannelList = listDataSave.getDataList("myChannel", ComicSource.class);
        }
            mFragmentList.clear();
        for (int i = 0; i < myChannelList.size(); i++) {
            fragment = ClassifyPagerFragment.newInstance(myChannelList.get(i).getThemeId());
            mFragmentList.add(fragment);
        }
        if (myChannelList.size() <= 4) {
            mClassifyTab.setTabMode(TabLayout.MODE_FIXED);
        } else {
            mClassifyTab.setTabMode(TabLayout.MODE_SCROLLABLE);
        }

    }

    /**
     * 在ManiActivty中被调用，当从ChannelManagerActivity返回时设置当前tab的位置
     */
    public void setCurrentChannel(int tabPosition) {
        mViewpager.setCurrentItem(tabPosition);
        mClassifyTab.setScrollPosition(tabPosition, 1, true);
    }

    /**
     * 在myChannelList发生改变的时候更新ui，在MainActivity调用
     */
    public void notifyChannelChange() {
        getDataFromSharedPreference();
        fixedPagerAdapter.setChannelBean(myChannelList);
        fixedPagerAdapter.setFragments(mFragmentList);
        fixedPagerAdapter.notifyDataSetChanged();

    }

    private List<ComicSource> setType(List<ComicSource> list) {
        Iterator<ComicSource> iterator = list.iterator();
        while (iterator.hasNext()) {
            ComicSource channelBean = iterator.next();
            channelBean.setTabType(APPConst.ITEM_EDIT);
        }
        return list;
    }


    @Override
    protected void initListener() {
        mClassifyTab.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                tabPosition = tab.getPosition();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        mAddmore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ChannelManagerActivity.class);
                intent.putExtra("TABPOSITION", tabPosition);
                startActivityForResult(intent, 999);
            }
        });
    }
//  因为页面比较多，用FragmentStatePagerAdapter
//    class ClassifyAdapter extends FragmentPagerAdapter {
//        public ClassifyAdapter(FragmentManager fm) {
//            super(fm);
//        }
//
//        @Override
//        public Fragment getItem(int position) {
//            return mFragmentList.get(position);
//        }
//
//        @Override
//        public CharSequence getPageTitle(int position) {
//            return mTitleList.get(position);
//        }
//
//        @Override
//        public int getCount() {
//            return mTitleList.size();
//        }
//    }

    private List<ComicSource> getMyChannel(){
        List<ComicSource> list = new ArrayList<>();
        list.add(new ComicSource("全部", 0,0));
        list.add(new ComicSource("爆笑", 1,0));
        list.add(new ComicSource("热血", 2,0));
        list.add(new ComicSource("冒险", 3,0));
        list.add(new ComicSource("恐怖", 4,0));
        list.add(new ComicSource("科幻", 5,0));
        list.add(new ComicSource("魔幻", 6,0));
        list.add(new ComicSource("玄幻", 7,0));
        list.add(new ComicSource("校园", 8,0));
        list.add(new ComicSource("悬疑", 9,0));
        return list;
    }
    private List<ComicSource> getMoreChannel() {
        List<ComicSource> list = new ArrayList<>();
        list.add(new ComicSource("推理", 10,0));
        list.add(new ComicSource("萌系", 11,0));
        list.add(new ComicSource("穿越", 12,0));
        list.add(new ComicSource("后宫", 13,0));
        list.add(new ComicSource("都市", 14,0));
        list.add(new ComicSource("恋爱", 15,0));
        list.add(new ComicSource("武侠", 16,0));
        list.add(new ComicSource("格斗", 17,0));
        list.add(new ComicSource("战争", 18,0));
        list.add(new ComicSource("历史", 19,0));
        list.add(new ComicSource("彩虹", 20,0));
        list.add(new ComicSource("同人", 21,0));
        list.add(new ComicSource("竞技", 22,0));
        list.add(new ComicSource("励志", 23,0));
        list.add(new ComicSource("百合", 24,0));
        list.add(new ComicSource("治愈", 25,0));
        list.add(new ComicSource("机甲", 26,0));
        list.add(new ComicSource("纯爱", 27,0));
        list.add(new ComicSource("美食", 28,0));
        list.add(new ComicSource("血腥", 29,0));
        list.add(new ComicSource("僵尸", 30,0));
        list.add(new ComicSource("恶搞", 31,0));
        list.add(new ComicSource("虐心", 32,0));
        list.add(new ComicSource("生活", 33,0));
        list.add(new ComicSource("动作", 34,0));
        list.add(new ComicSource("惊险", 35,0));
        list.add(new ComicSource("唯美", 36,0));
        list.add(new ComicSource("震撼", 37,0));
        list.add(new ComicSource("复仇", 38,0));
        list.add(new ComicSource("侦探", 39,0));
        list.add(new ComicSource("其它", 40,0));
        list.add(new ComicSource("脑洞", 41,0));
        list.add(new ComicSource("奇幻", 42,0));
        list.add(new ComicSource("宫斗", 43,0));
        list.add(new ComicSource("搞笑", 44,0));
        list.add(new ComicSource("运动", 45,0));
        list.add(new ComicSource("青春", 46,0));
        list.add(new ComicSource("穿越", 47,0));
        list.add(new ComicSource("灵异", 48,0));
        list.add(new ComicSource("古风", 49,0));
        list.add(new ComicSource("权谋", 50,0));
        list.add(new ComicSource("节操", 51,0));
        list.add(new ComicSource("明星", 52,0));
        list.add(new ComicSource("暗黑", 53,0));
        list.add(new ComicSource("社会", 53,0));
        list.add(new ComicSource("浪漫", 55,0));
        return list;
    }
}

