package com.example.orate.Activity.Fragments;

import android.webkit.JavascriptInterface;


public class JavaScriptInterface {
    private MethodsHelperClass helperClass;

    public JavaScriptInterface(MethodsHelperClass helperClass) {
        this.helperClass = helperClass;
    }

    @JavascriptInterface
    public void onPeerConnected() {
        helperClass.onPeerConnected();

    }
}
