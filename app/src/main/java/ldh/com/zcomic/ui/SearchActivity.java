package ldh.com.zcomic.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ldh.com.zcomic.R;
import ldh.com.zcomic.adapter.BasePagerAdapter;
import ldh.com.zcomic.adapter.ComicPagerAdapter;
import ldh.com.zcomic.bean.ComicBean;
import ldh.com.zcomic.entity.Constants;
import ldh.com.zcomic.utils.ActivityUtils;
import ldh.com.zcomic.utils.JsoupUtils;
import ldh.com.zcomic.utils.OkHttpResultCallback;
import ldh.com.zcomic.utils.OkHttpUtil;
import ldh.com.zcomic.utils.ViewUtil;
import ldh.com.zcomic.view.EditTextWithDel;
import okhttp3.Call;

/**
 * Created by allen liu on 2018/5/6.
 */

public class SearchActivity extends AppCompatActivity {
    @BindView(R.id.ib_search)
    ImageButton ib_search;
    @BindView(R.id.et_search)
    EditTextWithDel et_search;
    @BindView(R.id.search_rv)
    RecyclerView search_rv;
    @BindView(R.id.search_pb)
    ProgressBar search_pb;
    private List<ComicBean> mList;
    private  ComicPagerAdapter mSearchAdapter;
    private ActivityUtils utils;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_activity);
        ButterKnife.bind(this);
        utils = new ActivityUtils(this);
    }
    @Override
    protected void onStart() {
        super.onStart();
        //屏幕亮度
        Constants.osScreenBrightValue = ViewUtil.getScreenBrightness(this);
        if (Constants.osNightModel){
            ViewUtil.setScreenBrightness(this, 2);
        }else {
            ViewUtil.setScreenBrightness(this, Constants.osScreenBrightValue);
        }
    }
    @OnClick(R.id.ib_search)
    public void onViewClicked() {
        if (TextUtils.isEmpty(et_search.getText().toString())){
            utils.showToast("请输入您要搜索的关键词");
        }else {
            SearchComic(et_search.getText().toString());
        }
    }
      private void SearchComic(String query) {
        search_pb.setVisibility(View.VISIBLE);
        String keyUrlEncode = null;
        try {
            keyUrlEncode = URLEncoder.encode(query,"utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
            String url= Constants.COMIC_SEARCH+keyUrlEncode;
            OkHttpUtil.getInstance().getAsync(url, new OkHttpResultCallback() {
                @Override
                public void onError(Call call, Exception e) {
                    e.getMessage();
                }

                @Override
                public void onResponse(byte[] bytes) {
                    try {
                        String s = new String(bytes, "utf-8");
                        List<ComicBean> list = JsoupUtils.getInstance().getComicSearchData(s);
                        getSearchComicSuccess(list);
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    private void getSearchComicSuccess(List< ComicBean > list) {
        search_pb.setVisibility(View.GONE);
        mList = list;
        mSearchAdapter = new ComicPagerAdapter();
        mSearchAdapter.updateData(mList);
        search_rv.setLayoutManager( new GridLayoutManager(this,3));
        search_rv.setItemAnimator(new DefaultItemAnimator());
        search_rv.setAdapter(mSearchAdapter);

        mSearchAdapter.setOnRecyclerViewListener(new BasePagerAdapter.OnClickRecyclerViewListener() {
            @Override
            public void onItemClick(int position) {
                Intent intent = new Intent(SearchActivity.this,ComicItemActivity.class);
                String url = mList.get(position).getContentUrl();
                intent.putExtra("comicItemUrl",url.substring(url.indexOf("/Comic")));
                intent.putExtra("comicItemTitle",mList.get(position).getTitle());
                startActivity(intent);
            }

            @Override
            public boolean onItemLongClick(int position) {
                return false;
            }
        });
    }
}

