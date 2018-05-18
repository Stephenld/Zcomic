package ldh.com.zcomic.fragment;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

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

    @Override
    protected int getResRootViewId() {
        return R.layout.fragment_collect;
    }

    @Override
    protected void initData() {
        comicBeans = new ArrayList<>();
        userController = UserController.getInstance();
        comicController = ComicController.getInstance();
//        initCollectList(collectList);
        initCollectData();
    }

//    private void initCollectList(final List<String> collectList) {
//
//        if (collectList != null) {
//            comicController = ComicController.getInstance();
//            comicController.query(collectList, new ComicController.OnBmobListener() {
//                @Override
//                public void onSuccess(final List<ComicBean> list) {
////                    for(int i=0;i<selectSingle(list).size();i++) {
//                        comicBeans.add(selectSingle(list).get(i));
//                    }
//                    comicBeans = selectSingle(list);
//                    btnDelete.setVisibility(View.VISIBLE);
//                    mCollectAdapter = new ComicCollectAdapter(getActivity(), comicBeans);
//                    lvCollect.setAdapter(mCollectAdapter);
//
//                    mCollectAdapter.setOnRecyclerViewListener(new BasePagerAdapter.OnClickRecyclerViewListener() {
//                        @Override
//                        public void onItemClick(int position) {
//                            Intent intent = new Intent(getActivity(), ComicItemActivity.class);
//                            String url = comicBeans.get(position).getContentUrl();
//                            intent.putExtra("comicItemUrl", url);
//                            intent.putExtra("comicItemTitle", comicBeans.get(position).getTitle());
//                            intent.putExtra("comicId", comicBeans.get(position).getComicId());
//                            startActivity(intent);
//                        }
//
//                        @Override
//                        public boolean onItemLongClick(final int position) {
////                            DialogUtils.showStandardDialog(getActivity(), "", "是否从收藏中删除漫画[ " + comicBeans.get(position).getTitle() + " ]")
////                                    .setNegativeButton("取消", new DialogInterface.OnClickListener() {
////                                        @Override
////                                        public void onClick(DialogInterface dialog, int which) {
////                                            dialog.dismiss();
////                                        }
////                                    })
////                                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
////                                        @Override
////                                        public void onClick(DialogInterface dialog, int which) {
////                                            Log.i("collection",comicBeans.get(position).getComicId());
////                                            userController.deleteUserLove(comicBeans.get(position).getObjectId(), new BaseController.OnBmobCommonListener() {
////                                                @Override
////                                                public void onSuccess(String success) {
////                                                    Toast.makeText(getActivity(), success, Toast.LENGTH_SHORT).show();
////                                                    comicBeans.remove(position);
////                                                    Log.i("collection",position+"a");
////                                                    collectList.remove(position);
////                                                    Log.i("collection",position+"h");
//////                                                    userController.updateUserLove(collectList);
//////                                                    userController.clearUserLove();
////
////                                                    userController.getCurrentUser().collect_comicId = collectList;
////                                                    userController.getCurrentUser().update(new UpdateListener() {
////                                                        @Override
////                                                        public void done(BmobException e) {
////                                                        }
////                                                    });
////
////                                                    Log.i("collection",position+"z");
////                                                    initCollectList(collectList);
////                                                }
////                                                @Override
////                                                public void onError(String error) {
////                                                    Toast.makeText(getActivity(), error, Toast.LENGTH_SHORT).show();
////                                                }
////                                            });
////                                        }
////                                    })
////                                    .show();
//                            return true;
//                        }
//                    });

//                }

//                @Override
//                public void onError(String error) {
//                    Log.i("CollectionFragment", error);
//                }
//            });
//        } else {
//            Toast.makeText(getActivity(), "收藏为空", Toast.LENGTH_SHORT).show();
//        }
//
//
//    }

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
                    Toast.makeText(getActivity(), success, Toast.LENGTH_SHORT).show();
                    //更新UI
                    initCollectData();
                }

                @Override
                public void onError(String error) {
                    Toast.makeText(getActivity(), error, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    public void initCollectData() {
        collectList = userController.getCollectId();
        Log.i("ColectionFragmenta", collectList.size() + "条");
        comicController.query(collectList, new ComicController.OnBmobListener() {
            @Override
            public void onSuccess(final List<ComicBean> list) {
                Log.i("ColectionFragmentb", list.size() + "条");
                comicBeans = selectSingle(list);
                Log.i("ColectionFragmentc", comicBeans.size() + "条");
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
