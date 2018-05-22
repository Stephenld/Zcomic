package ldh.com.zcomic.ui;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.LazyHeaders;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import ldh.com.zcomic.R;
import ldh.com.zcomic.adapter.ChapterAdapter;
import ldh.com.zcomic.base.BaseActivity;
import ldh.com.zcomic.base.BaseController;
import ldh.com.zcomic.base.UserController;
import ldh.com.zcomic.bean.ChapterBean;
import ldh.com.zcomic.bean.ComicItem;
import ldh.com.zcomic.utils.ActivityUtils;
import ldh.com.zcomic.utils.JsoupUtils;
import ldh.com.zcomic.utils.OkHttpResultCallback;
import ldh.com.zcomic.utils.OkHttpUtil;
import ldh.com.zcomic.view.FastBlur;
import okhttp3.Call;

/**
 * Created by allen liu on 2018/5/12.
 */

public class ComicItemActivity extends BaseActivity {
    @BindView(R.id.appbar)
    AppBarLayout appbar;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.progress)
    ProgressBar progressBar;
    @BindView(R.id.sl_content)
    NestedScrollView slContent;
    @BindView(R.id.rl_content)
    RelativeLayout r1Content;
    @BindView(R.id.iv_blur)
    ImageView ivBlur;
    @BindView(R.id.rl_cover)
    RelativeLayout rlCover;
    @BindView(R.id.iv_cover)
    ImageView ivCover;
    @BindView(R.id.tv_comic_name)
    TextView tvComicName;
    @BindView(R.id.iv_collect)
    ImageView ivCollect;
    @BindView(R.id.tv_comic_author)
    TextView tvComicAuthor;
    @BindView(R.id.tv_comic_status)
    TextView tvComicStatus;
    @BindView(R.id.tv_comic_score)
    TextView tvComicScore;
    @BindView(R.id.tv_comic_time)
    TextView tvComicTime;
    @BindView(R.id.tv_comic_desc)
    TextView tvComicDesc;
    @BindView(R.id.tv_chapter)
    TextView tvChapter;
    @BindView(R.id.gv_chapter)
    GridView gvChapter;
    @BindView(R.id.rl_locationUp)
    RelativeLayout rlLocationUp;
    @BindView(R.id.rl_locationBottom)
    RelativeLayout rlLocationBottom;
    @BindView(R.id.rl_main)
    RelativeLayout rlMain;
    @BindView(R.id.rl_collect)
    RelativeLayout rlCollect;

    private String mUrl;
    private String mTitle;
    private String comicId;
    private ChapterAdapter mAdapter;
    private List<ChapterBean> mList;
    private UserController userController;
    private ActivityUtils utils;

    @Override
    protected int setLayoutResID() {
        return R.layout.activity_comic_details;
    }

    @Override
    protected void initView() {
        userController = UserController.getInstance();
        progressBar.setVisibility(View.VISIBLE);
        mList = new ArrayList<>();
        utils = new ActivityUtils(this);
        mUrl = getIntent().getStringExtra("comicItemUrl");
        mTitle = getIntent().getStringExtra("comicItemTitle");
        comicId = getIntent().getStringExtra("comicId");
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(mTitle);
        getComicDetailData(mUrl);
        initCollectIcon();
    }

    private void initCollectIcon() {
        if(userController.getCollectId().contains(comicId)){
            ivCollect.setBackgroundResource(R.drawable.comic_collect_on);
        } else{
            ivCollect.setBackgroundResource(R.drawable.comic_collect_off);
        }
    }

    private void getComicDetailData(String mUrl) {
        String url ="http://ac.qq.com"+ mUrl;
        OkHttpUtil.getInstance().getAsync(url, new OkHttpResultCallback() {
            @Override
            public void onError(Call call, Exception e) {
                e.getMessage();
            }
            @Override
            public void onResponse(byte[] bytes) {
                try {
                    String s = new String(bytes, "utf-8");
                    ComicItem comicDetailData = JsoupUtils.getInstance().getComicDetailData(s);
                    getCategoryComicSuccess(comicDetailData);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
        });
    }
    private void getCategoryComicSuccess(ComicItem comicItem) {
        progressBar.setVisibility(View.GONE);
        tvComicName.setText(comicItem.getTitle());
        tvComicAuthor.setText("作者："+comicItem.getAuthor());
        tvComicStatus.setText("状态: "+ comicItem.getStatus());
        tvComicScore.setText("评分："+comicItem.getScore());
        tvComicDesc.setText("简介："+comicItem.getSummary());
        tvComicTime.setText("更新："+comicItem.getUpdates());

        Glide.with(this).asBitmap().load(new GlideUrl(comicItem.getImgUrl(), new LazyHeaders.Builder()
                .addHeader("comic", comicItem.getImgUrl())
                .build())).into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                ivBlur.setImageBitmap(FastBlur.doBlur(resource, 10, false));
            }
        });
        Glide.with(this).load(comicItem.getImgUrl()).into(ivCover);

        mList = comicItem.getChapterList();
        mAdapter = new ChapterAdapter(mList,this);
        gvChapter.setAdapter(mAdapter);
        gvChapter.setSelector(new ColorDrawable(Color.TRANSPARENT));
    }

    @Override
    protected void initListener() {
        rlLocationUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                slContent.scrollTo(0,0);
            }
        });
        rlLocationBottom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                slContent.fullScroll(ScrollView.FOCUS_DOWN);
            }
        });
        rlCollect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userController.addUserCollect(comicId, ivCollect, new BaseController.onBmobUserListener() {
                    @Override
                    public void onSuccess(String success) {
                        utils.showToast(success);
                    }

                    @Override
                    public void onError(String error) {
                        utils.showToast(error);
                    }

                    @Override
                    public void onLoading(String loading) {
                        utils.showToast(loading);
                    }
                });
            }
        });
        gvChapter.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(ComicItemActivity.this, ComicPageActivity.class);
                intent.putExtra("url", "http://ac.qq.com"+ mList.get(position).getUrl());
                intent.putExtra("title", mList.get(position).getTitle());
                startActivity(intent);
            }
        });
    }
}
