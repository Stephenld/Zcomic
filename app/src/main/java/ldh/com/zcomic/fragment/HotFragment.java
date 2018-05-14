package ldh.com.zcomic.fragment;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import ldh.com.zcomic.R;
import ldh.com.zcomic.adapter.BasePagerAdapter;
import ldh.com.zcomic.adapter.ComicHotPagerAdapter;
import ldh.com.zcomic.adapter.ComicLoadMoreAdapter;
import ldh.com.zcomic.base.BaseFragment;
import ldh.com.zcomic.bean.ComicBean;
import ldh.com.zcomic.entity.Constants;
import ldh.com.zcomic.ui.ComicItemActivity;
import ldh.com.zcomic.utils.JsoupUtils;
import ldh.com.zcomic.utils.OkHttpResultCallback;
import ldh.com.zcomic.utils.OkHttpUtil;
import okhttp3.Call;

/**
 * Created by allen liu on 2018/5/2.
 */

public class HotFragment extends BaseFragment implements ComicLoadMoreAdapter.OnLoadMoreDataRv{
    @BindView(R.id.hot_pager_fresh)
    SwipeRefreshLayout mSrHot;
    @BindView(R.id.hot_comic_rc)
    RecyclerView mRvHot;
    private int mPageNum = 0;
    private List<ComicBean> mList;
    private ComicHotPagerAdapter mHotAdapter;
    private ComicLoadMoreAdapter mMoreAdapter;

    @Override
    protected int getResRootViewId() {
        return R.layout.fragment_hot;
    }
    @Override
    protected void initData() {
        mList = new ArrayList<>();
        mHotAdapter = new ComicHotPagerAdapter();
        mMoreAdapter = new ComicLoadMoreAdapter(mHotAdapter, this);
        mMoreAdapter.updateData(mList);
        mRvHot.setLayoutManager(new GridLayoutManager(getContext(), 2));
        mRvHot.setItemAnimator(new DefaultItemAnimator());
        mRvHot.setAdapter(mMoreAdapter);
    }

    @Override
    protected void initListener() {
        mSrHot.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mSrHot.setRefreshing(true);
                mPageNum = 1;
                getClassifyComic(mPageNum);
            }
        });
        mHotAdapter.setOnRecyclerViewListener(new BasePagerAdapter.OnClickRecyclerViewListener() {
            @Override
            public void onItemClick(int position) {
                Intent intent = new Intent(getActivity(), ComicItemActivity.class);
                String url = mList.get(position).getContentUrl();
                intent.putExtra("comicItemUrl", url.substring(url.indexOf("/Comic")));
                intent.putExtra("comicItemTitle",mList.get(position).getTitle());
                startActivity(intent);
            }
            @Override
            public boolean onItemLongClick(int position) {
                return false;
            }
        });
    }
    @Override
    public void loadMoreData() {
        mPageNum++;
        getClassifyComic(mPageNum);
    }
    private void getClassifyComic(int mPageNum) {
        String url= Constants.COMIC_JAPAN+mPageNum;
        OkHttpUtil.getInstance().getAsync(url, new OkHttpResultCallback() {
            @Override
            public void onError(Call call, Exception e) {
                e.getMessage();
            }

            @Override
            public void onResponse(byte[] bytes) {
                try {
                    String s = new String(bytes, "utf-8");
                    List<ComicBean> list = JsoupUtils.getInstance().getComicHotData(s);
                    getCategoryComicSuccess(list);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
        });
    }
    private void getCategoryComicSuccess(List<ComicBean> list) {
        if (mSrHot != null && mSrHot.isRefreshing()){
            mSrHot.setRefreshing(false);
        }
        if (list == null || list.size() == 0){
            mMoreAdapter.setHasMoreData(false);
        }else {
            if (mPageNum == 1){
                mList = list;
                mHotAdapter.updateData(mList);
            }else {
                mList.addAll(list);
                mHotAdapter.appendData(list);
            }
        }
        mMoreAdapter.notifyDataSetChanged();
    }
}
