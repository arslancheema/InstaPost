package com.example.aarshad.instapost;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseACL;

/**
 * Created by aarshad on 11/15/17.
 */

public class StarterApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        // Enable Local Datastore.
        Parse.enableLocalDatastore(this);

        // Add your initialization code here
        Parse.initialize(new Parse.Configuration.Builder(getApplicationContext())
                .applicationId("3fc753364e9996fa1c93eda4afa11d72e27a05c1")
                .clientKey("d957972dd76da77bbad7e6a3e2700d2d5977761d")
                .server("http://ec2-18-216-83-207.us-east-2.compute.amazonaws.com/parse/")
                .build()
        );

        // To Automatically signup Users
        //ParseUser.enableAutomaticUser();
        ParseACL defaultACL = new ParseACL();
        defaultACL.setPublicReadAccess(true);
        defaultACL.setPublicWriteAccess(true);
        ParseACL.setDefaultACL(defaultACL, true);

    }
}