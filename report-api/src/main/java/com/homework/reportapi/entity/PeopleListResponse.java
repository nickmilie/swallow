package com.homework.reportapi.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PeopleListResponse {

    @JsonProperty("count")
    private int count;

    @JsonProperty("next")
    private String next;

    @JsonProperty("results")
    private List<Person> people;

    public PeopleListResponse() {
    }

    public PeopleListResponse(int count, String next, List<Person> people) {
        this.count = count;
        this.next = next;
        this.people = people;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    /* AAAAAAAAAAAAAAAaaaaAAAAAaaAaaaa
    * FOUR DAYS
    * http must be HTTPS !!!!!! S!!!!!!!!! SSSS!!!!*/

    public String getNext() {
        if(next != null) {
            return "https" + next.substring(4);
        }
        else return null;
    }

    public void setNext(String next) {
        this.next = next;
    }

    public List<Person> getPeople() {
        return people;
    }

    public void setPeople(List<Person> people) {
        this.people = people;
    }


}
