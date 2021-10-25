package com.github.cannor147.itmo.software.lab06.visitor;

import com.github.cannor147.itmo.software.lab06.exception.ParsingException;
import com.github.cannor147.itmo.software.lab06.tokenizer.Token;
import com.github.cannor147.itmo.software.lab06.tokenizer.Tokenizer;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.function.Supplier;

public class PrinterVisitor extends TokenVisitor {
    private final Supplier<Iterator<Token>> tokenIterator;
    private final OutputStream out;

    public PrinterVisitor(ParserVisitor parserVisitor, OutputStream out) {
        this.tokenIterator = parserVisitor.parse()::iterator;
        this.out = out;
    }

    public PrinterVisitor(CharSequence charSequence, OutputStream out) {
        this.tokenIterator = new Tokenizer(charSequence)::iterator;
        this.out = out;
    }

    public void print() {
        final Iterator<Token> tokenIterator = this.tokenIterator.get();
        while (tokenIterator.hasNext()) {
            visit(tokenIterator.next());
            write(tokenIterator.hasNext() ? " " : System.lineSeparator());
        }
    }

    @Override
    protected void visitNumber(Number number) {
        write("NUMBER(%s)".formatted(number.toString()));
    }

    @Override
    protected void visitOperator(Operator operator) {
        final String result = switch (operator) {
            case ADDITION -> "ADD";
            case SUBTRACTION -> "SUB";
            case MULTIPLICATION -> "MUL";
            case DIVISION -> "DIV";
            case MODULO -> "MOD";
        };
        write(result);
    }

    @Override
    protected void visitBracket(boolean opening) {
        write(opening ? "LEFT" : "RIGHT");
    }

    private void write(String text) {
        try {
            out.write(text.getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            throw new ParsingException();
        }
    }
}
