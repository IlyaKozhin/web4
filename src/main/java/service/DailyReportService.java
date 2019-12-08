package service;

import DAO.DailyReportDao;
import DAO.TemporaryReportDao;
import javafx.util.Pair;
import model.DailyReport;
import org.hibernate.SessionFactory;
import util.DBHelper;

import java.util.List;

public class DailyReportService {

    private static DailyReportService dailyReportService;

    private SessionFactory sessionFactory;

    private DailyReportService(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public static DailyReportService getInstance() {
        if (dailyReportService == null) {
            dailyReportService = new DailyReportService(DBHelper.getSessionFactory());
        }
        return dailyReportService;
    }

    public List<DailyReport> getAllDailyReports() {
        return new DailyReportDao(sessionFactory.openSession()).getAllDailyReport();
    }

    public DailyReport getLastReport() {
        return  new DailyReportDao(sessionFactory.openSession()).getLastReport();
    }

    public void deleteAllDailyReports() {
        new DailyReportDao(sessionFactory.openSession()).deleteAllDailyReports();
    }
    public void deleteAllTemporaryReports() {
        new TemporaryReportDao(sessionFactory.openSession()).deleteAllTemporaryReports();
    }

    public void newDay() {
        TemporaryReportDao temporaryReportDao= new TemporaryReportDao(sessionFactory.openSession());
        Pair<Long,Long> pair = temporaryReportDao.getTemporaryReport();
        new DailyReportDao(sessionFactory.openSession()).addDailyReport(pair);
    }
}
