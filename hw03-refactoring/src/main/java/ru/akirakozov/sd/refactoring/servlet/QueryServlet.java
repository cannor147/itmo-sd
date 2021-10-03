package ru.akirakozov.sd.refactoring.servlet;

import ru.akirakozov.sd.refactoring.dao.ProductDao;
import ru.akirakozov.sd.refactoring.printer.HtmlPrinter;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

/**
 * @author akirakozov
 */
public class QueryServlet extends HttpServlet {
    private final ProductDao productDao;
    private final HtmlPrinter htmlPrinter;

    public QueryServlet(ProductDao productDao) {
        this.productDao = productDao;
        this.htmlPrinter = new HtmlPrinter();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String command = request.getParameter("command");

        try {
            switch (command) {
                case "max":
                    htmlPrinter.printProduct(response, "Product with max price", productDao.findMax());
                    break;
                case "min":
                    htmlPrinter.printProduct(response, "Product with min price", productDao.findMin());
                    break;
                case "sum":
                    htmlPrinter.printPage(response, "Summary price: " + productDao.sumPrices());
                    break;
                case "count":
                    htmlPrinter.printPage(response, "Number of products: " + productDao.count());
                    break;
                default:
                    response.getWriter().println("Unknown command: " + command);
                    break;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        response.setContentType("text/html");
        response.setStatus(HttpServletResponse.SC_OK);
    }

}
