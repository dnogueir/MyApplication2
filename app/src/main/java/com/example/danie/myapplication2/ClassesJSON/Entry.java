package com.example.danie.myapplication2.ClassesJSON;

/**
 * Created by Danie on 15/05/2018.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Danie on 02/05/2018.
 */

public class Entry {

    @SerializedName("id")
    @Expose
    private Id_ id;
    @SerializedName("updated")
    @Expose
    private Updated_ updated;
    @SerializedName("category")
    @Expose
    private ArrayList<Category_> category = null;
    @SerializedName("title")
    @Expose
    private Title_ title;
    @SerializedName("content")
    @Expose
    private Content content;
    @SerializedName("link")
    @Expose
    private ArrayList<Link_> link = null;
    @SerializedName("gsx$categoria")
    @Expose
    private Gsx$categoria gsx$categoria;
    @SerializedName("gsx$datadepublica\u00e7\u00e3o")
    @Expose
    private Gsx$datadepublicaO gsx$datadepublicaO;
    @SerializedName("gsx$datadeexpira\u00e7\u00e3o")
    @Expose
    private Gsx$datadeexpiraO gsx$datadeexpiraO;
    @SerializedName("gsx$mensagem")
    @Expose
    private Gsx$mensagem gsx$mensagem;
    @SerializedName("gsx$t\u00edtulo")
    @Expose
    private Gsx$tTulo gsx$tTulo;

    public Id_ getId() {
        return id;
    }

    public void setId(Id_ id) {
        this.id = id;
    }

    public Updated_ getUpdated() {
        return updated;
    }

    public void setUpdated(Updated_ updated) {
        this.updated = updated;
    }

    public ArrayList<Category_> getCategory() {
        return category;
    }

    public void setCategory(ArrayList<Category_> category) {
        this.category = category;
    }

    public Title_ getTitle() {
        return title;
    }

    public void setTitle(Title_ title) {
        this.title = title;
    }

    public Content getContent() {
        return content;
    }

    public void setContent(Content content) {
        this.content = content;
    }

    public ArrayList<Link_> getLink() {
        return link;
    }

    public void setLink(ArrayList<Link_> link) {
        this.link = link;
    }

    public Gsx$categoria getGsx$categoria() {
        return gsx$categoria;
    }

    public void setGsx$categoria(Gsx$categoria gsx$categoria) {
        this.gsx$categoria = gsx$categoria;
    }

    public Gsx$datadepublicaO getGsx$datadepublicaO() {
        return gsx$datadepublicaO;
    }

    public void setGsx$datadepublicaO(Gsx$datadepublicaO gsx$datadepublicaO) {
        this.gsx$datadepublicaO = gsx$datadepublicaO;
    }

    public Gsx$datadeexpiraO getGsx$datadeexpiraO() {
        return gsx$datadeexpiraO;
    }

    public void setGsx$datadeexpiraO(Gsx$datadeexpiraO gsx$datadeexpiraO) {
        this.gsx$datadeexpiraO = gsx$datadeexpiraO;
    }

    public Gsx$mensagem getGsx$mensagem() {
        return gsx$mensagem;
    }

    public void setGsx$mensagem(Gsx$mensagem gsx$mensagem) {
        this.gsx$mensagem = gsx$mensagem;
    }

    public Gsx$tTulo getGsx$tTulo() {
        return gsx$tTulo;
    }

    public void setGsx$tTulo(Gsx$tTulo gsx$tTulo) {
        this.gsx$tTulo = gsx$tTulo;
    }

}

