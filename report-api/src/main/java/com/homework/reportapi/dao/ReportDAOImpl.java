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

/*        Report databaseReport = session.createQuery(
                "select r " +
                        "from Report r " +
                        "join fetch r.resultList " +
                        "where r.report_id = :id", Report.class)
                .setParameter("id", report.getReport_id())
                .getSingleResult();*/

//        Report databaseReport = session.get(Report.class, report.getReport_id());

        if(report.getReport_id() != 0) {
/*            Report databaseReport = session.createQuery(
                    "select r " +
                            "from Report r " +
                            "join fetch r.resultList " +
                            "where r.report_id = :id", Report.class)
                    .setParameter("id", report.getReport_id())
                    .getSingleResult();*/

            Report databaseReport = session.get(Report.class, report.getReport_id());

/*
            entityManager.detach(databaseReport);

            databaseReport.getResultList().clear();
*/

            for (Result result : databaseReport.getResultList()){
                session.delete(result);
            }

            session.merge(report);

            /*            for (Result result : report.getResultList()) {
                *//*databaseReport.addResult(result);*//*
                session.save(result);
            }*/

            //session.merge(databaseReport);

        }
     //   entityManager.detach(databaseReport);

/*
        if(report.getReport_id() != 0 ) {

            entityManager.detach(databaseReport);

            databaseReport.getResultList().clear();

            for (Result result : report.getResultList()) {
                databaseReport.addResult(result);
            }
        }
*/
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
//            session.delete(report);

            deleteReport(report.getReport_id());
        }

//        reportList.removeAll(reportList);

    }

    @Override
    public void deleteReport(int id) {

        Session session = entityManager.unwrap(Session.class);

        Report report = session.get(Report.class, id);
/*        org.hibernate.query.Query query =
                session.createQuery("delete from Report where report_id=:id");
        query.setParameter("id", id);

        query.executeUpdate();*/

        session.delete(report);


    }

}
