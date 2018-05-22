package ldh.com.zcomic.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.List;

public class SharedPreUtils {

    private static String USER_SETTING = "settingFragment";

    /**
     * 存字符串
     *
     * @param mContext
     * @param key
     * @param values
     */
    public static void putString(Context mContext, String key, String values) {
        SharedPreferences sp = mContext.getSharedPreferences("config", Context.MODE_PRIVATE);
        sp.edit().putString(key, values).commit();
    }

    /**
     * 取字符串
     *
     * @param mContext
     * @param key
     * @param values   默认值
     * @return 取出的值
     */
    public static String getString(Context mContext, String key, String values) {
        SharedPreferences sp = mContext.getSharedPreferences("config", Context.MODE_PRIVATE);
        return sp.getString(key, values);
    }


    /**
     * 存布尔值
     *
     * @param mContext
     * @param key
     * @param values
     */
    public static void putBoolean(Context mContext, String key, boolean values) {
        SharedPreferences sp = mContext.getSharedPreferences("config", Context.MODE_PRIVATE);
        sp.edit().putBoolean(key, values).commit();
    }

    /**
     * 取布尔值
     *
     * @param mContext
     * @param key
     * @param values   默认值
     * @return true/false
     */
    public static boolean getBoolean(Context mContext, String key, boolean values) {
        SharedPreferences sp = mContext.getSharedPreferences("config", Context.MODE_PRIVATE);
        return sp.getBoolean(key, values);
    }


    /**
     * 存int值
     *
     * @param mContext
     * @param key
     * @param values   值
     */
    public static void putInt(Context mContext, String key, int values) {
        SharedPreferences sp = mContext.getSharedPreferences("config", Context.MODE_PRIVATE);
        sp.edit().putInt(key, values).commit();
    }

    /**
     * 取int值
     *
     * @param mContext
     * @param key
     * @param values   默认值
     * @return
     */
    public static int getInt(Context mContext, String key, int values) {
        SharedPreferences sp = mContext.getSharedPreferences("config", Context.MODE_PRIVATE);
        return sp.getInt(key, values);
    }

    /**
     * 删除一条字段
     *
     * @param mContext
     * @param key
     */
    public static void deleShare(Context mContext, String key) {
        SharedPreferences sp = mContext.getSharedPreferences("config", Context.MODE_PRIVATE);
        sp.edit().remove(key).commit();
    }

    /**
     * 删除全部数据
     *
     * @param mContext
     */
    public static void deleShareAll(Context mContext) {
        SharedPreferences sp = mContext.getSharedPreferences("config", Context.MODE_PRIVATE);
        sp.edit().clear().commit();
    }

    /**
     * 获取当前用户主题
     */
    public static String getCurrentTheme(Context mContext) {
        SharedPreferences sp = mContext.getSharedPreferences(USER_SETTING, Context.MODE_PRIVATE);
        return sp.getString("theme", "原谅绿");
    }

    /**
     * 获取当前用户主题
     */
    public static void setCurrentTheme(Context mContext,String theme) {
        SharedPreferences sp = mContext.getSharedPreferences(USER_SETTING, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        if (editor != null) {
            editor.putString("theme", theme);
            editor.commit();
        }
    }

    /**
     * 保存List
     */
    public static <T> void setDataList(Context mContext,String tag, List<T> datalist) {
        if (null == datalist || datalist.size() <= 0)
            return;
        Gson gson = new Gson();
        //转换成json数据，再保存
        String strJson = gson.toJson(datalist);
//        editor.clear();
        SharedPreferences sp = mContext.getSharedPreferences("channel", Context.MODE_PRIVATE);
        sp.edit().putString(tag, strJson).commit();
    }

    /**
     * 获取list
     */
    public static <T> List<T> getDataList(Context mContext,String tag, Class<T> clazz) {
        List<T> datalist=new ArrayList<T>();
        String strJson = mContext.getSharedPreferences("channel", Context.MODE_PRIVATE).getString(tag, null);
        if (null == strJson) {
            return datalist;
        }
        JsonArray array = new JsonParser().parse(strJson).getAsJsonArray();
        for (final JsonElement elem : array) {
            datalist.add(new Gson().fromJson(elem, clazz));
        }
        return datalist;
    }
}
