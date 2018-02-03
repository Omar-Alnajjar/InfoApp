package com.omi.infoapp.create_activity.model;



import com.omi.infoapp.objects.DataObject;

import java.io.File;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Maybe;
import io.reactivex.Observable;

public interface CreateRepository {

    Observable<String> uploadImages(File[] files);

    Completable createInfo(DataObject dataObject);
}
