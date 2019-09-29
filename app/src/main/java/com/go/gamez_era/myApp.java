package com.go.gamez_era;
import android.app.Application;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Build;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.core.graphics.TypefaceCompatUtil;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

public class myApp extends Application {
    static String user_name;
    static Double ver=1.2;
    @Override
    public void onCreate() {
        super.onCreate();
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("font/sans_medium.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()

        );

    }
}