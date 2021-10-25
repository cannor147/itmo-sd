package com.github.cannor147.itmo.software.lab06;

import com.github.cannor147.itmo.software.lab06.tokenizer.Token;
import com.github.cannor147.itmo.software.lab06.visitor.ParserVisitor;

import java.util.List;

public class Application {
    public static void main(String[] args) {
        final String expression = "(23 + 10) * 5 – 3 * (32 + 5) * ((5))";
        final ParserVisitor parserVisitor = new ParserVisitor(expression);
        final List<Token> tokens = parserVisitor.parse();

        for (Token token : tokens) {
            System.out.println(token);
        }
    }
}
