package com.example.danie.myapplication2.ClassesJSON;

/**
 * Created by Danie on 15/05/2018.
 */
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OpenSearch$startIndex {

    @SerializedName("$t")
    @Expose
    private String $t;

    public String get$t() {
        return $t;
    }

    public void set$t(String $t) {
        this.$t = $t;
    }

}