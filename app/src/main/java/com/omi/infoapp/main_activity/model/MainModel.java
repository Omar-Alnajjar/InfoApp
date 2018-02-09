package com.omi.infoapp.main_activity.model;


import com.omi.infoapp.main_activity.MainActivityMVP;
import com.omi.infoapp.objects.DataObject;

import java.util.List;

import io.reactivex.Observable;

public class MainModel implements MainActivityMVP.Model {

    private MainRepository repository;

    public MainModel(MainRepository repository) {
        this.repository = repository;
    }

    @Override
    public Observable<List<DataObject>> loadData(String lastId) {
        return repository.getResults(lastId);
    }

    @Override
    public Observable<List<DataObject>> saveData(List<DataObject> dataObjects) {
        return repository.saveInfoToDB(dataObjects);
    }

}
