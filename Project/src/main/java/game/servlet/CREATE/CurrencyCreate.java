package game.servlet;

import game.dal.CurrencyDao;
import game.model.Currency;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/currencycreate")
public class CurrencyCreate extends HttpServlet {

    private CurrencyDao currencyDao;

    @Override
    public void init() throws ServletException {
        currencyDao = CurrencyDao.getInstance();
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Map<String, String> messages = new HashMap<>();
        request.setAttribute("messages", messages);
        messages.put("title", "Create Currency");
        request.getRequestDispatcher("/CurrencyCreate.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Map<String, String> messages = new HashMap<>();
        request.setAttribute("messages", messages);

        try {
            String currencyName = request.getParameter("currencyName");
            int cap = Integer.parseInt(request.getParameter("cap"));
            boolean isContinued = Boolean.parseBoolean(request.getParameter("isContinued"));

            if (currencyName == null || currencyName.trim().isEmpty()) {
                messages.put("success", "Currency name cannot be empty.");
            } else {
                Currency currency = new Currency(currencyName, cap, isContinued);
                currencyDao.create(currency);
                messages.put("success", "Successfully created currency: " + currencyName);
            }
        } catch (NumberFormatException e) {
            messages.put("success", "Invalid number format. Please check your inputs.");
        } catch (SQLException e) {
            e.printStackTrace();
            messages.put("success", "Database error: Unable to create currency.");
        }

        request.getRequestDispatcher("/CurrencyCreate.jsp").forward(request, response);
    }
}
