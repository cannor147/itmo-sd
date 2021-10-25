package com.github.cannor147.itmo.software.lab06;

import com.github.cannor147.itmo.software.lab06.tokenizer.Token;
import com.github.cannor147.itmo.software.lab06.tokenizer.Tokenizer;

import java.util.ArrayList;
import java.util.List;

public class Application {
    public static void main(String[] args) {
        final String expression = "(23 + 10) * 5 – 3 * (32 + 5) * (10 – 4 * 5) + 8 / 2";
        final Tokenizer tokenizer = new Tokenizer(expression);

        final List<Token> tokens = new ArrayList<>();
        while (!tokenizer.currentToken().isEnd()) {
            final Token token = tokenizer.nextToken();
            if (!token.isEnd()) {
                tokens.add(token);
            }
        }

        for (Token token : tokens) {
            System.out.println(token);
        }
    }
}
