package com.homework.reportapi.dao;

import com.homework.reportapi.entity.Report;
import com.homework.reportapi.entity.Result;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ReportDAOImpl implements ReportDAO{

    private EntityManager entityManager;

    @Autowired
    public ReportDAOImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<Report> getReportList() {

        Session session = entityManager.unwrap(Session.class);

        Query query =
                session.createQuery("from Report", Report.class);

        List<Report> reportList = query.getResultList();

        return reportList;
    }

    @Override
    public Report getReport(int id) {

        Session session = entityManager.unwrap(Session.class);

        Report report = session.get(Report.class, id);

        return report;
    }

    @Override
    public void saveReport(Report report) {

        Session session = entityManager.unwrap(Session.class);

        if(report.getReport_id() != 0) {

            Report databaseReport = session.get(Report.class, report.getReport_id());


            for (Result result : databaseReport.getResultList()){
                session.delete(result);
            }

            session.merge(report);
        }

        else {
            session.merge(report);
        }
        }

    @Override
    public void deleteReportList() {

        Session session = entityManager.unwrap(Session.class);

        Query query =
                session.createQuery("from Report", Report.class);

        List<Report> reportList = query.getResultList();

        for(Report report : reportList){

            deleteReport(report.getReport_id());
        }

    }

    @Override
    public void deleteReport(int id) {

        Session session = entityManager.unwrap(Session.class);

        Report report = session.get(Report.class, id);

        session.delete(report);


    }

}
