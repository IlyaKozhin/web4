package DAO;

import javafx.util.Pair;
import model.Car;
import model.TemporaryReport;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;

public class TemporaryReportDao {
    private Session session;

    public TemporaryReportDao(Session session) {
        this.session = session;
    }

    public void updateReport(Car car) {
        Transaction transaction = session.beginTransaction();
        Criteria criteria = session.createCriteria(TemporaryReport.class);;
        TemporaryReport temporaryReport = (TemporaryReport) criteria.uniqueResult();
        if (temporaryReport==null) {
            session.save(new TemporaryReport(car.getPrice(),1L));
        } else {
            temporaryReport.setSoldCars(temporaryReport.getSoldCars() + 1);
            temporaryReport.setEarnings(temporaryReport.getEarnings() + car.getPrice());
            session.update(temporaryReport);
        }
        transaction.commit();
        session.close();
    }

    public Pair<Long, Long> getTemporaryReport() {
        Transaction transaction = session.beginTransaction();
        Criteria criteria = session.createCriteria(TemporaryReport.class);;
        TemporaryReport temporaryReport = (TemporaryReport) criteria.uniqueResult();
        if (temporaryReport==null) {
            return new Pair<>(0L,0L);
        }
        Pair<Long,Long> pair = new Pair<>(temporaryReport.getEarnings(),temporaryReport.getSoldCars());
        session.delete(temporaryReport);
        transaction.commit();
        session.close();
        return pair;
    }
    public void deleteAllTemporaryReports() {
        Transaction transaction = session.beginTransaction();
        session.createQuery("DELETE FROM TemporaryReport").executeUpdate();
        transaction.commit();
        session.close();
    }
}
