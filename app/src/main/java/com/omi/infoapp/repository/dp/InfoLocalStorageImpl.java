package com.omi.infoapp.repository.dp;


import com.omi.infoapp.objects.DataObject;

import java.util.List;

import io.reactivex.Maybe;
import io.reactivex.Observable;
import io.realm.Realm;
import io.realm.RealmResults;

public class InfoLocalStorageImpl implements InfoLocalStorage {


    public InfoLocalStorageImpl() {
    }

    @Override
    public Maybe<List<DataObject>> getInfo() {
        Realm realm = Realm.getDefaultInstance();
        try {
            RealmResults<DataObject> dataObjects = realm.where(DataObject.class).findAll();
            if(dataObjects == null){
                return Maybe.empty();
            }else {
                return Maybe.just(realm.copyFromRealm(dataObjects));
            }
        }finally {
            realm.close();
        }
    }

    @Override
    public Observable<List<DataObject>> saveInfo(final List<DataObject> dataObjects) {
        Realm realm = Realm.getDefaultInstance();
        try {
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    realm.copyToRealmOrUpdate(dataObjects);
                }
            });
        }finally {
            realm.close();
        }
        return Observable.just(dataObjects);
    }
}
