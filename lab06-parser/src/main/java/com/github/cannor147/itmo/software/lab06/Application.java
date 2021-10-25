package com.github.cannor147.itmo.software.lab06;

import com.github.cannor147.itmo.software.lab06.visitor.ParserVisitor;
import com.github.cannor147.itmo.software.lab06.visitor.PrinterVisitor;

public class Application {
    public static void main(String[] args) {
        final String expression = "(23 + 10) * 5 – 3 * (32 + 5) * ((5))";
        new PrinterVisitor(expression, System.out).print();

        final ParserVisitor parser = new ParserVisitor(expression);
        new PrinterVisitor(parser, System.out).print();

    }
}
