package br.com.consultai.utils;

import android.content.Context;
import android.widget.Toast;


public class ToastUtils {

    public static void makeText(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }


    public static void makeText(Context context, int messageId) {
        Toast.makeText(context, context.getResources().getText(messageId), Toast.LENGTH_SHORT).show();
    }


}