package com.omi.infoapp.root;

import android.app.Application;

import com.omi.infoapp.create_activity.CreateModule;
import com.omi.infoapp.main_activity.MainModule;
import com.omi.infoapp.repository.dp.InfoLocalModule;
import com.omi.infoapp.repository.http.InfoApiModule;

import io.realm.Realm;
import io.realm.RealmConfiguration;


public class App extends Application {

    private ApplicationComponent component;
    private static final int DATABASE_VERSION = 1;

    @Override
    public void onCreate() {
        super.onCreate();

        setupRealm();

        component = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .infoApiModule(new InfoApiModule())
                .infoLocalModule(new InfoLocalModule())
                .mainModule(new MainModule())
                .createModule(new CreateModule())
                .build();

    }

    private void setupRealm() {
        Realm.init(this);

        new RealmConfiguration.Builder()
                .schemaVersion(DATABASE_VERSION)
                .name("info.realm")
                .build();
    }

    public ApplicationComponent getComponent() {
        return component;
    }
}
