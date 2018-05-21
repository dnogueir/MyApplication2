package com.example.danie.myapplication2;

/**
 * Created by Danie on 21/05/2018.
 */

import android.app.Dialog;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.content.DialogInterface;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.content.Context;
import android.widget.Toast;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;



/**
 * Created by Danie on 07/05/2018.
 */

public class SettingsDialog extends DialogFragment {
    MainActivity rootRef;
    Context context;

    public void rootRef(MainActivity rootRef, Context context){
        this.rootRef = rootRef;
        this.context = context;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.configura_categorias, null));

        builder.setPositiveButton("Salvar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                Switch s_tfg = (Switch) getDialog().findViewById(R.id.categoria_tfg);
                Switch s_ca = (Switch) getDialog().findViewById(R.id.categoria_ca);
                Switch s_estagio = (Switch) getDialog().findViewById(R.id.categoria_estagio);
                Switch s_geral = (Switch) getDialog().findViewById(R.id.categoria_geral);
                Switch s_equipes = (Switch) getDialog().findViewById(R.id.categoria_equipes);


                ArrayList<ConfiguracoesCategorias> cc = new ArrayList<ConfiguracoesCategorias>();


                ConfiguracoesCategorias aux1 = new ConfiguracoesCategorias();
                aux1.setCategoria("TFG");
                aux1.setIncrito(s_tfg.isChecked());
                cc.add(aux1);


                ConfiguracoesCategorias aux2 = new ConfiguracoesCategorias();
                aux2.setCategoria("Estágio");
                aux2.setIncrito(s_estagio.isChecked());
                cc.add(aux2);

                ConfiguracoesCategorias aux3 = new ConfiguracoesCategorias();
                aux3.setCategoria("CA");
                aux3.setIncrito(s_ca.isChecked());
                cc.add(aux3);

                ConfiguracoesCategorias aux4 = new ConfiguracoesCategorias();
                aux4.setCategoria("Geral");
                aux4.setIncrito(s_geral.isChecked());
                cc.add(aux4);

                ConfiguracoesCategorias aux5 = new ConfiguracoesCategorias();
                aux5.setCategoria("Equipes");
                aux5.setIncrito(s_equipes.isChecked());
                cc.add(aux5);


                saveConfiguracoes(cc);


            }
        })
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                    }
                })
                .setTitle("Configurações");

        return builder.create();
    }

    public void saveConfiguracoes(ArrayList<ConfiguracoesCategorias> data){
        SharedPreferences sharedPreferences = context.getSharedPreferences("Configuracoes", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String jsonData = gson.toJson(data);
        editor.putString("inscricoes",jsonData);
        editor.apply();

    }

    public ArrayList<ConfiguracoesCategorias> loadConfiguracoes(){
        SharedPreferences sharedPreferences =  context.getSharedPreferences("Configuracoes", Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String jsonData = sharedPreferences.getString("inscricoes", null);
        Type type = new TypeToken<ArrayList<ConfiguracoesCategorias>>() {}.getType();
        return gson.fromJson(jsonData, type);
    }

    @Override
    public void onStart() {
        super.onStart();
        Button positive = ((AlertDialog) getDialog()).getButton(AlertDialog.BUTTON_POSITIVE);
        positive.setTextColor(Color.BLUE);

        Button negative = ((AlertDialog) getDialog()).getButton(AlertDialog.BUTTON_NEGATIVE);
        negative.setTextColor(Color.RED);

        ArrayList<ConfiguracoesCategorias> configuracoesCategorias = new ArrayList<>();
        configuracoesCategorias = loadConfiguracoes();

        if(configuracoesCategorias != null ){
            Switch s_tfg1 = (Switch) getDialog().findViewById(R.id.categoria_tfg);
            Switch s_estagio1 = (Switch) getDialog().findViewById(R.id.categoria_estagio);
            Switch s_ca1 = (Switch) getDialog().findViewById(R.id.categoria_ca);
            Switch s_geral1 = (Switch) getDialog().findViewById(R.id.categoria_geral);
            Switch s_equipes1 = (Switch) getDialog().findViewById(R.id.categoria_equipes);

            s_tfg1.setChecked(configuracoesCategorias.get(0).getInscrito());
            s_estagio1.setChecked(configuracoesCategorias.get(1).getInscrito());
            s_ca1.setChecked(configuracoesCategorias.get(2).getInscrito());
            s_geral1.setChecked(configuracoesCategorias.get(3).getInscrito());
            s_equipes1.setChecked(configuracoesCategorias.get(4).getInscrito());
        }

        //positive.setBackgroundColor(getResources().getColor(R.color.GrayBGColor));
    }
}
