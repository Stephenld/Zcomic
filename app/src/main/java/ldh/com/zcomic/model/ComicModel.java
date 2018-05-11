package ldh.com.zcomic.model;

/**
 * Created by allen liu on 2018/5/7.
 */

public class ComicModel{
    //分类页面
//    public void getClassifyComic(int pageNum) {
//        String url= Constants.COMIC_All + pageNum;
//        OkHttpUtil.getInstance().getAsync(url, new OkHttpResultCallback() {
//            @Override
//            public void onError(Call call, Exception e) {
//                mPresenter.getError(e.getMessage());
//            }
//
//            @Override
//            public void onResponse(byte[] bytes) {
//                try {
//                    String s = new String(bytes, "utf-8");
//                    List<ComicBean> list = JsoupUtils.getInstance().getComicAllData(s);
//                    mPresenter.getCategoryComicSuccess(list);
//                } catch (UnsupportedEncodingException e) {
//                    e.printStackTrace();
//                }
//            }
//        });
//    }
//    //热门日漫
//    public void getHotComic(int pageNum) {
//        String url= Constants.COMIC_JAPAN + pageNum;
//        OkHttpUtil.getInstance().getAsync(url, new OkHttpResultCallback() {
//            @Override
//            public void onError(Call call, Exception e) {
//                mPresenter.getError(e.getMessage());
//            }
//            @Override
//            public void onResponse(byte[] bytes) {
//                try {
//                    String s = new String(bytes,"utf-8");
//                    List<ComicBean> list = JsoupUtils.getInstance().getComicHotData(s);
//                    if (list != null){
//                        mPresenter.getHotComicSuccess(list);
//                    }else {
//                        mPresenter.getError("获取热门漫画数据出错啦....");
//                    }
//                } catch (UnsupportedEncodingException e) {
//                    e.printStackTrace();
//                }
//            }
//        });
//    }
//    //漫画资讯
//    public void getNewsComic() {
//        OkHttpUtil.getInstance().getAsync(Constants.COMIC_NEWS, new OkHttpResultCallback() {
//            @Override
//            public void onError(Call call, Exception e) {
//                mPresenter.getError(e.getMessage());
//            }
//            @Override
//            public void onResponse(byte[] bytes) {
//                try {
//                    String s = new String(bytes,"utf-8");
//                    List<ComicBean> list = JsoupUtils.getInstance().getComicNewsData(s);
//                    if (list != null){
//                        mPresenter.getNewsComicSuccess(list);
//                    }else {
//                        mPresenter.getError("获取热门漫画数据出错啦....");
//                    }
//                } catch (UnsupportedEncodingException e) {
//                    e.printStackTrace();
//                }
//            }
//        });
//    }
//    //最新更新
//    public void getUpdateComic() {
//        OkHttpUtil.getInstance().getAsync(Constants.COMIC_UPDATE, new OkHttpResultCallback() {
//            @Override
//            public void onError(Call call, Exception e) {
//                mPresenter.getError(e.getMessage());
//            }
//            @Override
//            public void onResponse(byte[] bytes) {
//                try {
//                    String s = new String(bytes,"utf-8");
//                    List<ComicBean> list = JsoupUtils.getInstance().getComicUpdateData(s);
//                    if (list != null){
//                        mPresenter.getUpdateComicSuccess(list);
//                    }else {
//                        mPresenter.getError("获取热门漫画数据出错啦....");
//                    }
//                } catch (UnsupportedEncodingException e) {
//                    e.printStackTrace();
//                }
//            }
//        });
//    }
//
//    //搜索页面
//    public void getSearchComic(String key) {
//        String keyUrlEncode = null;
//        try {
//            keyUrlEncode = URLEncoder.encode(key,"utf-8");
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }
//        OkHttpUtil.getInstance().getAsync(Constants.COMIC_SEARCH + keyUrlEncode, new OkHttpResultCallback() {
//            @Override
//            public void onError(Call call, Exception e) {
//                mPresenter.getError(e.getMessage());
//            }
//
//            @Override
//            public void onResponse(byte[] bytes) {
//                try {
//                    String s = new String(bytes,"utf-8");
//                    List<ComicBean> list = JsoupUtils.getInstance().getComicSearchData(s);
//                    mPresenter.getSearchComicSuccess(list);
//                } catch (UnsupportedEncodingException e) {
//                    e.printStackTrace();
//                }
//            }
//        });
//    }
}

