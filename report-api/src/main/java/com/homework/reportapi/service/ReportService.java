package com.homework.reportapi.service;

import com.homework.reportapi.entity.Report;

import java.util.List;

public interface ReportService {

    public List<Report> getReportList();

    public Report getReport(int id);

    public void saveReport(Report report);

    public void deleteReportList();

    public void deleteReport(int id);

}
