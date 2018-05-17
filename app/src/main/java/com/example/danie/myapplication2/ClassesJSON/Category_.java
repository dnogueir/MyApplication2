package com.example.danie.myapplication2.ClassesJSON;

/**
 * Created by Danie on 15/05/2018.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Category_ {

    @SerializedName("scheme")
    @Expose
    private String scheme;
    @SerializedName("term")
    @Expose
    private String term;

    public String getScheme() {
        return scheme;
    }

    public void setScheme(String scheme) {
        this.scheme = scheme;
    }

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }

}