package br.com.consultai.utils;

import android.util.Patterns;

/**
 * Created by leonardo.ribeiro on 27/10/2017.
 */

public class TextUtils {

    public static boolean isEmailValid(String email) {
        return email != null && Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
}
