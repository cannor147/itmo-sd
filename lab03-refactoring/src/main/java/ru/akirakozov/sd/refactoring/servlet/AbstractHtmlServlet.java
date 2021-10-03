package ru.akirakozov.sd.refactoring.servlet;

import ru.akirakozov.sd.refactoring.printer.HtmlPrinter;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

public abstract class AbstractHtmlServlet extends HttpServlet {
    @Override
    protected final void doGet(HttpServletRequest request, HttpServletResponse response) {
        try {
            final HtmlPrinter printer = new HtmlPrinter(response);
            this.doGet(request, printer);
            response.setContentType("text/html");
            response.setStatus(HttpServletResponse.SC_OK);
        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    protected abstract void doGet(HttpServletRequest request, HtmlPrinter printer) throws IOException, SQLException;
}
