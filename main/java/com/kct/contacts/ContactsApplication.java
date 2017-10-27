package com.kct.contacts;

import android.app.Application;
import android.content.ContextWrapper;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.pixplicity.easyprefs.library.Prefs;

/**
 * Created by Rinik on 13/10/17.
 */

public class ContactsApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        new Prefs.Builder()
                .setContext(this)
                .setMode(ContextWrapper.MODE_PRIVATE)
                .setPrefsName(getPackageName())
                .setUseDefaultSharedPreference(true)
                .build();
        Fresco.initialize(this);
    }
}
