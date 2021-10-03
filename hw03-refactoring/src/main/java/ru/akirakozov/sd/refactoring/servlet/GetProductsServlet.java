package ru.akirakozov.sd.refactoring.servlet;

import ru.akirakozov.sd.refactoring.dao.ProductDao;
import ru.akirakozov.sd.refactoring.printer.HtmlPrinter;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;

/**
 * @author akirakozov
 */
public class GetProductsServlet extends AbstractHtmlServlet {
    private final ProductDao productDao;

    public GetProductsServlet(ProductDao productDao) {
        this.productDao = productDao;
    }

    @Override
    protected void doGet(HttpServletRequest request, HtmlPrinter printer) throws SQLException {
        printer.printProducts(productDao.findAll());
    }
}
