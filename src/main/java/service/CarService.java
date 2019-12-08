package service;

import DAO.CarDao;
import DAO.TemporaryReportDao;
import model.Car;
import model.TemporaryReport;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import util.DBHelper;

import java.util.List;

public class CarService {

    private static CarService carService;

    private SessionFactory sessionFactory;

    private CarService(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public static CarService getInstance() {
        if (carService == null) {
            carService = new CarService(DBHelper.getSessionFactory());
        }
        return carService;
    }

    public List<Car> getAllCars() {
        return new CarDao(sessionFactory.openSession()).getAllCars();
    }

    public boolean buyCar(String brand, String model, String licensePlate) {
        CarDao carDao = new CarDao(sessionFactory.openSession());
        Car car = carDao.getCar(brand, model, licensePlate);
        if (car==null) {
            return false;
        }
        carDao = new CarDao(sessionFactory.openSession());
        carDao.deleteCar(car);
        TemporaryReportDao temporaryReportDao= new TemporaryReportDao(sessionFactory.openSession());
        temporaryReportDao.updateReport(car);
        return true;
    }

    public boolean addCar(String brand, String model, String licensePlate, Long price) {
        CarDao carDao = new CarDao(sessionFactory.openSession());
        int i = carDao.checkCarmodelAmount(model);
        if ( i >=10) {
            return false;
        }
        carDao = new CarDao(sessionFactory.openSession());
        carDao.addCar(new Car(brand, model, licensePlate, price));
        return true;
    }

    public void deleteAllCars() {
        new CarDao(sessionFactory.openSession()).deleteAllCars();
    }
}
