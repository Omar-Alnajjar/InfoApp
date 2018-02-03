package com.omi.infoapp.create_activity;


import com.omi.infoapp.objects.DataObject;

import java.io.File;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Observable;

public interface CreateActivityMVP {

    interface View {

        void doneCreate();

        void setImage(String imageUrl);

        void showSnackbar(String error);

    }

    interface Presenter {

        void compressImages(File imageFile);

        void uploadImages(File[] imageFiles);

        boolean uploadWasDone();

        void createInfo(DataObject dataObject);

        void rxUnsubscribe();

        void setView(CreateActivityMVP.View view);
    }

    interface Model {
        Observable<String> uploadImages(File[] imageFiles);
        Completable createInfo(DataObject dataObject);
    }
}
