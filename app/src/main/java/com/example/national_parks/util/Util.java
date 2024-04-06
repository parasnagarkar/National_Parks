package com.example.national_parks.util;

import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

public class Util {
    public static final String PARKS_URL = "https://developer.nps.gov/api/v1/parks?stateCode=hi&acad&api_key=nM5O8v1xgI49gvjD9hyfWnssDkpWsOsJI7TgUbBj";

    public static String getParksUrl(String stateCode){
        return "https://developer.nps.gov/api/v1/parks?stateCode="+stateCode+"&acad&api_key=nM5O8v1xgI49gvjD9hyfWnssDkpWsOsJI7TgUbBj";
    }

    public static void hideSoftKeyBoard(View v) {
        InputMethodManager IMM = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        IMM.hideSoftInputFromWindow(v.getWindowToken(), 0);

    }
}
