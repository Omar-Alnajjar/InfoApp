package com.omi.infoapp.main_activity.model;



import com.omi.infoapp.objects.DataObject;

import java.util.List;

import io.reactivex.Maybe;
import io.reactivex.Observable;

public interface Repository {

    Maybe<List<DataObject>> getResultsFromMemory(String last);

    Maybe<List<DataObject>> getResultsFromNetwork(String cityName);

    Observable<List<DataObject>> getResultData(String cityName);

    Observable<List<DataObject>> saveInfoToDB(List<DataObject> dataObjects);
}
