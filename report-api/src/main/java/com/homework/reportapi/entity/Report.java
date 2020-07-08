package com.homework.reportapi.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="report")
//@JsonIgnoreProperties
public class Report {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="report_id")
    private int report_id;

    @Column(name="query_criteria_character_phrase")
    private String query_criteria_character_phrase;

    @Column(name="query_criteria_planet_name")
    private String query_criteria_planet_name;


    @OneToMany(orphanRemoval = true,
            mappedBy = "report",
            cascade = {CascadeType.ALL})
//    @Column(nullable = true)
    @JsonManagedReference
    private List<Result> resultList;

    public Report() {
    }

    public Report(String query_criteria_character_phrase, String query_criteria_planet_name) {
        this.query_criteria_character_phrase = query_criteria_character_phrase;
        this.query_criteria_planet_name = query_criteria_planet_name;
    }

    public int getReport_id() {
        return report_id;
    }

    public void setReport_id(int report_id) {
        this.report_id = report_id;
    }

    public String getQuery_criteria_character_phrase() {
        return query_criteria_character_phrase;
    }

    public void setQuery_criteria_character_phrase(String query_criteria_character_phrase) {
        this.query_criteria_character_phrase = query_criteria_character_phrase;
    }

    public String getQuery_criteria_planet_name() {
        return query_criteria_planet_name;
    }

    public void setQuery_criteria_planet_name(String query_criteria_planet_name) {
        this.query_criteria_planet_name = query_criteria_planet_name;
    }

    public List<Result> getResultList() {
        return resultList;
    }

/*    public void setResultList(List<Result> resultList) {
        this.resultList=new ArrayList<>();

        //report to result
        this.resultList = resultList;

        //need to link result to Report
        //as report linked to result
        for(Result result : this.resultList){
            result.setReport(this);
        }

    }*/

    public void addResult(Result result){
        if(resultList==null){
            resultList=new ArrayList<>();
        }

        resultList.add(result);

        result.setReport(this);
    }
}
