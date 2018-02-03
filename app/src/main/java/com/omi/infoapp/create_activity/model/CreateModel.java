package com.omi.infoapp.create_activity.model;


import com.omi.infoapp.create_activity.CreateActivityMVP;
import com.omi.infoapp.main_activity.MainActivityMVP;
import com.omi.infoapp.objects.DataObject;

import java.io.File;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Observable;

public class CreateModel implements CreateActivityMVP.Model {

    private CreateRepository repository;

    public CreateModel(CreateRepository repository) {
        this.repository = repository;
    }


    @Override
    public Observable<String> uploadImages(File[] imageFiles) {

        return repository.uploadImages(imageFiles);
    }

    @Override
    public Completable createInfo(DataObject dataObject) {
        return repository.createInfo(dataObject);
    }


}
