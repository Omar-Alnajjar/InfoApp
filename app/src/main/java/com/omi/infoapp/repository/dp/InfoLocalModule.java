package com.omi.infoapp.repository.dp;


import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.realm.Realm;
import io.realm.RealmConfiguration;

@Module
public class InfoLocalModule {


    @Singleton
    @Provides
    InfoLocalStorage provideInfoDataSource() {
        return new InfoLocalStorageImpl();
    }

}
