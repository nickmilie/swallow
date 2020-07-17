package com.homework.reportapi.rest;

import com.homework.reportapi.entity.*;
import com.homework.reportapi.exception.ReportNotFoundException;
import com.homework.reportapi.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ReportRestController {

    @Autowired
    private ReportService reportService;

    private RestTemplate restTemplate;

    @GetMapping("/report")
    public List<Report> getReportList() {
        return reportService.getReportList();
    }

    @GetMapping("/report/{reportId}")
    public Report getReport(@PathVariable int reportId){

        Report report = reportService.getReport(reportId);

        if(report == null){
            throw new ReportNotFoundException("Customer id not found - " + reportId);
        }

        return report;
    }

    @PutMapping("/report/{reportId}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void putReport(@PathVariable int reportId, @RequestBody Criteria criteria){

        String phrase = criteria.getQuery_criteria_character_phrase();
        String planet_name = criteria.getQuery_criteria_planet_name();

        Report report = createReport(phrase, planet_name);

        List<Report> reportList = reportService.getReportList();

        List<Integer> reportIdList = new ArrayList<>();

        for(Report databaseReport : reportList){

            reportIdList.add(databaseReport.getReport_id());

        }

        if(reportIdList.contains(reportId)){
            report.setReport_id(reportId);
            reportService.saveReport(report);
        }

        else {
            report.setReport_id(0);
            reportService.saveReport(report);
        }

    }

    @DeleteMapping("/report")
    public void deleteReportList(){

        List<Report> reportList = reportService.getReportList();
        for(Report report : reportList){

            reportService.deleteReport(report.getReport_id());
        }

    }


    @DeleteMapping("/report/{reportId}")
    public void deleteReport(@PathVariable int reportId){

        Report report = reportService.getReport(reportId);

        if(report == null){
            throw new ReportNotFoundException("Report id is not found - " + reportId);
        }

        reportService.deleteReport(reportId);
    }


    @GetMapping("/search")
    public List<Film> search(@RequestParam(defaultValue = "Luke", value = "phrase") String phrase,
                             @RequestParam(defaultValue = "Tatooine", value = "planet_name") String planet_name){


        List<Person> peopleWithHomeWorld = getPeopleWithHomeWorld(phrase, planet_name);

        List<String> filmUrlList = getFilmUrls(phrase, planet_name, peopleWithHomeWorld);

        List<Film> films = getFilms(phrase, planet_name, filmUrlList);

        Report report = composeReport(phrase, planet_name, films, filmUrlList, peopleWithHomeWorld);

        reportService.saveReport(report);

        return films;
    }


    private Report createReport(String phrase, String planet_name){

        List<Person> peopleWithHomeWorld = getPeopleWithHomeWorld(phrase, planet_name);

        List<String> filmUrlList = getFilmUrls(phrase, planet_name, peopleWithHomeWorld);

        List<Film> films = getFilms(phrase, planet_name, filmUrlList);

        Report report = composeReport(phrase, planet_name, films, filmUrlList, peopleWithHomeWorld);

        return report;

    }

    private Report composeReport(String phrase, String planet_name,
                                List<Film> films, List<String> filmUrlList,
                                List<Person> peopleWithHomeWorld){

        Report report = new Report(phrase,planet_name);

        for(Person person : peopleWithHomeWorld){

            for (String filmUrl : person.getFilms()){

                if(filmUrlList.contains(filmUrl)){

//                    Lists: filmUrlList and films has the same indexation
//                    So I've took th index from filmUrlList to get the Object from films
//                    to not recall restTemplate again

                    Film film = films.get(filmUrlList.indexOf(filmUrl));

                    HomeWorld homeWorld = restTemplate.getForObject(person.getHomeWorld(),HomeWorld.class);

                    Result result = new Result(
                            cutIndexForFilm(film.getUrl()),
                            cutIndexForCharacter(person.getUrl()),
                            cutIndexForPlanet(homeWorld.getUrl()),
                            film.getTitle(),
                            person.getName(),
                            homeWorld.getName());

                    report.addResult(result);

                }

            }

        }

        return report;

    }

    private List<Film> getFilms(String phrase, String planet_name, List<String> filmUrlList){

        List<Film> films = new ArrayList<>();

        for(String filmUrl : filmUrlList){
            films.add(restTemplate.getForObject("https" + filmUrl.substring("http".length()),Film.class));
        }

        return films;
    }

    private List<String> getFilmUrls(String phrase, String planet_name, List<Person> peopleWithHomeWorld){

        List<String> filmUrlList = new ArrayList<>();

        for (Person person : peopleWithHomeWorld) {

            for(String filmUrl : person.getFilms()){

                if(!filmUrlList.contains(filmUrl)){
                    filmUrlList.add(filmUrl);
                }
            }

        }

        return filmUrlList;
    }

    private List<Person> getPeopleWithHomeWorld(String phrase, String planet_name){

        String url = "https://swapi.dev/api/people/?format=json";

        List<Person> searchedPeople = new ArrayList<>();

        while(url != null)
        {

            restTemplate = new RestTemplate();

            PeopleListResponse peopleListResponse = restTemplate.getForObject(url,
                    PeopleListResponse.class/*, MediaType.APPLICATION_JSON*/);

            List<Person> people = peopleListResponse.getPeople();

            for(Person person : people){
                if(person.getName().indexOf(phrase /*"e"*/) != -1){
                    searchedPeople.add(person);
                }
            }

            url = peopleListResponse.getNext();
        }

        List<Person> peopleWithHomeWorld = new ArrayList<>();

        for(Person person : searchedPeople){

            String homeWorldUrl = person.getHomeWorld();

            HomeWorld homeWorld = restTemplate.getForObject(homeWorldUrl,HomeWorld.class);

            String homeWorldName = homeWorld.getName();
            if( homeWorldName.equals(planet_name /*"Tatooine"*/)){

                peopleWithHomeWorld.add(person);

            }

        }

        return peopleWithHomeWorld;
    }


    private int cutIndexForFilm(String url){

        String s = url.substring(0, url.length() - "/?format=json".length());

        String header = "https://swapi.dev/api/films/";

        s = s.substring(header.length());

        int result;
        try {
            result = Integer.parseInt(s);
        }
        catch (NumberFormatException e)
        {
            result = 0;
        }

        return result;

    }

    private int cutIndexForCharacter(String url){

        String s = url.substring(0, url.length() - 1);

        String header = "http://swapi.dev/api/people/";

        s = s.substring(header.length());

        int result;
        try {
            result = Integer.parseInt(s);
        }
        catch (NumberFormatException e)
        {
            result = 0;
        }

        return result;

    }

    private int cutIndexForPlanet(String url){

        String s = url.substring(0, url.length() - "/?format=json".length());

        String header = "https://swapi.dev/api/planets/";

        s = s.substring(header.length());

        int result;
        try {
            result = Integer.parseInt(s);
        }
        catch (NumberFormatException e)
        {
            result = 0;
        }

        return result;

    }
}

