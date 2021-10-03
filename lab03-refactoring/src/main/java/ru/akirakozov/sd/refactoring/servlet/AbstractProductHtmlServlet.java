package ru.akirakozov.sd.refactoring.servlet;

import ru.akirakozov.sd.refactoring.model.Product;
import ru.akirakozov.sd.refactoring.printer.HtmlPrinter;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public abstract class AbstractProductHtmlServlet extends AbstractHtmlServlet {
    @SuppressWarnings("OptionalUsedAsFieldOrParameterType")
    public void printProduct(HtmlPrinter printer, String title, Optional<Product> product) {
        printer.printPage(String.format("<h1>%s: </h1>%s", title, product.map(x -> x + "</br>").orElse("")));
    }

    public void printProducts(HtmlPrinter printer, List<Product> product) {
        printer.printPage(product.stream().map(x -> x.toString() + "</br>").collect(Collectors.joining()));
    }
}
