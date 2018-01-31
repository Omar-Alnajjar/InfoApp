package com.omi.infoapp.main_activity.model;


import com.omi.infoapp.main_activity.MainActivityMVP;
import com.omi.infoapp.objects.DataObject;

import java.util.List;

import io.reactivex.Observable;

public class InfoModel implements MainActivityMVP.Model {

    private Repository repository;

    public InfoModel(Repository repository) {
        this.repository = repository;
    }

    @Override
    public Observable<List<DataObject>> result(String cityName) {

        return repository.getResultData(cityName);
    }

    @Override
    public Observable<List<DataObject>> saveData(List<DataObject> dataObjects) {
        return repository.saveInfoToDB(dataObjects);
    }

}
