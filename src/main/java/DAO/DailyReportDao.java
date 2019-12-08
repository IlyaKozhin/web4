package DAO;

import javafx.util.Pair;
import model.DailyReport;
import model.TemporaryReport;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import java.util.List;

public class DailyReportDao {

    private Session session;

    public DailyReportDao(Session session) {
        this.session = session;
    }

    public List<DailyReport> getAllDailyReport() {
        Transaction transaction = session.beginTransaction();
        List<DailyReport> dailyReports = session.createQuery("FROM DailyReport").list();
        transaction.commit();
        session.close();
        return dailyReports;
    }

    public DailyReport getLastReport() {
        Transaction transaction = session.beginTransaction();
        Criteria criteria = session.createCriteria(DailyReport.class);
        DailyReport dailyReport = (DailyReport) criteria.addOrder(Order.desc("id"))
                .setMaxResults(1)
                .uniqueResult();
        transaction.commit();
        session.close();
        return dailyReport;
    }

    public void deleteAllDailyReports() {
        Transaction transaction = session.beginTransaction();
        session.createQuery("DELETE FROM DailyReport").executeUpdate();
        transaction.commit();
        session.close();
    }

    public void addDailyReport(Pair<Long, Long> pair) {
        Transaction transaction = session.beginTransaction();
        session.save(new DailyReport(pair.getKey(),pair.getValue()));
        transaction.commit();
        session.close();
    }
}
