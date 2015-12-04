package com.webmyne.fridaynasta;

import android.app.Application;
import android.net.ParseException;
import android.util.Log;

import com.parse.Parse;
import com.parse.ParsePush;
import com.parse.SaveCallback;

/**
 * Created by krishnakumar on 04-12-2015.
 */
public class MyApplication extends Application {
    @Override
    public void onCreate() {
        // app id......client key
        Parse.initialize(MyApplication.this, getResources().getString(R.string.parsse_appID), getResources().getString(R.string.parsse_clientKEY));

        //ParseUser.enableAutomaticUser();

        ParsePush.subscribeInBackground("", new SaveCallback() {
            @Override
            public void done(com.parse.ParseException e) {
                if (e == null) {
                    Log.e("###### com.parse.push", "successfully subscribed to the broadcast channel.");
                } else {
                    Log.e("###### com.parse.push", "failed to subscribe for push", e);
                }
            }

        });


       

    }
}
