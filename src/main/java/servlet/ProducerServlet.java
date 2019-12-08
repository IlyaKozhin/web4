package servlet;

import service.CarService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ProducerServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=utf-8");
        String brand = req.getParameter("brand");
        String model = req.getParameter("model");
        String licensePlate = req.getParameter("licensePlate");
        if (brand == null || model == null || licensePlate == null) {
            resp.setStatus(403);
            return;
        }
        long price;
        try {
            price = Integer.parseInt(req.getParameter("price"));
        }
        catch (NumberFormatException e)
        {
            resp.setStatus(403);
            return;
        }
        resp.setContentType("text/html;charset=utf-8");
        if (CarService.getInstance().addCar(brand,model,licensePlate,price)) {
            resp.setStatus(200);
        } else {
            resp.setStatus(403);
        }
    }
}
