package ru.akirakozov.sd.refactoring.servlet;

import ru.akirakozov.sd.refactoring.dao.ProductDao;
import ru.akirakozov.sd.refactoring.printer.HtmlPrinter;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;

/**
 * @author akirakozov
 */
public class QueryServlet extends AbstractHtmlServlet {
    private final ProductDao productDao;

    public QueryServlet(ProductDao productDao) {
        this.productDao = productDao;
    }

    @Override
    protected void doGet(HttpServletRequest request, HtmlPrinter printer) throws SQLException {
        String command = request.getParameter("command");
        switch (command) {
            case "max":
                printer.printProduct("Product with max price", productDao.findMax());
                break;
            case "min":
                printer.printProduct("Product with min price", productDao.findMin());
                break;
            case "sum":
                printer.printPage("Summary price: " + productDao.sumPrices());
                break;
            case "count":
                printer.printPage("Number of products: " + productDao.count());
                break;
            default:
                printer.printText("Unknown command: " + command);
                break;
        }
    }
}
