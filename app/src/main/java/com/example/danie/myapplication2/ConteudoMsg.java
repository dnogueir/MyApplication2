package com.example.danie.myapplication2;

/**
 * Created by Danie on 21/05/2018.
 */

public class ConteudoMsg {

    private String Categoria;
    private String Data_Postagem;
    private String Data_Expira;
    private String Mensagem;
    private String Titulo;

    public void setCategoria(String Categoria){
        this.Categoria = Categoria;
    }
    public void setTitulo(String Titulo){
        this.Titulo = Titulo;
    }
    public void setMensagem(String Mensagem){
        this.Mensagem = Mensagem;
    }
    public void setDataExpira(String Data_Expira){
        this.Data_Expira = Data_Expira;
    }
    public void setDataPostagem(String Data_Postagem){
        this.Data_Postagem = Data_Postagem;
    }
    public String getCategoria(){
        return Categoria;
    }

    public String getDataPostagem(){
        return Data_Postagem;
    }

    public String getDataExpira(){
        return Data_Expira;
    }

    public String getMensagem(){
        return Mensagem;
    }

    public String getTitulo(){return Titulo;}
}
