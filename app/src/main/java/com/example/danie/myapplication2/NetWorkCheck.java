package com.example.danie.myapplication2;

import android.app.Application;

import greyfox.rxnetwork.RxNetwork;

/**
 * Created by Danie on 16/05/2018.
 */

public class NetWorkCheck extends Application {

    @Override public void onCreate() {
        super.onCreate();

        RxNetwork.init(this); // this is the line
    }
}
