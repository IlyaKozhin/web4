package servlet;

import com.google.gson.Gson;
import model.DailyReport;
import service.CarService;
import service.DailyReportService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DailyReportServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Gson gson = new Gson();
        String json;
        resp.setContentType("text/html;charset=utf-8");
        if(req.getPathInfo()!=null) {
            if (req.getPathInfo().contains("all")) {
                json = gson.toJson(DailyReportService.getInstance().getAllDailyReports());
            } else if (req.getPathInfo().contains("last")) {
                json = gson.toJson(DailyReportService.getInstance().getLastReport());
            } else {
                resp.setStatus(403);
                return;
            }
            resp.getWriter().write(json);
            resp.setStatus(200);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
         if (req.getPathInfo()==null) {
             DailyReportService dailyReportService = DailyReportService.getInstance();
             dailyReportService.deleteAllDailyReports();
             dailyReportService.deleteAllTemporaryReports();
             CarService.getInstance().deleteAllCars();
             resp.setStatus(200);
        }
    }
}
