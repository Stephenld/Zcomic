package ldh.com.zcomic.base;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import ldh.com.zcomic.bean.ComicBean;

/**
 * Created by allen liu on 2018/5/15.
 */

public class BaseController {
    //缓存策略
    public BmobQuery.CachePolicy mPolicy = BmobQuery.CachePolicy.NETWORK_ONLY;
    //一页加载的数量
    public int pageCount = 12;
    //加载的数量限制
    public int limit_page = 2000;  //这个值如果太少，listview显示时会数据不全，比如有22条，只显示10条！！
    //重连机制的总时间
    public int connect_time = 5000;
    //重连机制的间隔时间
    public int interval_time = 5000;

    /**
     * bmob查询接口
     */
    public interface OnBmobListener {
        void onSuccess(List<ComicBean> list);

        void onError(String error);
    }

    /**
     * bmob增删改接口
     */
    public interface OnBmobCommonListener {
        void onSuccess(String success);

        void onError(String error);
    }

    /**
     * bmob用户接口
     */
    public interface onBmobUserListener {
        void onSuccess(String success);

        void onError(String error);

        void onLoading(String loading);
    }

    /**
     * bmob文件接口
     */
    public interface onBmobInsertListener {
        void onSuccess(String success);

        void onError(String error);

        void onLoading(int loading);
    }
}
