package br.com.consultai.utils;

import android.app.ProgressDialog;
import android.content.Context;

/**
 * Created by renan.boni on 24/11/2017.
 */

public class DialogUtil {

    public static ProgressDialog showProgressDialog(Context context, String title, String content) {
        ProgressDialog pDialog = new ProgressDialog(context);
        pDialog.setTitle(title);
        pDialog.setMessage(content);
        pDialog.setCancelable(false);
        pDialog.show();
        return pDialog;
    }

    public static void hideProgressDialog(ProgressDialog pDialog) {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }

}
