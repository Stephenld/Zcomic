package ldh.com.zcomic.base;

import android.app.Application;
import android.content.Context;

import cn.bmob.v3.Bmob;
import ldh.com.zcomic.entity.Key;


/**
 * Created by allen liu on 2018/5/3.
 */

public class BaseApplication extends Application {
    private static Context context;
    @Override
    public void onCreate() {
        super.onCreate();
            Bmob.initialize(this, Key.BMOB_APPLICATION_KEY);
    }
    public static Context getContext() {
        return context;
    }
}
