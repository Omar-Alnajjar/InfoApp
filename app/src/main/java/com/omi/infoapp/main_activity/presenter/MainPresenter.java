package com.omi.infoapp.main_activity.presenter;


import com.omi.infoapp.main_activity.MainActivityMVP;
import com.omi.infoapp.objects.DataObject;

import org.reactivestreams.Subscription;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class MainPresenter implements MainActivityMVP.Presenter {

    private MainActivityMVP.View view;
    private Subscription subscription = null;
    private MainActivityMVP.Model model;
    private CompositeDisposable disposables;

    public MainPresenter(MainActivityMVP.Model model) {
        this.model = model;
        disposables = new CompositeDisposable();
    }

    @Override
    public void loadData(String lastId) {

        disposables.add(model.result(lastId).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())


                .subscribeWith(new DisposableObserver<List<DataObject>>() {



                    @Override
                public void onError(Throwable e) {
                    e.printStackTrace();
                    if (view != null) {
                        view.showSnackbar("Error getting Info");
                    }
                }

                @Override
                public void onComplete() {

                }


                @Override
                public void onNext(List<DataObject> dataObjects) {
                    if (view != null) {
                        view.updateData(dataObjects);
                        model.saveData(dataObjects);
                    }
                }
            }));
    }

    @Override
    public void rxUnsubscribe() {
        disposables.dispose();
    }

    @Override
    public void setView(MainActivityMVP.View view) {

        this.view = view;

    }
}
