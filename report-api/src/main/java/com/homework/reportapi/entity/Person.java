package com.homework.reportapi.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Person {

    @JsonProperty("name")
    private String name;;

    @JsonProperty("homeworld")
    private String homeWorld;

    @JsonProperty
    private String url;

    @JsonProperty("films")
    private List<String> films;

    public Person() {
    }

    public Person(String name, String homeWorld) {
        this.name = name;
        this.homeWorld = homeWorld;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHomeWorld() {
        return "https" + homeWorld.substring(4) + "?format=json";
    }

    public void setHomeWorld(String homeWorld) {
        this.homeWorld = homeWorld;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<String> getFilms() {
        return films;
    }

    public void setFilms(List<String> films) {
        this.films = films;
    }
}
