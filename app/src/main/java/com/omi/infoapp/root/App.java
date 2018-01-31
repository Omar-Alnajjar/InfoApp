package com.omi.infoapp.root;

import android.app.Application;

import com.omi.infoapp.main_activity.MainModule;
import com.omi.infoapp.repository.dp.InfoLocalModule;
import com.omi.infoapp.repository.http.InfoApiModule;


public class App extends Application {

    private ApplicationComponent component;

    @Override
    public void onCreate() {
        super.onCreate();

        component = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .infoApiModule(new InfoApiModule())
                .mainModule(new MainModule())
                .infoLocalModule(new InfoLocalModule())
                .build();
    }

    public ApplicationComponent getComponent() {
        return component;
    }
}
