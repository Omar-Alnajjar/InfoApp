package com.omi.infoapp.repository.dp;


import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.realm.Realm;
import io.realm.RealmConfiguration;

@Module
public class InfoLocalModule {

    private static final int DATABASE_VERSION = 1;

    @Singleton
    @Provides
    InfoLocalStorage provideInfoDataSource(Realm realm) {
        return new InfoLocalStorageImpl(realm);
    }

    @Provides
    @Singleton
    Realm provideRealm(RealmConfiguration config) {
        return Realm.getInstance(config);
    }

    @Provides
    @Singleton
    RealmConfiguration provideRealmConfig(Context context) {
        Realm.init(context);

        return new RealmConfiguration.Builder()
                .schemaVersion(DATABASE_VERSION)
                .name("info.realm")
                .build();
    }

}
