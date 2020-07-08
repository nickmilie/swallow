package com.homework.reportapi.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

@Entity
@Table(name = "result")
//@JsonIgnoreProperties
public class Result {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "result_id")
    private int result_id;

    @Column(name = "film_id")
    private int film_id;

    @Column(name = "character_id")
    private int character_id;

    @Column(name = "planet_id")
    private int planet_id;

    @Column(name = "film_name")
    private String film_name;

    @Column(name = "character_name")
    private String character_name;

    @Column(name = "planet_name")
    private String planet_name;

    @ManyToOne(
            cascade = {CascadeType.DETACH, CascadeType.MERGE,
            CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.REMOVE})/* , CascadeType.REMOVE*/
    @JoinColumn(name = "report_id")
    @JsonBackReference
    private Report report;


    public Result() {
    }

    public Result(int film_id, int character_id, int planet_id, String film_name, String character_name, String planet_name) {
        this.film_id = film_id;
        this.character_id = character_id;
        this.planet_id = planet_id;
        this.film_name = film_name;
        this.character_name = character_name;
        this.planet_name = planet_name;
    }

    public int getResult_id() {
        return result_id;
    }

    public void setResult_id(int result_id) {
        this.result_id = result_id;
    }

    public int getFilm_id() {
        return film_id;
    }

    public void setFilm_id(int film_id) {
        this.film_id = film_id;
    }

    public int getCharacter_id() {
        return character_id;
    }

    public void setCharacter_id(int character_id) {
        this.character_id = character_id;
    }

    public int getPlanet_id() {
        return planet_id;
    }

    public void setPlanet_id(int planet_id) {
        this.planet_id = planet_id;
    }

    public String getFilm_name() {
        return film_name;
    }

    public void setFilm_name(String film_name) {
        this.film_name = film_name;
    }

    public String getCharacter_name() {
        return character_name;
    }

    public void setCharacter_name(String character_name) {
        this.character_name = character_name;
    }

    public String getPlanet_name() {
        return planet_name;
    }

    public void setPlanet_name(String planet_name) {
        this.planet_name = planet_name;
    }

    public Report getReport() {
        return report;
    }

    public void setReport(Report report) {
        this.report = report;
    }

    @Override
    public String toString() {
        return "Result{" +
                "result_id=" + result_id +
                ", film_id=" + film_id +
                ", character_id=" + character_id +
                ", planet_id=" + planet_id +
                ", film_name='" + film_name + '\'' +
                ", character_name='" + character_name + '\'' +
                ", planet_name='" + planet_name + '\'' +
                '}';
    }
}
