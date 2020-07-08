package com.homework.reportapi.service;

import com.homework.reportapi.dao.ReportDAO;
import com.homework.reportapi.entity.Report;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ReportServiceImpl implements ReportService {

    @Autowired
    private ReportDAO reportDAO;

    @Override
    @Transactional
    public List<Report> getReportList() {
        return reportDAO.getReportList();
    }

    @Override
    @Transactional
    public Report getReport(int id) {
        return reportDAO.getReport(id);
    }

    @Override
    @Transactional
    public void saveReport(Report report) {
        reportDAO.saveReport(report);
    }

    @Override
    public void deleteReportList() {
        reportDAO.deleteReportList();
    }

    @Override
    @Transactional
    public void deleteReport(int id) {
        reportDAO.deleteReport(id);
    }
}
