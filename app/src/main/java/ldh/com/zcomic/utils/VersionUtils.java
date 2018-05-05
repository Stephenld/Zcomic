package ldh.com.zcomic.utils;

import android.os.Build;

/*
 *  描述：    版本判断
 */
public class VersionUtils {

    //获取当前的版本
    public static int getVersion() {
        return Build.VERSION.SDK_INT;
    }

    //是否大于5.0
    public static boolean isLollipop() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            return true;
        } else {
            return false;
        }
    }

    //是否大于6.0
    public static boolean isM() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return true;
        } else {
            return false;
        }
    }
}
