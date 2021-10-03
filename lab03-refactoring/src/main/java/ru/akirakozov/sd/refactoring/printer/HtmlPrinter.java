package ru.akirakozov.sd.refactoring.printer;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class HtmlPrinter {
    protected final HttpServletResponse response;

    public HtmlPrinter(HttpServletResponse response) {
        this.response = response;
    }

    public void printText(String text) {
        try {
            response.getWriter().println(text);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void printPage(String content) {
        printText(String.format("<html><body>%s</body></html>", content));
    }
}
