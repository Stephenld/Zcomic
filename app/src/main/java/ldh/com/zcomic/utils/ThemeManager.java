package ldh.com.zcomic.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import ldh.com.zcomic.R;
import ldh.com.zcomic.entity.Constants;

/**
 * Created by allen liu on 2018/5/4.
 */

public class ThemeManager {
    private String[] mThemes = {"原谅绿", "酷炫黑", "少女红", "胖次蓝", "基佬紫", "活力橙", "大地棕"};
    private static ThemeManager manager;
    public static ThemeManager getManager(){
        if (manager == null) {
            manager = new ThemeManager();
        }
        return manager;
    }
    public String[] getThemes(){
        return mThemes;
    }

    public void setTheme(Activity mContext, String theme) {
        String curTheme = SharedPreUtils.getCurrentTheme(mContext);
        if(curTheme != null && curTheme.equals(theme)){
            return;
        }
        SharedPreUtils.setCurrentTheme(mContext, theme);
        mContext.finish();
        Intent intent = mContext.getIntent();
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra(Constants.EXTRA_IS_UPDATE_THEME, true);
        mContext.startActivity(intent);
    }

    public String getCurThemeName(Context mContext) {
        return SharedPreUtils.getCurrentTheme(mContext);
    }

    public void init(Context mContext) {
        String theme = SharedPreUtils.getCurrentTheme(mContext);
        if (theme.equals(mThemes[0])) {
            mContext.setTheme(R.style.AppTheme);
        } else if (theme.equals(mThemes[1])) {
            mContext.setTheme(R.style.Black);
        } else if (theme.equals(mThemes[2])) {
            mContext.setTheme(R.style.Green);
        } else if (theme.equals(mThemes[3])) {
            mContext.setTheme(R.style.Blue);
        } else if (theme.equals(mThemes[4])) {
            mContext.setTheme(R.style.Purple);
        } else if (theme.equals(mThemes[5])) {
            mContext.setTheme(R.style.Orange);
        } else if (theme.equals(mThemes[6])) {
            mContext.setTheme(R.style.Brown);
        }
    }
}
