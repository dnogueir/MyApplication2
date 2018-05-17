package com.example.danie.myapplication2;

/**
 * Created by Danie on 15/05/2018.
 */
import com.example.danie.myapplication2.ClassesJSON.*;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import io.reactivex.Observable;

public interface RequestInterface {

    @GET("feeds/list/1fm9G38HBVr8XfbDqzDrz2GKo-NMF1X37Rskqjkxxcv8/od6/public/values?alt=json")
    //Call<JSONResponse> getJSON();
   Observable<JSONResponse> getEntrada();

}
