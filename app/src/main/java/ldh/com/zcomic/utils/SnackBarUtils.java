package ldh.com.zcomic.utils;

import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.RelativeLayout;

public class SnackBarUtils {
    public static Snackbar mSnackbar;
    public static void showSnackBar(View v, String str) {

        if (v instanceof RelativeLayout) {
            mSnackbar = Snackbar.make(v,str, Snackbar.LENGTH_SHORT).setAction("确定", new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mSnackbar.dismiss();
                }
            });
            mSnackbar.show();
        }
    }
}
