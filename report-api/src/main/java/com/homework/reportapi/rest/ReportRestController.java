package com.homework.reportapi.rest;

import com.homework.reportapi.entity.*;
import com.homework.reportapi.exception.ReportNotFoundException;
import com.homework.reportapi.methods.SearchLogic;
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

        Report report = SearchLogic.createReport(phrase, planet_name);

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


}

