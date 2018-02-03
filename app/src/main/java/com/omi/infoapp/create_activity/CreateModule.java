package com.omi.infoapp.create_activity;


import android.content.Context;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.omi.infoapp.create_activity.model.CreateModel;
import com.omi.infoapp.create_activity.model.CreateRepository;
import com.omi.infoapp.create_activity.model.CreateRepositoryImpl;
import com.omi.infoapp.create_activity.presenter.CreatePresenter;
import com.omi.infoapp.repository.dp.InfoLocalStorage;
import com.omi.infoapp.repository.http.InfoApiService;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class CreateModule {

    @Provides
    public CreateActivityMVP.Presenter provideCreateActivityPresenter(CreateActivityMVP.Model CreateModel, Context context) {
        return new CreatePresenter(CreateModel, context);
    }

    @Provides
    public CreateActivityMVP.Model provideCreateActivityModel(CreateRepository repository) {
        return new CreateModel(repository);
    }

    @Provides
    @Singleton
    public StorageReference provideCreateActivityStorage() {
        FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
        return firebaseStorage.getReference().child("photos");
    }

    @Singleton
    @Provides
    public CreateRepository provideRepo(InfoApiService infoApiService, InfoLocalStorage infoLocalStorage, StorageReference storageReference) {
        return new CreateRepositoryImpl(infoApiService, infoLocalStorage, storageReference);
    }


}
