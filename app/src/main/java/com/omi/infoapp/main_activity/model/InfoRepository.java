package com.omi.infoapp.main_activity.model;



import com.omi.infoapp.objects.DataObject;
import com.omi.infoapp.repository.dp.InfoLocalStorage;
import com.omi.infoapp.repository.http.InfoApiService;

import java.util.List;

import io.reactivex.Maybe;
import io.reactivex.Observable;

public class InfoRepository implements Repository {

    private InfoApiService infoApiService;
    private InfoLocalStorage infoLocalStorage;

    public InfoRepository(InfoApiService infoApiService, InfoLocalStorage infoLocalStorage) {
        this.infoApiService = infoApiService;
        this.infoLocalStorage = infoLocalStorage;
    }


    @Override
    public Maybe<List<DataObject>> getResultsFromMemory(String cityName) {
        return infoLocalStorage.getInfo();
    }

    @Override
    public Maybe<List<DataObject>> getResultsFromNetwork(String lastId) {
        Maybe<List<DataObject>> apiResult = infoApiService.getInfo(lastId);
        return apiResult;
        /*return apiResult.flatMap(new Function<Result, Maybe<FinalWeatherData>>() {

            @Override
            public Maybe<FinalWeatherData> apply(Result result) throws Exception {
                if(result != null) {
                    return Maybe.just(new FinalWeatherData(result.getName(), result.getMain().getTempMax(), result.getMain().getTempMin(), result.getWeather().get(0).getDescription()));
                }else {
                    return Maybe.empty();
                }
            }
        }).doOnSuccess(new Consumer<FinalWeatherData>() {
            @Override
            public void accept(FinalWeatherData weatherData) throws Exception {
                infoLocalStorage.saveOrUpdateWeather(weatherData);
            }
        });*/
    }


    @Override
    public Observable<List<DataObject>> getResultData(String cityName) {
        return getResultsFromMemory(cityName).concatWith(getResultsFromNetwork(cityName)).toObservable();
        /*flatMapObservable(new Function<FinalWeatherData, ObservableSource<? extends FinalWeatherData>>() {
                    @Override
                    public ObservableSource<? extends FinalWeatherData> apply(FinalWeatherData finalWeatherData) throws Exception {
                        return Observable.just(finalWeatherData);
                    }
                });*/

    }

    @Override
    public Observable<List<DataObject>> saveInfoToDB(List<DataObject> dataObjects) {
        return infoLocalStorage.saveInfo(dataObjects);
    }
}
