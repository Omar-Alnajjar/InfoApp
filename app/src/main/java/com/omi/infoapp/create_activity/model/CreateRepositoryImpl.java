package com.omi.infoapp.create_activity.model;



import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.net.Uri;
import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.omi.infoapp.objects.DataObject;
import com.omi.infoapp.repository.dp.InfoLocalStorage;
import com.omi.infoapp.repository.http.InfoApiService;

import org.reactivestreams.Subscriber;

import java.io.File;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Maybe;
import io.reactivex.Observable;
import io.reactivex.Single;

public class CreateRepositoryImpl implements CreateRepository {

    private InfoApiService infoApiService;
    private InfoLocalStorage infoLocalStorage;
    private static final String serverStorageUrl = "";
    private StorageReference storageReference;

    public CreateRepositoryImpl(InfoApiService infoApiService, InfoLocalStorage infoLocalStorage, StorageReference storageReference) {
        this.infoApiService = infoApiService;
        this.infoLocalStorage = infoLocalStorage;
        this.storageReference = storageReference;
    }


    @Override
    public Observable<String> uploadImages(File[] imageFiles) {

        return Observable.create(observableSubscriber -> {
            for(File imageFile : imageFiles) {
                Uri file = Uri.fromFile(imageFile);
                StorageReference childRef = storageReference.child(imageFile.getName());
                UploadTask uploadTask = childRef.putFile(file);
                // Register observers to listen for when the download is done or if it fails
                uploadTask.addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        // Handle unsuccessful uploads
                        observableSubscriber.onError(exception);
                    }
                }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
                        observableSubscriber.onNext(taskSnapshot.getDownloadUrl().toString());
                    }
                });

            }
        });
    }

    @Override
    public Completable createInfo(DataObject dataObject) {
        return infoApiService.createInfo(dataObject);
    }

}
