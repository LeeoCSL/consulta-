package br.com.consultai.utils;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.support.annotation.NonNull;

/**
 * Created by leonardo.ribeiro on 18/10/2017.
 */

public class DialogFactory extends AlertDialog {

    private static ProgressDialog mDialog;

    protected DialogFactory(@NonNull Context context) {
        super(context);
    }

    public static void createDialog(Context ctx, String title, String content){
        mDialog = new ProgressDialog(ctx);
        mDialog.setTitle(title);
        mDialog.setMessage(content);
        mDialog.setCancelable(false);
        mDialog.setCanceledOnTouchOutside(false);
        mDialog.show();
    }

    public static void destroyDialog(){
        if(!mDialog.isShowing()){
            mDialog.dismiss();
        }
    }

    public static void loadingDialog(Context context) {
        mDialog = new ProgressDialog(context);
        mDialog.setMessage("Por favor, aguarde.");
        mDialog.setCancelable(false);
        mDialog.setCanceledOnTouchOutside(false);
        mDialog.show();
    }

    public static void hideLoadingDialog() {
        if (mDialog.isShowing()) {
            mDialog.dismiss();
        }
    }
}

