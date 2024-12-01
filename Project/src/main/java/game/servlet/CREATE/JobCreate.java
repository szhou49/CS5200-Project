package game.servlet;

import game.dal.JobDao;
import game.model.Job;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/jobcreate")
public class JobCreate extends HttpServlet {

    private JobDao jobDao;

    @Override
    public void init() throws ServletException {
        jobDao = JobDao.getInstance();
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Map for storing messages.
        Map<String, String> messages = new HashMap<>();
        request.setAttribute("messages", messages);

        messages.put("title", "Create Job");
        request.getRequestDispatcher("/JobCreate.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Map<String, String> messages = new HashMap<>();
        request.setAttribute("messages", messages);

        try {
            String jobName = request.getParameter("jobName");

            if (jobName == null || jobName.trim().isEmpty()) {
                messages.put("error", "Job name cannot be empty.");
            } else {
                Job existingJob = jobDao.getJobByName(jobName);
                if (existingJob != null) {
                    messages.put("error", "Job with name '" + jobName + "' already exists.");
                } else {
                    Job job = new Job(jobName);
                    jobDao.create(job);
                    messages.put("success", "Successfully created job: " + jobName);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            messages.put("error", "Database error: Unable to create job.");
        }

        request.getRequestDispatcher("/JobCreate.jsp").forward(request, response);
    }
}
