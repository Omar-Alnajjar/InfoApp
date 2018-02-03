package com.omi.infoapp.repository.http;


import com.google.firebase.database.DataSnapshot;
import com.omi.infoapp.objects.DataObject;

import java.util.List;
import java.util.Map;

import io.reactivex.Completable;
import io.reactivex.Maybe;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface InfoApiService {

    @GET("/info/.json")
    Maybe<Map<String, DataObject>> getInfo(/*@Query("id") String lastId*/);


    @POST("/info.json")
    Completable createInfo(@Body DataObject dataObject);

}
