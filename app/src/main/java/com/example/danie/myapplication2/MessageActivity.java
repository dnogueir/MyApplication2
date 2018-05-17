package com.example.danie.myapplication2;

/**
 * Created by Danie on 15/05/2018.
 */

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import com.example.danie.myapplication2.ClassesJSON.*;
/**
 * Created by Danie on 05/05/2018.
 */

public class MessageActivity extends AppCompatActivity{


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mensagem);

        TextView Mensagem = (TextView)findViewById(R.id.mensagem_completa);

        Bundle b = getIntent().getExtras();
        // int value = b.getInt("key");
        String msg = b.getString("mensagem");

        Mensagem.setText(msg);


    }
}