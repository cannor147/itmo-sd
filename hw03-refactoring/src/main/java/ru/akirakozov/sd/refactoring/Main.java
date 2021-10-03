package ru.akirakozov.sd.refactoring;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import ru.akirakozov.sd.refactoring.dao.ProductDao;
import ru.akirakozov.sd.refactoring.servlet.AddProductServlet;
import ru.akirakozov.sd.refactoring.servlet.GetProductsServlet;
import ru.akirakozov.sd.refactoring.servlet.QueryServlet;

/**
 * @author akirakozov
 */
public class Main {
    private static ProductDao productDao;

    public static void main(String[] args) throws Exception {
        productDao = new ProductDao();
        productDao.createTable();

        final Server server = new Server(8081);
        server.setHandler(createHandler());
        server.start();
        server.join();
    }

    private static ServletContextHandler createHandler() {
        final ServletContextHandler contextHandler = new ServletContextHandler(ServletContextHandler.SESSIONS);
        contextHandler.setContextPath("/");

        contextHandler.addServlet(new ServletHolder(new AddProductServlet(productDao)), "/add-product");
        contextHandler.addServlet(new ServletHolder(new GetProductsServlet(productDao)), "/get-products");
        contextHandler.addServlet(new ServletHolder(new QueryServlet(productDao)), "/query");

        return contextHandler;
    }
}
