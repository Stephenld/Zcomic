package ldh.com.zcomic.fragment;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import ldh.com.zcomic.R;
import ldh.com.zcomic.adapter.BasePagerAdapter;
import ldh.com.zcomic.adapter.ComicNewsPagerAdapter;
import ldh.com.zcomic.base.BaseFragment;
import ldh.com.zcomic.bean.ComicBean;
import ldh.com.zcomic.entity.Constants;
import ldh.com.zcomic.ui.ComicPageActivity;
import ldh.com.zcomic.utils.JsoupUtils;
import ldh.com.zcomic.utils.OkHttpResultCallback;
import ldh.com.zcomic.utils.OkHttpUtil;
import okhttp3.Call;

/**
 * Created by allen liu on 2018/5/13.
 */

public class NewsExtraPagerFragment extends BaseFragment {
    @BindView(R.id.news_pager_fresh)
    SwipeRefreshLayout mSrNews;
    @BindView(R.id.news_comic_rc)
    RecyclerView mRvNews;
    private List<ComicBean> mList;
    private ComicNewsPagerAdapter mNewsAdapter;

    @Override
    protected int getResRootViewId() {
        return R.layout.newspager_fragment;
    }

    @Override
    protected void initData() {
        mList = new ArrayList<>();
        mNewsAdapter = new ComicNewsPagerAdapter();
        getClassifyComic();
        mRvNews.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));
        mRvNews.setItemAnimator(new DefaultItemAnimator());
        mRvNews.setAdapter(mNewsAdapter);
    }

    @Override
    protected void initListener() {
        mSrNews.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mSrNews.setRefreshing(true);
                getClassifyComic();
            }
        });
        mNewsAdapter.setOnRecyclerViewListener(new BasePagerAdapter.OnClickRecyclerViewListener() {
            @Override
            public void onItemClick(int position) {
                Intent intent = new Intent(getActivity(), ComicPageActivity.class);
                intent.putExtra("url","http://comic.qq.com"+mList.get(position).getContentUrl());
                intent.putExtra("title",mList.get(position).getTitle());
                startActivity(intent);
            }
            @Override
            public boolean onItemLongClick(int position) {
                return false;
            }
        });
    }

    private void getClassifyComic() {
        String url= Constants.COMIC_Extra;
        OkHttpUtil.getInstance().getAsync(url, new OkHttpResultCallback() {
            @Override
            public void onError(Call call, Exception e) {
                e.getMessage();
            }

            @Override
            public void onResponse(byte[] bytes) {
                try {
                    String s = new String(bytes, "GBK");
                    List<ComicBean> list = JsoupUtils.getInstance().getComicExtraData(s);
                    getCategoryComicSuccess(list);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
        });
    }
    private void getCategoryComicSuccess(List<ComicBean> list) {
        if (mSrNews != null && mSrNews.isRefreshing()){
            mSrNews.setRefreshing(false);
        }
        mList = list;
        mNewsAdapter.updateData(mList);
    }
}
