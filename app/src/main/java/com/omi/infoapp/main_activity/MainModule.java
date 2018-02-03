package com.omi.infoapp.main_activity;


import com.omi.infoapp.main_activity.model.MainRepository;
import com.omi.infoapp.main_activity.model.MainModel;
import com.omi.infoapp.main_activity.model.MainRepositoryImpl;
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
    public MainActivityMVP.Model provideMainActivityModel(MainRepository repository) {
        return new MainModel(repository);
    }

    @Singleton
    @Provides
    public MainRepository provideRepo(InfoApiService infoApiService, InfoLocalStorage infoLocalStorage) {
        return new MainRepositoryImpl(infoApiService, infoLocalStorage);
    }


}
