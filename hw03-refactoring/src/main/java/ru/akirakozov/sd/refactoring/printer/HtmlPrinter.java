package ru.akirakozov.sd.refactoring.printer;

import ru.akirakozov.sd.refactoring.model.Product;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class HtmlPrinter {
    private void printSafely(HttpServletResponse response, String text) {
        try {
            response.getWriter().println(text);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @SuppressWarnings("OptionalUsedAsFieldOrParameterType")
    public void printProduct(HttpServletResponse response, String title, Optional<Product> product) {
        printPage(response, String.format("<h1>%s: </h1>%s", title, product.map(x -> x + "</br>").orElse("")));
    }

    public void printProducts(HttpServletResponse response, List<Product> product) {
        printPage(response, product.stream().map(x -> x.toString() + "</br>").collect(Collectors.joining()));
    }

    public void printPage(HttpServletResponse response, String content) {
        printSafely(response, String.format("<html><body>%s</body></html>", content));
    }
}
