package ldh.com.zcomic.base;

import android.os.CountDownTimer;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import ldh.com.zcomic.bean.ComicBean;

/**
 * Created by allen liu on 2018/5/15.
 */

public class ComicController extends BaseController{
    public static volatile ComicController comicController;

    public static ComicController getInstance() {
        if (comicController == null) {
            synchronized (ComicController.class) {
                if (comicController == null) {
                    comicController = new ComicController();
                }
            }
        }
        return comicController;
    }

    /**
     * 查询漫画id集合中的所有漫画
     *
     */
    public void query(final List<String> comics, final OnBmobListener listener) {
        BmobQuery<ComicBean> query = new BmobQuery<>();
        query.setCachePolicy(mPolicy);
        query.setLimit(limit_page);
        query.addWhereContainedIn("comicId", comics);
        query.findObjects(new FindListener<ComicBean>() {
            @Override
            public void done(List<ComicBean> list, BmobException e) {
                if (e != null) {
                    if(e.getErrorCode() == 9016){
                        listener.onError("无网络连接，请检查您的手机网络");
                        return;
                    }
//                    listener.onError("服务器异常，正在重连");
                    listener.onError(e.getErrorCode()+"");
                    //重连机制
                    new CountDownTimer(connect_time, interval_time) {
                        @Override
                        public void onTick(long millisUntilFinished) {
                        }
                        @Override
                        public void onFinish() {
                            query(comics, listener);
                        }
                    }.start();
                    return;
                }
                if (list.isEmpty()) {
                    listener.onError("空空如也");
                    return;
                }
                if (listener != null) {
                    listener.onSuccess(list);
                }
            }

//            @Override
//            public void done(final List<ComicBean> list, BmobException e) {
//            public void done(ComicBean list, BmobException e) {
//                if (e != null) {
//                    if(e.getErrorCode() == 9016){
//                        listener.onError("无网络连接，请检查您的手机网络");
//                        return;
//                    }
////                    listener.onError("服务器异常，正在重连");
//                      listener.onError(e.getErrorCode()+"");
//                    //重连机制
//                    new CountDownTimer(connect_time, interval_time) {
//                        @Override
//                        public void onTick(long millisUntilFinished) {
//                        }
//                        @Override
//                        public void onFinish() {
//                            query(comics, listener);
//                        }
//                    }.start();
//                    return;
//                }
//                if (list.isEmpty()) {
//                    listener.onError("空空如也");
//                    return;
//                }
//                if (listener != null) {
//                    listener.onSuccess(list);
//                }
//            }
        });
    }
}
