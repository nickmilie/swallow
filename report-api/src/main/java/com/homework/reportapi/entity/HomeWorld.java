package com.homework.reportapi.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class HomeWorld {

    @JsonProperty("name")
    private String name;

    @JsonProperty("url")
    private String url;

    public HomeWorld() {
    }

    public HomeWorld(String name, String url) {
        this.name = name;
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return "https" + url.substring(4) + "?format=json";
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
