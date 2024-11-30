package game.servlet.GET;

import game.dal.CurrencyDao;
import game.model.Currency;

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

@WebServlet("/currencygetall")
public class CurrencyGetAll extends HttpServlet {

    protected CurrencyDao currencyDao;

    @Override
    public void init() throws ServletException {
        currencyDao = CurrencyDao.getInstance();
    }

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        // Map for storing messages.
        Map<String, String> messages = new HashMap<>();
        req.setAttribute("messages", messages);

        List<Currency> currencies = new ArrayList<>();

        // Retrieve all currencies.
        try {
            currencies = currencyDao.getAllCurrencies();
            messages.put("success", "Displaying all available currencies.");
        } catch (SQLException e) {
            e.printStackTrace();
            messages.put("error", "Unable to retrieve currencies. Please try again later.");
        }

        // Save the result in request scope for rendering in JSP.
        req.setAttribute("currencies", currencies);

        req.getRequestDispatcher("/CurrencyGetAll.jsp").forward(req, resp);
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        doGet(req, resp);
    }
}