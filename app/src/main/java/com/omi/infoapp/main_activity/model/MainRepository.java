package com.omi.infoapp.main_activity.model;



import com.google.firebase.database.DataSnapshot;
import com.omi.infoapp.objects.DataObject;

import java.util.List;

import io.reactivex.Maybe;
import io.reactivex.Observable;

public interface MainRepository {

    Maybe<List<DataObject>> getResultsFromMemory(String last);

    Maybe<List<DataObject>> getResultsFromNetwork(String lastId);

    Observable<List<DataObject>> getResults(String lastId);


    Observable<List<DataObject>> saveInfoToDB(List<DataObject> dataObjects);
}
