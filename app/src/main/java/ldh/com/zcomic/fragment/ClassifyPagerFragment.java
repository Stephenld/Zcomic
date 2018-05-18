package ldh.com.zcomic.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import ldh.com.zcomic.R;
import ldh.com.zcomic.adapter.BasePagerAdapter;
import ldh.com.zcomic.adapter.ComicLoadMoreAdapter;
import ldh.com.zcomic.adapter.ComicPagerAdapter;
import ldh.com.zcomic.base.BaseFragment;
import ldh.com.zcomic.bean.ComicBean;
import ldh.com.zcomic.entity.Constants;
import ldh.com.zcomic.ui.ComicItemActivity;
import ldh.com.zcomic.utils.JsoupUtils;
import ldh.com.zcomic.utils.OkHttpResultCallback;
import ldh.com.zcomic.utils.OkHttpUtil;
import okhttp3.Call;

/**
 * Created by allen liu on 2018/5/8.
 */

public class ClassifyPagerFragment extends BaseFragment implements ComicLoadMoreAdapter.OnLoadMoreDataRv {
    @BindView(R.id.classify_pager_fresh)
    SwipeRefreshLayout mSrClassify;
    @BindView(R.id.classify_pager_rv)
    RecyclerView mRvClassify;
    @BindView(R.id.classify_pager_fab)
    FloatingActionButton mFaButton;

    private int mType = 0;
    private int mPageNum = 0;
    private List<ComicBean> mList;
    private ComicPagerAdapter mAdapter;
    private ComicLoadMoreAdapter mMoreAdapter;
    public static ClassifyPagerFragment newInstance(int index){
        ClassifyPagerFragment fragment = new ClassifyPagerFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("fragmentID", index);
        fragment.setArguments(bundle);
        return fragment;
    }
    @Override
    protected int getResRootViewId() {
        return R.layout.classifypager_fragment;
    }
    @Override
    protected void initData() {
        mType = getArguments().getInt("fragmentID");
        getClassifyComic(mType,0);
        mList = new ArrayList<>();
        mAdapter = new ComicPagerAdapter();
        mMoreAdapter=new ComicLoadMoreAdapter(mAdapter,  this);
        mMoreAdapter.updateData(mList);
        mRvClassify.setLayoutManager(new GridLayoutManager(getContext(), 3));
        mRvClassify.setItemAnimator(new DefaultItemAnimator());
        mRvClassify.setAdapter(mMoreAdapter);
    }
    @Override
    protected void initListener() {
        mSrClassify.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mSrClassify.setRefreshing(true);
                mPageNum = 1;
                getClassifyComic(mType,mPageNum);
            }
        });
        mFaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mRvClassify.getAdapter() != null) {
                    mRvClassify.smoothScrollToPosition(0);
                }
            }
        });

        mAdapter.setOnRecyclerViewListener(new BasePagerAdapter.OnClickRecyclerViewListener() {
            @Override
            public void onItemClick(int position) {
                Intent intent = new Intent(getActivity(), ComicItemActivity.class);
                String url = mList.get(position).getContentUrl();
                intent.putExtra("comicItemUrl",url);
                intent.putExtra("comicItemTitle",mList.get(position).getTitle());
                intent.putExtra("comicId",mList.get(position).getComicId());
                startActivity(intent);
            }
            @Override
            public boolean onItemLongClick(int position) {
                return false;
            }
        });

    }
    private void getClassifyComic(int mType,int pageNum) {
        String url= Constants.COMIC_All_HEAD+mType+Constants.COMIC_ALL_END+pageNum;
        OkHttpUtil.getInstance().getAsync(url, new OkHttpResultCallback() {
            @Override
            public void onError(Call call, Exception e) {
               e.getMessage();
            }

            @Override
            public void onResponse(byte[] bytes) {
                try {
                    String s = new String(bytes, "utf-8");
                    List<ComicBean> list = JsoupUtils.getInstance().getComicAllData(s);
                     getCategoryComicSuccess(list);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void loadMoreData() {
        mPageNum++;
        getClassifyComic(mType,mPageNum);
    }

    private void getCategoryComicSuccess(List<ComicBean> list) {
        if (mSrClassify != null && mSrClassify.isRefreshing()){
            mSrClassify.setRefreshing(false);
        }
        if (list == null || list.size() == 0){
            mMoreAdapter.setHasMoreData(false);
        }else {
            if (mPageNum == 1){
                mList = list;
                mAdapter.updateData(mList);
            }else {
                mList.addAll(list);
                mAdapter.appendData(list);
            }
        }
        mMoreAdapter.notifyDataSetChanged();
    }
}
