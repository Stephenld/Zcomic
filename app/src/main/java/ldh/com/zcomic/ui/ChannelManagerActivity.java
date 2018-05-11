package ldh.com.zcomic.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import ldh.com.channelmanager.APPConst;
import ldh.com.channelmanager.ComicSource;
import ldh.com.channelmanager.adapter.ChannelAdapter;
import ldh.com.channelmanager.base.IChannelType;
import ldh.com.channelmanager.utils.GridItemDecoration;
import ldh.com.zcomic.R;
import ldh.com.zcomic.utils.ListDataSave;
import ldh.com.zcomic.utils.SharedPreUtils;

/**
 * Created by allen liu on 2018/5/11.
 */

public class ChannelManagerActivity extends AppCompatActivity implements  ChannelAdapter.ChannelItemClickListener{
    private RecyclerView mRecyclerView;
    private ChannelAdapter mRecyclerAdapter;
    private List<ComicSource> mMyChannelList;
    private List<ComicSource> mRecChannelList;
    private Context context;
    private int tabposition;
    private ListDataSave listDataSave;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int theme_id = SharedPreUtils.getInt(this,"theme_id", R.style.AppTheme);
        setTheme(theme_id);
        setContentView(R.layout.activity_channel_manager);
        getIntentData();
        context = this;
        initToolbar();
        listDataSave = new ListDataSave(this, "channel");
        mRecyclerView = findViewById(ldh.com.channelmanager.R.id.id_tab_recycler_view);
        GridLayoutManager gridLayout = new GridLayoutManager(context, 4);
        gridLayout.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                boolean isHeader = mRecyclerAdapter.getItemViewType(position) == IChannelType.TYPE_MY_CHANNEL_HEADER ||
                        mRecyclerAdapter.getItemViewType(position) == IChannelType.TYPE_REC_CHANNEL_HEADER;
                return isHeader ? 4 : 1;
            }
        });
        mRecyclerView.setLayoutManager(gridLayout);
        mRecyclerView.addItemDecoration(new GridItemDecoration(APPConst.ITEM_SPACE));
        initData();
        mRecyclerAdapter = new ChannelAdapter(context, mRecyclerView, mMyChannelList, mRecChannelList, 1, 1);
        mRecyclerAdapter.setChannelItemClickListener(this);
        mRecyclerView.setAdapter(mRecyclerAdapter);
    }
    private void getIntentData(){
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        tabposition = bundle.getInt("TABPOSITION");
    }

    private void initToolbar(){
        Toolbar toolbar =  findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("频道管理");
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }
    private void initData() {
        mMyChannelList = new ArrayList<>();

        List<ComicSource> list = listDataSave.getDataList("myChannel", ComicSource.class);
        for (int i = 0; i < list.size(); i ++){
            ComicSource comicChannelBean = list.get(i);
            if (i == tabposition){
                comicChannelBean.setTabType(APPConst.ITEM_DEFAULT);
            } else {
                // 判断i是否为0或者1,如果为0设置标题为红色（当前浏览的tab标签），如果为1则设置type为1（不可编辑移动），不为1则type为2
                // type为2表示该标签可供编辑移动
                int type;
                if (i == 0  || i == 1){
                    type = 1;
                } else {
                    type = 2;
                }
                comicChannelBean.setTabType(type);
            }
            mMyChannelList.add(comicChannelBean);
        }

        mRecChannelList = new ArrayList<>();
        List<ComicSource> moreChannelList = listDataSave.getDataList("moreChannel", ComicSource.class);
        for (ComicSource comicChannelBean : moreChannelList) {
            mRecChannelList.add(comicChannelBean);
        }
    }
    @Override
    protected void onPause() {

        Iterator<ComicSource> iterator = mMyChannelList.iterator();
        while (iterator.hasNext()){
            ComicSource projectChannelBean = iterator.next();
            // 将当前模式设置为不可编辑状态
            projectChannelBean.setEditStatus(0);
        }
        listDataSave.setDataList("myChannel", mMyChannelList);
        listDataSave.setDataList("moreChannel", mRecChannelList);

        super.onPause();
    }

    @Override
    public void finish() {
        mRecyclerAdapter.doCancelEditMode(mRecyclerView);

        for (int i = 0; i < mMyChannelList.size(); i ++) {
            ComicSource comicChannelBean = mMyChannelList.get(i);
            if (comicChannelBean.getTabType() == 0){
                tabposition = i;
            }
        }
        Intent intent = new Intent();
        intent.putExtra("NewTabPostion", tabposition);
        setResult(789, intent);

        super.finish();
    }
    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
    @Override
    public void onChannelItemClick(List<ldh.com.channelmanager.ComicSource> list, int position) {

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
