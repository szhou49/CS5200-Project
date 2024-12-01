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

@WebServlet("/currencydelete")
public class CurrencyDelete extends HttpServlet {

    private CurrencyDao currencyDao;

    @Override
    public void init() throws ServletException {
        currencyDao = CurrencyDao.getInstance();
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Map<String, String> messages = new HashMap<>();
        request.setAttribute("messages", messages);
        messages.put("title", "Delete Currency");
        request.getRequestDispatcher("/CurrencyDelete.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Map<String, String> messages = new HashMap<>();
        request.setAttribute("messages", messages);

        try {
            String currencyName = request.getParameter("currencyName");

            if (currencyName == null || currencyName.trim().isEmpty()) {
                messages.put("success", "Currency name cannot be empty.");
            } else {
                Currency currency = currencyDao.getCurrencyByName(currencyName);
                if (currency == null) {
                    messages.put("success", "Currency with name '" + currencyName + "' does not exist.");
                } else {
                    currencyDao.delete(currency);
                    messages.put("success", "Successfully deleted currency: " + currencyName);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            messages.put("success", "Database error: Unable to delete currency.");
        }

        request.getRequestDispatcher("/CurrencyDelete.jsp").forward(request, response);
    }
}
