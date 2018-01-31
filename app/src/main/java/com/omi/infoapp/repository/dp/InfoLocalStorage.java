package com.omi.infoapp.repository.dp;


import com.omi.infoapp.objects.DataObject;

import java.util.List;

import io.reactivex.Maybe;
import io.reactivex.Observable;

public interface InfoLocalStorage {

    Maybe<List<DataObject>> getInfo();
    Observable<List<DataObject>> saveInfo(List<DataObject> dataObjects);

}
