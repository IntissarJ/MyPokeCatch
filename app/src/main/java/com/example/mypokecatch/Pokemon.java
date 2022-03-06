package com.example.mypokecatch;

import java.io.Serializable;

public class Pokemon implements Serializable {

    private int id;
    private String name;
    private String url;

    public Pokemon(String name, String url){
        this.url = url;
        this.name = name;
    }
    public int getId() {
        return Integer.parseInt(url.replaceAll("[\\D]", "").substring(1));
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
