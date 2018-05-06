package ldh.com.zcomic.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Build;
import android.os.Environment;

import com.blankj.utilcode.utils.AppUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.FileCallBack;

import java.io.File;

import okhttp3.Call;

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

    public static void versionUpdate(final Context context, String url) {

        final ProgressDialog dialog = new ProgressDialog(context);
        dialog.setCancelable(false);
        dialog.setTitle("更新");
        dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        dialog.show();


        OkHttpUtils//
                .get()//
                .url(url)//
                .build()//
                .execute(new FileCallBack(Environment.getExternalStorageDirectory()
                        .getAbsolutePath(), "QNews.apk") {

                    @Override
                    public void inProgress(float progress, long total, int id) {
                        dialog.setProgress((int) (progress * 100));
                    }

                    @Override
                    public void onError(Call call, Exception e, int id) {
                        LogUtils.i("e:" + e.getMessage());
                    }

                    @Override
                    public void onResponse(File response, int id) {
                        LogUtils.i("file:" + response.getAbsolutePath());
                        dialog.cancel();

                        AppUtils.installApp(context, response.getAbsolutePath());
                    }
                });
    }

}
