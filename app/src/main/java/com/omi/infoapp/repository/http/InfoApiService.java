package com.omi.infoapp.repository.http;


import com.omi.infoapp.objects.DataObject;

import java.util.List;

import io.reactivex.Maybe;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface InfoApiService {

    @GET("id")
    Maybe<List<DataObject>> getInfo(@Query("id") String lastId);
}
