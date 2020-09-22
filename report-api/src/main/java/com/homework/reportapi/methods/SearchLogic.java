package com.homework.reportapi.methods;

import com.homework.reportapi.entity.*;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

public class SearchLogic {

    private static RestTemplate restTemplate;

    private static String startUrl = "https://swapi.dev/api";

    public static Report createReport(String phrase, String planet_name){

        List<Person> peopleWithHomeWorld = getPeopleWithHomeWorld(phrase, planet_name);

        List<String> filmUrlList = getFilmUrls(peopleWithHomeWorld);

        List<Film> films = getFilms(filmUrlList);

        Report report = composeReport(phrase, planet_name, films, filmUrlList, peopleWithHomeWorld);

        return report;

    }

    public static Report composeReport(String phrase, String planet_name,
                                       List<Film> films, List<String> filmUrlList,
                                       List<Person> peopleWithHomeWorld){

        Report report = new Report(phrase,planet_name);

        for(Person person : peopleWithHomeWorld){

            for (String filmUrl : person.getFilms()){

                if(filmUrlList.contains(filmUrl)){

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

    public static List<Film> getFilms(List<String> filmUrlList){

        List<Film> films = new ArrayList<>();

        for(String filmUrl : filmUrlList){
            films.add(restTemplate.getForObject("https" + filmUrl.substring("http".length()),Film.class));
        }

        return films;
    }

    public static List<String> getFilmUrls(List<Person> peopleWithHomeWorld){

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

    public static List<Person> getPeopleWithHomeWorld(String phrase, String planet_name){

        String url = startUrl + "/people/?format=json";

        List<Person> searchedPeople = new ArrayList<>();

        while(url != null)
        {

            restTemplate = new RestTemplate();

            PeopleListResponse peopleListResponse = restTemplate.getForObject(url,
                    PeopleListResponse.class);

            List<Person> people = peopleListResponse.getPeople();

            for(Person person : people){
                if(person.getName().indexOf(phrase) != -1){
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
            if( homeWorldName.equals(planet_name)){

                peopleWithHomeWorld.add(person);

            }

        }

        return peopleWithHomeWorld;
    }

    public static int cutIndexForFilm(String url){

        String s = url.substring(0, url.length() - "/?format=json".length());

        String header = startUrl + "/films/";

        s = s.substring(header.length());

        return StringToInt(s);

    }

    public static int cutIndexForCharacter(String url){

        String s = url.substring(0, url.length() - "/?format=json".length());

        String header = startUrl + "/people/";

        s = s.substring(header.length());

        return StringToInt(s);

    }

    public static int cutIndexForPlanet(String url){

        String s = url.substring(0, url.length() - "/?format=json".length());

        String header = startUrl + "/planets/";

        s = s.substring(header.length());

        return StringToInt(s);

    }

    public static int StringToInt(String s){

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
