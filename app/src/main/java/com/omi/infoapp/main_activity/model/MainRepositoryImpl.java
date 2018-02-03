package com.omi.infoapp.main_activity.model;



import com.google.firebase.database.DataSnapshot;
import com.omi.infoapp.objects.DataObject;
import com.omi.infoapp.repository.dp.InfoLocalStorage;
import com.omi.infoapp.repository.http.InfoApiService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import io.reactivex.Maybe;
import io.reactivex.MaybeSource;
import io.reactivex.Observable;
import io.reactivex.functions.Function;

public class MainRepositoryImpl implements MainRepository {

    private InfoApiService infoApiService;
    private InfoLocalStorage infoLocalStorage;

    public MainRepositoryImpl(InfoApiService infoApiService, InfoLocalStorage infoLocalStorage) {
        this.infoApiService = infoApiService;
        this.infoLocalStorage = infoLocalStorage;
    }


    @Override
    public Maybe<List<DataObject>> getResultsFromMemory(String lastId) {
        return infoLocalStorage.getInfo();
    }

    @Override
    public Maybe<List<DataObject>> getResultsFromNetwork(String lastId) {
        Maybe<Map<String, DataObject>> apiResult = infoApiService.getInfo();
        return apiResult.flatMap(new Function<Map<String, DataObject>, Maybe<List<DataObject>>>() {
            @Override
            public Maybe<List<DataObject>> apply(Map<String, DataObject> dataSnapshots) throws Exception {
                List<DataObject> dataObjects = new ArrayList<>();
                dataObjects.addAll(dataSnapshots.values());

                return Maybe.just(dataObjects);
            }
        });
    }

    @Override
    public Observable<List<DataObject>> saveInfoToDB(List<DataObject> dataObjects) {
        return infoLocalStorage.saveInfo(dataObjects);
    }
}
