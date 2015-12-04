package com.webmyne.fridaynasta;

/**
 * @author jatin
 */

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Point;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.CountDownTimer;
import android.os.Environment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.webkit.MimeTypeMap;
import android.webkit.URLUtil;
import android.widget.Toast;


import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Functions {

    public static float convertDpToPixel(float dp, Context context) {
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float px = dp * (metrics.densityDpi / 160f);
        return px;
    }

    /**
     * This method converts device specific pixels to density independent pixels.
     *
     * @param px      A value in px (pixels) unit. Which we need to convert into db
     * @param context Context to get resources and device specific display metrics
     * @return A float value to represent dp equivalent to px value
     */
    public static float convertPixelsToDp(float px, Context context) {
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float dp = px / (metrics.densityDpi / 160f);
        return dp;
    }



    public static boolean emailValidation(String email) {
        boolean validEmailAddress = true;
        if (email.length() == 0) {
            validEmailAddress = false;
        } else {
            if (!email.contains(".") || !email.contains("@")) {
                validEmailAddress = false;
            } else {
                int index1 = email.indexOf("@");
                String subStringType = email.substring(index1);
                int index2 = index1 + subStringType.indexOf(".");
                if (index1 == 0 || index2 == 0) {
                    validEmailAddress = false;
                } else {
                    String typeOf = email.substring(index1, index2);
                    if (typeOf.length() < 1) {
                        validEmailAddress = false;
                    }
                    String typeOf2 = email.substring(index2);
                    if (typeOf2.length() < 2) {
                        validEmailAddress = false;
                    }
                }

            }
        }

        return validEmailAddress;
    }
   /* public static void showSnack(View v,String msg){
        Snackbar snack = Snackbar.make(v, msg, Snackbar.LENGTH_SHORT);
        int col = Color.parseColor("#313189");
        snack.getView().setBackgroundColor(col);
        snack.show();

    }

    public static void showSnackandGone(View v, final Context ctx,String msg){

        Snackbar snack = Snackbar.make(v, msg, Snackbar.LENGTH_SHORT);
        int col = Color.parseColor("#313189");
        snack.getView().setBackgroundColor(col);
        snack.show();

        new CountDownTimer(1000,1000){

            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                Activity act = (Activity) ctx;
                act.finish();
            }
        }.start();
    }
*/
    public static boolean isInternetConnected(Context _context){
        ConnectivityManager connectivity = (ConnectivityManager) _context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null)
        {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null)
                for (int i = 0; i < info.length; i++)
                    if (info[i].getState() == NetworkInfo.State.CONNECTED)
                    {
                        return true;
                    }

        }
        return false;
    }

    public static String md5(String input) {

        String md5 = null;

        if (null == input)
            return null;

        try {

            // Create MessageDigest object for MD5
            MessageDigest digest = MessageDigest.getInstance("MD5");

            // Update input string in message digest
            digest.update(input.getBytes(), 0, input.length());

            // Converts message digest value in base 16 (hex)
            md5 = new BigInteger(1, digest.digest()).toString(16);

        } catch (NoSuchAlgorithmException e) {

            e.printStackTrace();
        }
        return md5;
    }




}