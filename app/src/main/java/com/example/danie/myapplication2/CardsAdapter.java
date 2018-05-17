package com.example.danie.myapplication2;

/**
 * Created by Danie on 15/05/2018.
 */
import android.content.Context;
        import android.content.Intent;
        import android.os.Bundle;
        import android.os.DropBoxManager;
        import android.support.v7.widget.CardView;
        import android.support.v7.widget.RecyclerView;
        import android.util.Log;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.ImageView;
        import android.widget.TextView;
        import android.widget.Toast;

        import java.util.ArrayList;

        import com.amulyakhare.textdrawable.TextDrawable;
        import com.amulyakhare.textdrawable.util.ColorGenerator;
        import com.example.danie.myapplication2.ClassesJSON.*;



public class CardsAdapter extends RecyclerView.Adapter<CardsAdapter.ViewHolder> {
    private ArrayList<Entry> entradas;
    private Intent intent = null;
    ColorGenerator generator = ColorGenerator.MATERIAL;

    // OBJETO COM CATEGORIAS INSCRITAS DO SHAREDPREFERENCES

    public CardsAdapter(ArrayList<Entry> entradas) {
        this.entradas = entradas;
    }

    @Override
    public CardsAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_row, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CardsAdapter.ViewHolder viewHolder, final int i) {
        // FALTA FILTRAR INFORMAÇÕES

        viewHolder.categoria_msg.setText(entradas.get(i).getGsx$categoria().get$t().toString());
        viewHolder.data_publicacao.setText(entradas.get(i).getGsx$datadepublicaO().get$t().toString());
        viewHolder.titulo.setText(entradas.get(i).getGsx$tTulo().get$t().toString());
        //PEGA A PRIMEIRA LETRA DA CATEGORIA
        String letter = String.valueOf(entradas.get(i).getGsx$categoria().get$t().toString().charAt(0));

        //CRIA UMA COR RANDÔMICA
        TextDrawable drawable = TextDrawable.builder()
                .buildRound(letter, generator.getRandomColor());

        viewHolder.letter.setImageDrawable(drawable);


        final Context context = viewHolder.context;
        viewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                ConteudoMsg currentValue = entradas.get(i);
//                intent = new Intent(context, MessageActivity.class);
//                Bundle b = new Bundle();
//
//                b.putString("mensagem", entradas.get(i).getMensagem());
//                b.putInt("key", 1); //Your id
//                intent.putExtras(b); //Put your id to your next Intent
//                context.startActivity(intent);
                //Log.d("CardView", "CardView Clicked: " + currentValue);
                Log.d("CardView", "CardView Clicked");
            }
        });
    }

    @Override
    public int getItemCount() {
        return entradas.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView categoria_msg,data_publicacao,titulo;
        private CardView cardView;
        private final Context context;
        private ImageView letter;
        public ViewHolder(View view) {
            super(view);

            letter = (ImageView) itemView.findViewById(R.id.gmailitem_letter);
            context = view.getContext();
            cardView = (CardView) view.findViewById(R.id.card_view);
            categoria_msg = (TextView)view.findViewById(R.id.categoria_msg);
            data_publicacao = (TextView)view.findViewById(R.id.data_publicacao);
            titulo = (TextView)view.findViewById(R.id.titulo);

        }
    }

}
