package com.github.cannor147.itmo.tt.lab;

import com.github.cannor147.itmo.tt.lab.expression.Expression;
import com.github.cannor147.itmo.tt.lab.parser.Parser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Task1 {
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        StringBuilder expr = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            expr.append(" ").append(line);
        }

        Parser parser = new Parser();
        Expression expression = parser.parse(expr.toString());
        System.out.println(expression);
    }
}
