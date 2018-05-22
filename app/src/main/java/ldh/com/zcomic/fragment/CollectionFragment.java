package ldh.com.zcomic.fragment;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import ldh.com.zcomic.R;
import ldh.com.zcomic.adapter.ComicCollectAdapter;
import ldh.com.zcomic.base.BaseController;
import ldh.com.zcomic.base.BaseFragment;
import ldh.com.zcomic.base.ComicController;
import ldh.com.zcomic.base.UserController;
import ldh.com.zcomic.bean.ComicBean;
import ldh.com.zcomic.ui.ComicItemActivity;
import ldh.com.zcomic.utils.ActivityUtils;
import ldh.com.zcomic.utils.LogUtils;

/**
 * Created by allen liu on 2018/5/6.
 */

public class CollectionFragment extends BaseFragment implements View.OnClickListener {

    @BindView(R.id.btn_collect_delete)
    Button btnDelete;
    @BindView(R.id.lv_collect)
    ListView lvCollect;
    private UserController userController;
    private ComicController comicController;
    private List<String> collectList;
    private ComicCollectAdapter mCollectAdapter;
    private List<ComicBean> comicBeans;
    private ActivityUtils utils;

    @Override
    protected int getResRootViewId() {
        return R.layout.fragment_collect;
    }

    @Override
    protected void initData() {
        comicBeans = new ArrayList<>();
        utils = new ActivityUtils(this);
        userController = UserController.getInstance();
        comicController = ComicController.getInstance();
        initCollectData();
    }

    @Override
    protected void initListener() {
        btnDelete.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_collect_delete) {
            userController.deleteUserLove(mCollectAdapter.getSelected_comicId(), new BaseController.OnBmobCommonListener() {
                @Override
                public void onSuccess(String success) {
                    utils.showToast(success);
                    //更新UI
                    initCollectData();
                }

                @Override
                public void onError(String error) {
                    utils.showToast(error);
                }
            });
        }
    }

    public void initCollectData() {
        collectList = userController.getCollectId();
        LogUtils.i(collectList.size() + "条");
        comicController.query(collectList, new ComicController.OnBmobListener() {
            @Override
            public void onSuccess(final List<ComicBean> list) {
                LogUtils.i(list.size() + "条");
                comicBeans = selectSingle(list);
                LogUtils.i(comicBeans.size() + "条");
                btnDelete.setVisibility(View.VISIBLE);
                mCollectAdapter = new ComicCollectAdapter(getActivity(), comicBeans);
                lvCollect.setAdapter(mCollectAdapter);
                lvCollect.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent intent = new Intent(getActivity(), ComicItemActivity.class);
                        String url = comicBeans.get(position).getContentUrl();
                        intent.putExtra("comicItemUrl", url);
                        intent.putExtra("comicItemTitle", comicBeans.get(position).getTitle());
                        intent.putExtra("comicId", comicBeans.get(position).getComicId());
                        startActivity(intent);
                    }
                });
            }
            @Override
            public void onError(String error) {
                btnDelete.setVisibility(View.GONE);
                if (mCollectAdapter != null) {
                    mCollectAdapter.clear();
                }
            }
        });
    }
    private List<ComicBean> selectSingle(List<ComicBean> list) {
        for (int i = 0; i < list.size(); i++) {
            for (int j = list.size() - 1; j > i; j--) {
                if (list.get(j).getComicId().equals(list.get(i).getComicId())) {
                    list.remove(j);
                }
            }
        }
        return list;
    }
}
