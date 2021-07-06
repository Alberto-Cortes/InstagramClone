package com.example.instagramclone;

import android.app.Application;

import com.parse.Parse;

// Instance a Parse Application
public class ParseApplication extends Application {

    // Connect with app ID and keys when created
    @Override
    public void onCreate() {
        super.onCreate();

        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("Zn8vftfyM28ozk0GW6rTe4Gi6QduW8FmysIqO1vh")
                .clientKey("wTKJ5TgjscJzTEiHKid3noa2m6JmKByaa1ZwG2ER")
                .server("https://parseapi.back4app.com")
                .build()
        );
    }
}
