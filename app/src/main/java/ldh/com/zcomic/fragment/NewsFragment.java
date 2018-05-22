package ldh.com.zcomic.fragment;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerListener;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import ldh.com.zcomic.R;
import ldh.com.zcomic.base.BaseFragment;
import ldh.com.zcomic.bean.ComicBean;
import ldh.com.zcomic.entity.Constants;
import ldh.com.zcomic.view.FrescoImageLoader;
import ldh.com.zcomic.ui.ComicPageActivity;
import ldh.com.zcomic.utils.DepthPageTransformer;
import ldh.com.zcomic.utils.JsoupUtils;
import ldh.com.zcomic.utils.OkHttpResultCallback;
import ldh.com.zcomic.utils.OkHttpUtil;
import okhttp3.Call;

/**
 * Created by allen liu on 2018/5/2.
 */

public class NewsFragment extends BaseFragment {
    @BindView(R.id.banner)
    Banner mBanner;
    @BindView(R.id.fragment_news_tab)
    TabLayout mNewsTab;
    @BindView(R.id.viewpager_news)
    ViewPager mViewpager;
    List<Fragment> mFragmentList;
    List<String> mTitleList;
    List<String> mBannerTitleList;
    List<String> mBannerImgList;
    private List<ComicBean> comicList;
    private NewsAdapter mNewsAdapter;
    @Override
    protected int getResRootViewId() {
        return R.layout.news_fragment;
    }
    @Override
    protected void initData() {
        comicList = new ArrayList<>();
        mTitleList = new ArrayList<>();
        mBannerTitleList = new ArrayList<>();
        mBannerImgList = new ArrayList<>();
        mBanner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE_INSIDE);
        mBanner.setImageLoader(new FrescoImageLoader());
        mBanner.isAutoPlay(true);
        mBanner.setDelayTime(3000);
        mBanner.setIndicatorGravity(BannerConfig.RIGHT);
        mBanner.setBannerAnimation(Transformer.DepthPage);
        getComicBannerData();

        mTitleList.add("动漫资讯");
        mTitleList.add("八卦杂谈");
        mFragmentList = new ArrayList<>();
        NewsPagerFragment newsPagerFragment = new NewsPagerFragment();
        mFragmentList.add(newsPagerFragment);
        NewsExtraPagerFragment newsExtraPagerFragment = new NewsExtraPagerFragment();
        mFragmentList.add(newsExtraPagerFragment);
        mNewsTab.setTabMode(TabLayout.MODE_FIXED);
        mNewsAdapter = new NewsAdapter(getChildFragmentManager());
        mViewpager.setAdapter(mNewsAdapter);
        mViewpager.setPageTransformer(true, new DepthPageTransformer());
        mNewsTab.setupWithViewPager(mViewpager);
    }

    @Override
    protected void initListener() {
        mBanner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                Intent intent = new Intent(getActivity(), ComicPageActivity.class);
                intent.putExtra("url", comicList.get(position).getContentUrl());
                intent.putExtra("title", comicList.get(position).getTitle());
                startActivity(intent);
            }
        });
    }
    class NewsAdapter extends FragmentPagerAdapter {
        public NewsAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTitleList.get(position);
        }

        @Override
        public int getCount() {
            return mTitleList.size();
        }
    }
    private void getComicBannerData() {
        String url= Constants.COMIC_BANNER;
        OkHttpUtil.getInstance().getAsync(url, new OkHttpResultCallback() {
            @Override
            public void onError(Call call, Exception e) {
                e.getMessage();
            }

            @Override
            public void onResponse(byte[] bytes) {
                try {
                    String s = new String(bytes, "utf-8");
                    List<ComicBean> list = JsoupUtils.getInstance().getComicBannerData(s);
                    getCategoryComicSuccess(list);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
        });
    }
    private void getCategoryComicSuccess(List<ComicBean> list) {
          comicList= list;
        for (ComicBean bean : list) {
            mBannerImgList.add(bean.getImgUrl());
            mBannerTitleList.add(bean.getTitle());
        }
        mBanner.setImages(mBannerImgList);
        mBanner.setBannerTitles(mBannerTitleList);
        mBanner.start();
    }
}
