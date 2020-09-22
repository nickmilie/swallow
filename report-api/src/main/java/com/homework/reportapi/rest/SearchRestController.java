package com.homework.reportapi.rest;

import com.homework.reportapi.entity.*;
import com.homework.reportapi.methods.SearchLogic;
import com.homework.reportapi.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;



@RestController
@RequestMapping("/api")
public class SearchRestController {

    @Autowired
    private ReportService reportService;

    @GetMapping("/search")
    public List<Film> search(@RequestParam(defaultValue = "Luke", value = "phrase") String phrase,
                             @RequestParam(defaultValue = "Tatooine", value = "planet_name") String planet_name){


        List<Person> peopleWithHomeWorld = SearchLogic.getPeopleWithHomeWorld(phrase, planet_name);

        List<String> filmUrlList = SearchLogic.getFilmUrls(peopleWithHomeWorld);

        List<Film> films = SearchLogic.getFilms(filmUrlList);

        Report report = SearchLogic.composeReport(phrase, planet_name, films, filmUrlList, peopleWithHomeWorld);

        reportService.saveReport(report);

        return films;
    }

}
