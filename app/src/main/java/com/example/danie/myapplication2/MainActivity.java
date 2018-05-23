package com.example.danie.myapplication2;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
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
    static MainActivity instance;
    private ArrayList<ConteudoMsg> conteudoMsg;
    private ArrayList<ConfiguracoesCategorias> configCategorias;
    private CoordinatorLayout coordinatorLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        instance = this;
        configCategorias = new ArrayList<>();

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
                                       callService();

                                   }
                                   else{
                                       Log.d("Internet: ", "Falha na conexão");
                                       stopService();
                                       configCategorias = loadConfiguracoes();
                                       if(configCategorias == null){
                                           configCategorias = new ArrayList<>();
                                           instanciaConfigCategorias();
                                       }
                                       loadData();
                                   }
                               }
                           }
                );
    }

    private void stopService() {

        Intent intent = new Intent("com.example.danie.myapplication2.MyIntentService.SERVICO_INTENT");
        intent.putExtra("desligar", 1);
        intent.setPackage("com.example.danie.myapplication2");
        startService(intent);
        Snackbar snackbar = Snackbar
                .make(coordinatorLayout, "Internet connection lost!!", Snackbar.LENGTH_LONG);

        snackbar.show();


    }

    private void callService() {
        Intent intent = new Intent("com.example.danie.myapplication2.MyIntentService.SERVICO_INTENT");
        intent.setPackage("com.example.danie.myapplication2");
        startService(intent);
        Log.d("Internet: ", "Servico chamado");
    }

    public void callCardViews(JSONResponse jsonResponse){
        entry = jsonResponse.getEntrada().getEntry();

        configCategorias = loadConfiguracoes();
        if(configCategorias == null){
            configCategorias = new ArrayList<>();
            instanciaConfigCategorias();
        }
        Log.d("cards","SaveData()");
        saveData();
        Log.d("cards","FilterInformation()");
        filterInformation();
        Log.d("cards","Adapter()");
        cardsAdapter = new CardsAdapter(conteudoMsg);
        recyclerView.setAdapter(cardsAdapter);
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
            SettingsDialog dialog = new SettingsDialog();
            dialog.rootRef(this, this);

            dialog.show(getSupportFragmentManager(),"SettingsDialog");
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
        coordinatorLayout = (CoordinatorLayout) findViewById(R.id
                .coordinatorLayout);
        configCategorias = loadConfiguracoes();
        if(configCategorias == null){
            configCategorias = new ArrayList<>();
            instanciaConfigCategorias();
        }
//        loadData();
        //loadJSON();
    }
    public ArrayList<ConfiguracoesCategorias> loadConfiguracoes(){
        SharedPreferences sharedPreferences =  getSharedPreferences("Configuracoes", MODE_PRIVATE);
        Gson gson = new Gson();
        String jsonData = sharedPreferences.getString("inscricoes", null);
        Type type = new TypeToken<ArrayList<ConfiguracoesCategorias>>() {}.getType();
        return gson.fromJson(jsonData, type);
    }

    private void filterInformation() {
        conteudoMsg = new ArrayList<>();
        for (int i = 0; i < entry.size(); i++) {
            for (int j = 0; j < configCategorias.size(); j++) {
                if (configCategorias.get(j).getInscrito() && configCategorias.get(j).getCategoria().equals(entry.get(i).getGsx$categoria().get$t().toString())) {
                    ConteudoMsg aux = new ConteudoMsg();
                    aux.setCategoria(entry.get(i).getGsx$categoria().get$t().toString());
                    aux.setDataPostagem(entry.get(i).getGsx$datadepublicaO().get$t().toString());
                    aux.setDataExpira(entry.get(i).getGsx$datadeexpiraO().get$t().toString());
                    aux.setMensagem(entry.get(i).getGsx$mensagem().get$t().toString());
                    aux.setTitulo(entry.get(i).getGsx$tTulo().get$t().toString());
                    conteudoMsg.add(aux);
                    j = configCategorias.size();
                }
            }
        }
    }

    public void instanciaConfigCategorias(){

        ConfiguracoesCategorias aux1 = new ConfiguracoesCategorias();
        aux1.setCategoria("TFG");
        aux1.setIncrito(false);
        configCategorias.add(aux1);


        ConfiguracoesCategorias aux2 = new ConfiguracoesCategorias();
        aux2.setCategoria("Estágio");
        aux2.setIncrito(false);
        configCategorias.add(aux2);

        ConfiguracoesCategorias aux3 = new ConfiguracoesCategorias();
        aux3.setCategoria("CA");
        aux3.setIncrito(false);
        configCategorias.add(aux3);

        ConfiguracoesCategorias aux4 = new ConfiguracoesCategorias();
        aux4.setCategoria("Geral");
        aux4.setIncrito(false);
        configCategorias.add(aux4);

        ConfiguracoesCategorias aux5 = new ConfiguracoesCategorias();
        aux5.setCategoria("Equipes");
        aux5.setIncrito(false);
        configCategorias.add(aux5);

    }

    private void saveData(){
        SharedPreferences sharedPreferences = getSharedPreferences("Caixa_Entrada", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String jsonData = gson.toJson(entry);
        editor.putString("Lista_de_mensagens",jsonData);
        editor.apply();
    }

    private void loadData(){
        SharedPreferences sharedPreferences = getSharedPreferences("Caixa_Entrada", MODE_PRIVATE);
        Gson gson = new Gson();
        String jsonData = sharedPreferences.getString("Lista_de_mensagens", null);
        Type type = new TypeToken<ArrayList<Entry>>() {}.getType();
        entry = gson.fromJson(jsonData, type);
        Log.d("sem_internet","Chamou");
        //CHECA SE NULL OU NÃO ANTES DE EXIBIR
        if(entry.size() > 0 ){
           // entry  = data.getEntry();
            if(checaCategorias()){
                Log.d("sem_internet","loadData()");
                filterInformation();
                cardsAdapter = new CardsAdapter(conteudoMsg);
                recyclerView.setAdapter(cardsAdapter);
            }
        }
    }

    //se for inscrito em pelo menos 1 categoria retorna true
    public boolean checaCategorias(){
        for(int i = 0 ; i < configCategorias.size() ; i++){
            if(configCategorias.get(i).getInscrito())
                return true;
        }
        return false;
    }
}

