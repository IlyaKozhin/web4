package DAO;

import model.Car;
import model.DailyReport;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import java.util.List;

public class CarDao {

    private Session session;

    public CarDao(Session session) {
        this.session = session;
    }
    public List<Car> getAllCars() {
        Transaction transaction = session.beginTransaction();
        List<Car> cars = session.createQuery("FROM Car").list();
        transaction.commit();
        session.close();
        return cars;
    }
    public Car getCar(String brand, String model, String licensePlate) {
        Transaction transaction = session.beginTransaction();
        Criteria criteria = session.createCriteria(Car.class);
        Car car = (Car) criteria
                .add(Restrictions.eq("brand",brand))
                .add(Restrictions.eq("model",model))
                .add(Restrictions.eq("licensePlate",licensePlate))
                .setMaxResults(1)
                .uniqueResult();
        transaction.commit();
        session.close();
        return car;
    }

    public void deleteCar(Car car) {
        Transaction transaction = session.beginTransaction();
        session.delete(car);
        transaction.commit();
        session.close();
    }

    public void addCar(Car car) {
        Transaction transaction = session.beginTransaction();
        session.save(car);
        transaction.commit();
        session.close();
    }

    public void deleteAllCars() {
        Transaction transaction = session.beginTransaction();
        session.createQuery("DELETE FROM Car").executeUpdate();
        transaction.commit();
        session.close();
    }

    public int checkCarmodelAmount(String model) {
        Transaction transaction = session.beginTransaction();
        Criteria criteria = session.createCriteria(Car.class);
        List<Car> cars = (List<Car>) criteria
                .add(Restrictions.eq("model",model))
                .list();
        transaction.commit();
        session.close();
        return cars.size();
    }
}
