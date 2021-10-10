package ru.akirakozov.sd.refactoring.servlet;

import ru.akirakozov.sd.refactoring.dao.ProductDao;
import ru.akirakozov.sd.refactoring.model.Product;
import ru.akirakozov.sd.refactoring.printer.HtmlPrinter;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;

/**
 * @author akirakozov
 */
public class AddProductServlet extends AbstractHtmlServlet {
    private final ProductDao productDao;

    public AddProductServlet(ProductDao productDao) {
        this.productDao = productDao;
    }

    @Override
    protected void doGet(HttpServletRequest request, HtmlPrinter printer) throws SQLException {
        final Product product = new Product();
        product.setName(request.getParameter("name"));
        product.setPrice(Long.parseLong(request.getParameter("price")));

        productDao.insert(product);
        printer.printText("OK");
    }
}
