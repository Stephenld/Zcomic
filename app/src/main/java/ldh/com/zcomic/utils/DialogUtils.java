package ldh.com.zcomic.utils;

import android.app.Dialog;
import android.content.Context;
import android.support.v7.app.AlertDialog;

/*
 *  描述：    自定义提示框
 */
public class DialogUtils {

    //标准的提示框
    public static AlertDialog.Builder showStandardDialog(Context mContext, String title, String message) {
        return new AlertDialog.Builder(mContext)
                .setTitle(title)
                .setMessage(message);
    }

    //自定义的Dialog
    public static Dialog showViewDialog(Context mContext, int layout) {
        Dialog dialog = new Dialog(mContext);
        dialog.setContentView(layout);
        return dialog;
    }
}
