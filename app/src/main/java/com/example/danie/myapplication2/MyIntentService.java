package com.example.danie.myapplication2;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.danie.myapplication2.ClassesJSON.JSONResponse;

import java.util.Timer;
import java.util.TimerTask;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Danie on 17/05/2018.
 */

public class MyIntentService extends IntentService {

    private boolean stopService;

    public MyIntentService(){
        super("MyIntentService");
        stopService = true;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId){

        Bundle b  = intent.getExtras();
        if(b != null){
            int desligar = b.getInt("desligar");
            if(desligar == 1){
                stopService = false;
            }
        }
        return(super.onStartCommand(intent,flags,startId));
    }

    @Override
    protected void onHandleIntent(Intent intent){
         long period;
        while(stopService) {
            Log.d("SERVICO", "Rodando");
            loadRetrofit();

             period = 30000;
            try{
                Log.d("SERVICO", "Dormindo");
                Thread.sleep(period);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }
        Log.d("SERVICO", "Parando");
    }

    private void loadRetrofit(){
        if(stopService){
            Log.d("SERVICO", "Carregando retrofit");
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("https://spreadsheets.google.com/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();

            RequestInterface requestInterface = retrofit.create(RequestInterface.class);
            Observable<JSONResponse> observable = requestInterface.getEntrada().subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread());

            observable.subscribe(new Observer<JSONResponse>() {
                @Override
                public void onSubscribe(@NonNull Disposable d) {

                }

                @Override
                public void onNext(@NonNull JSONResponse jsonResponse) {
                    // retorna informacao para main
                    MainActivity mainActivity = MainActivity.instance;
                    mainActivity.callCardViews(jsonResponse);
                }

                @Override
                public void onError(@NonNull Throwable e) {
                    Log.d("SERVICO", "erro");
                }

                @Override
                public void onComplete() {

                }
            });

        }
        else{

            Log.d("SERVICO", "Servico parado");
        }
    }
}
