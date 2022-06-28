package com.example.orate.Activity.Fragments;

import android.webkit.JavascriptInterface;

import com.example.orate.MethodHelperClasses.MainActivityHelper;


public class JavaScriptInterface {
    private MainActivityHelper helperClass;

    public JavaScriptInterface(MainActivityHelper helperClass) {
        this.helperClass = helperClass;
    }

    @JavascriptInterface
    public void onPeerConnected() {
        helperClass.onPeerConnected();

    }
}
