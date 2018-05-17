package com.example.danie.myapplication2;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Switch;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;

import greyfox.rxnetwork.RxNetwork;
import greyfox.rxnetwork.internal.net.RxNetworkInfo;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;


import com.example.danie.myapplication2.ClassesJSON.*;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.reactivestreams.Subscription;

import javax.inject.Inject;

public class MainActivity extends AppCompatActivity {

    @Inject RxNetwork rxNetwork;
    private RecyclerView recyclerView;
    private Feed data;
    private ArrayList<Entry> entry;
    private CardsAdapter cardsAdapter;
    private Subscription sendStateSubscription;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
        internetCheck();


    }

    private void internetCheck() {
        rxNetwork = RxNetwork.init(this);
        rxNetwork.observe()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Consumer<RxNetworkInfo>() {
                               @Override
                               public void accept(RxNetworkInfo networkInfo) throws Exception {
                                   // do sth with networkInfo like calling getState(), getType(), isConnected()
                                   // and so on (essentialy anything you'd normally do with NetworkInfo)
                                   if(networkInfo.isConnected()){
                                       Log.d("Internet: ", "Conexão feita");
                                       //loadJSON();
                                       loadJSON();
                                   }
                                   else{
                                       Log.d("Internet: ", "Falha na conexão");
                                       //Mata o processo que chama o JSON;
                                   }
                               }
                           }
                );
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if(id == R.id.action_refresh){
//            configCategorias = loadConfiguracoes();
//            if(configCategorias == null){
//                configCategorias = new ArrayList<>();
//                instanciaConfigCategorias();
//            }
            //loadJSON();
            return true;
        }
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
//            SettingsDialog dialog = new SettingsDialog();
//            dialog.rootRef(this, this);
//
//            dialog.show(getSupportFragmentManager(),"SettingsDialog");
            //loadConfiguracoes();
            //loadData();

            //Toast.makeText(MainActivity.this, "Action clicked", Toast.LENGTH_LONG).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    private void initViews(){
        recyclerView = (RecyclerView)findViewById(R.id.card_recycler_view);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
//        configCategorias = loadConfiguracoes();
//        if(configCategorias == null){
//            configCategorias = new ArrayList<>();
//            instanciaConfigCategorias();
//        }
//        loadData();
        //loadJSON();
    }
    private void loadJSON(){

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
                entry = jsonResponse.getEntrada().getEntry();
                Log.d("FEED",jsonResponse.getEntrada().toString());
                cardsAdapter = new CardsAdapter(entry);
                recyclerView.setAdapter(cardsAdapter);
            }

            @Override
            public void onError(@NonNull Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }

//    public boolean internetConnection(){
//        boolean connected = false;
//        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
//        if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
//                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
//            //we are connected to a network
//            connected = true;
//        }
//        else {
//            connected = false;
//        }
//
//        return connected;
//    }

}

