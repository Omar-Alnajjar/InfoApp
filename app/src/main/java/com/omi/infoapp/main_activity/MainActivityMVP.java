package com.omi.infoapp.main_activity;


import com.omi.infoapp.objects.DataObject;

import java.util.List;

import io.reactivex.Observable;

public interface MainActivityMVP {

    interface View {

        void updateData(List<DataObject> dataObject);

        void showSnackbar(String error);


    }

    interface Presenter {

        void loadData(String lastId);


        void rxUnsubscribe();

        void setView(MainActivityMVP.View view);
    }

    interface Model {

        Observable<List<DataObject>> loadData(String lastId);
        Observable<List<DataObject>> saveData(List<DataObject> dataObjects);

    }
}
