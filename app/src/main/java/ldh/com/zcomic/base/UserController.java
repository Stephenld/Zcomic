package ldh.com.zcomic.base;


import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;
import ldh.com.zcomic.R;
import ldh.com.zcomic.bean.User;

/**
 * Created by allen liu on 2018/5/15.
 */

public class UserController extends BaseController {
    public static volatile UserController userController;

    public static UserController getInstance() {
        if (userController == null) {
            synchronized (UserController.class) {
                if (userController == null) {
                    userController = new UserController();
                }
            }
        }
        return userController;
    }
    /**
     * 获取当前用户
     *
     * @return
     */
    public User getCurrentUser() {
        User user = BmobUser.getCurrentUser(User.class);
        return user == null ? null : user;
    }

    /**
     * 是否已经登录
     *
     * @return
     */
    public boolean isLogin() {
        return getCurrentUser() != null;
    }
    /**
     * 添加收藏漫画
     */
    public void addUserCollect(String comicId, final ImageView iv, final onBmobUserListener listener) {
        if (!isLogin()) {
            listener.onError("请登录后再加入我的收藏");
            return;
        }
        List<String> collectId = getCollectId();
        if (collectId.contains(comicId)) {
            listener.onSuccess("已经加入收藏列表");
            return;
        }
        collectId.add(comicId);
        User user = getCurrentUser();
        user.collect_comicId = collectId;
        user.update(new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    iv.setBackgroundResource(R.drawable.comic_collect_on);
                    listener.onSuccess("收藏成功");
                } else {
                    listener.onError("收藏失败");
                }
            }
        });
    }

    /**
     * 删除收藏漫画
     */
    public void deleteUserLove(List<String> comicIds, final OnBmobCommonListener listener) {
        if (comicIds.isEmpty()) {
            return;
        }
        List<String> collectIds = getCollectId();
        for (String comicId : comicIds) {
            collectIds.remove(comicId);
        }
        User user = getCurrentUser();
        user.collect_comicId = collectIds;
        user.update(new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    listener.onSuccess("删除成功");
                } else{
                    listener.onError("删除失败");
                }
            }
        });
    }

//    /**
//     * 更新收藏漫画列表
//     */
//    public void updateUserLove(List<String> collectList) {
//        if (isLogin()) {
//            getCurrentUser().setCollect_comicId(collectList);
////            getCurrentUser().collect_comicId = collectList;  (更新数据要用哪个set()方法)
//            getCurrentUser().update(getCurrentUser().getObjectId(),new UpdateListener() {
//                @Override
//                public void done(BmobException e) {
//                    if(e==null){
//                        Log.i("UserController", "更新收藏漫画列表成功");
//                    }
//                }
//            });
//        }
//    }
//    /**
//     * 清空收藏漫画列表
//     */
//    public void clearUserLove() {
//        if (isLogin()) {
//            getCurrentUser().remove("collect_comicId");
//            getCurrentUser().update(getCurrentUser().getObjectId(),new UpdateListener() {
//                @Override
//                public void done(BmobException e) {
//                    if(e==null){
//                        Log.i("UserController", "更新收藏漫画列表成功");
//                    }
//                }
//            });
//        }
//    }

    /**
     * 获取我的收藏列表
     *
     * @return
     */
    public List<String> getCollectId() {
        if (!isLogin()) {
            return new ArrayList<>();
        }
        if (getCurrentUser().collect_comicId == null) {
            return new ArrayList<>();
        } else {
            return getCurrentUser().collect_comicId;
        }
    }
}
