package com.github.cannor147.itmo.software.lab06;

import com.github.cannor147.itmo.software.lab06.visitor.EvaluatorVisitor;
import com.github.cannor147.itmo.software.lab06.visitor.ParserVisitor;
import com.github.cannor147.itmo.software.lab06.visitor.PrinterVisitor;

public class Application {
    public static void main(String[] args) {
        final String expression = "(23 + 10) * 5 – 3 * (32 + 5) * (10 – 4 * 5) + 8 / 2";
        final ParserVisitor parser = new ParserVisitor(expression);
        final PrinterVisitor printer = new PrinterVisitor(parser, System.out);
        final EvaluatorVisitor evaluator = new EvaluatorVisitor(parser);

        printer.print();
        System.out.println(evaluator.evaluate());
    }
}
