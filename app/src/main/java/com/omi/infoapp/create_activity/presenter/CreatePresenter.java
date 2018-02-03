package com.omi.infoapp.create_activity.presenter;


import android.content.Context;
import android.os.Environment;

import com.omi.infoapp.compresser.Compressor;
import com.omi.infoapp.create_activity.CreateActivityMVP;
import com.omi.infoapp.objects.DataObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.CompletableObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class CreatePresenter implements CreateActivityMVP.Presenter {

    private CreateActivityMVP.View view;
    private CreateActivityMVP.Model model;
    private CompositeDisposable disposables;
    private List<String> imageUrls = new ArrayList<>();
    private Context context;

    public CreatePresenter(CreateActivityMVP.Model model, Context context) {
        this.model = model;
        this.context = context;
        disposables = new CompositeDisposable();
    }


    @Override
    public void compressImages(File imageFile) {
        try {
            disposables.add(new Compressor(context)
                    .setMaxHeight(900)
                    .setMaxWidth(900)
                    .setRadius(25)
                    .setDestinationDirectoryPath(Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "InfoApp" + File.separator + "Images")
                    .compressToFile(imageFile).subscribeOn(Schedulers.computation()).observeOn(AndroidSchedulers.mainThread())


                    .subscribeWith(new DisposableObserver<File[]>() {



                        @Override
                        public void onError(Throwable e) {
                            e.printStackTrace();
                            if (view != null) {
                                view.showSnackbar("Error compressing images");
                            }
                        }

                        @Override
                        public void onComplete() {

                        }


                        @Override
                        public void onNext(File[] files) {

                            if(files.length > 0){
                                uploadImages(files);
                            }
                        }
                    }));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void uploadImages(File[] imageFiles) {
        model.uploadImages(imageFiles).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<String>() {



            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
                if (view != null) {
                    view.showSnackbar("Error uploading images");
                }
            }

            @Override
            public void onComplete() {

            }


            @Override
            public void onNext(String fileUrl) {
                imageUrls.add(fileUrl);
                if(view != null){
                    view.setImage(fileUrl);
                }
            }
        });

    }

    @Override
    public boolean uploadWasDone() {
        if(imageUrls.size() != 2){
            if(view != null){
                view.showSnackbar("Please wait until upload dene");
                return false;
            }
        }
        return true;
    }

    @Override
    public void createInfo(DataObject dataObject) {
        if(!uploadWasDone()){
            return;
        }
        if(imageUrls.get(0).contains("blur")) {
            dataObject.setDataImageBlur(imageUrls.get(0));
            dataObject.setDataImage(imageUrls.get(1));
        }else {
            dataObject.setDataImageBlur(imageUrls.get(1));
            dataObject.setDataImage(imageUrls.get(0));
        }

        model.createInfo(dataObject).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new CompletableObserver() {
                    @Override
                    public void onError(Throwable e) {
                        if(view != null){
                            view.showSnackbar("Error creating info");
                        }
                    }

                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onComplete() {
                        if(view != null){
                            view.doneCreate();
                        }
                    }
                });
    }

    @Override
    public void rxUnsubscribe() {
        disposables.dispose();
    }

    @Override
    public void setView(CreateActivityMVP.View view) {
        this.view = view;
    }
}
