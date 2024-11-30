package game.servlet.GET;


import game.dal.JobDao;
import game.model.Job;

import javax.servlet.annotation.WebServlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/jobgetall")
public class JobGetAll extends HttpServlet {

    protected JobDao jobDao;

    @Override
    public void init() throws ServletException {
        jobDao = JobDao.getInstance();
    }

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        // Map for storing messages.
        Map<String, String> messages = new HashMap<>();
        req.setAttribute("messages", messages);

        List<Job> jobs = new ArrayList<>();

        // Retrieve all jobs.
        try {
            jobs = jobDao.getAllJobs();
            messages.put("success", "Displaying all available jobs.");
        } catch (SQLException e) {
            e.printStackTrace();
            messages.put("error", "Unable to retrieve jobs. Please try again later.");
        }

        // Save the result in request scope for rendering in JSP.
        req.setAttribute("jobs", jobs);

        req.getRequestDispatcher("/JobGetAll.jsp").forward(req, resp);
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        doGet(req, resp);
    }
}