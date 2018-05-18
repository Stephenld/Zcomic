package ldh.com.zcomic.utils;

import android.app.Activity;
import android.content.ContentResolver;
import android.provider.Settings;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;

public class ViewUtil {

    private static AlphaAnimation mHideAnimation= null;
    private static AlphaAnimation mShowAnimation= null;

    /**
     * 设置屏幕亮度
     * @param activity 环境
     * @param value 亮度值
     */
    public static void setScreenBrightness(Activity activity, int value){
        WindowManager.LayoutParams params = activity.getWindow().getAttributes();
        params.screenBrightness = value / 255f;
        activity.getWindow().setAttributes(params);
    }

    /**
     * 获取屏幕亮度
     * @param activity
     * @return
     */
  public static int getScreenBrightness(Activity activity){
      int value = 0;
      ContentResolver cr = activity.getContentResolver();
      try {
          value = Settings.System.getInt(cr, Settings.System.SCREEN_BRIGHTNESS);
      }catch (Exception e){
          e.printStackTrace();
      }
      return value;
  }
}
