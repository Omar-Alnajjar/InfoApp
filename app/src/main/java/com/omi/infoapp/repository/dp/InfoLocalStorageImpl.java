package com.omi.infoapp.repository.dp;


import com.omi.infoapp.objects.DataObject;

import java.util.List;

import io.reactivex.Maybe;
import io.reactivex.Observable;
import io.realm.Realm;
import io.realm.RealmResults;

public class InfoLocalStorageImpl implements InfoLocalStorage {

    Realm realm;

    public InfoLocalStorageImpl(Realm realm) {
        this.realm = realm;
    }

    @Override
    public Maybe<List<DataObject>> getInfo() {
        RealmResults<DataObject> dataObjects = realm.where(DataObject.class).findAll();
        if(dataObjects == null){
            return Maybe.empty();
        }else {
            return Maybe.just(realm.copyFromRealm(dataObjects));
        }
    }

    @Override
    public Observable<List<DataObject>> saveInfo(final List<DataObject> dataObjects) {
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.copyToRealmOrUpdate(dataObjects);
            }
        });


        return Observable.just(dataObjects);
    }
}
