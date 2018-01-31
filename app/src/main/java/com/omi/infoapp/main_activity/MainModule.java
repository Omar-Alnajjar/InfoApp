package com.omi.infoapp.main_activity;


import com.omi.infoapp.main_activity.model.Repository;
import com.omi.infoapp.main_activity.model.InfoModel;
import com.omi.infoapp.main_activity.model.InfoRepository;
import com.omi.infoapp.main_activity.presenter.MainPresenter;
import com.omi.infoapp.repository.dp.InfoLocalStorage;
import com.omi.infoapp.repository.http.InfoApiService;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class MainModule {

    @Provides
    public MainActivityMVP.Presenter provideMainActivityPresenter(MainActivityMVP.Model MainModel) {
        return new MainPresenter(MainModel);
    }

    @Provides
    public MainActivityMVP.Model provideMainActivityModel(Repository repository) {
        return new InfoModel(repository);
    }

    @Singleton
    @Provides
    public Repository provideRepo(InfoApiService infoApiService, InfoLocalStorage infoLocalStorage) {
        return new InfoRepository(infoApiService, infoLocalStorage);
    }


}
