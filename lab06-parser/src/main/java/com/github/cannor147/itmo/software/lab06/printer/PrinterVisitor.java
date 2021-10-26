package com.github.cannor147.itmo.software.lab06.printer;

import com.github.cannor147.itmo.software.lab06.exception.ParsingException;
import com.github.cannor147.itmo.software.lab06.parser.ParserVisitor;
import com.github.cannor147.itmo.software.lab06.tokenizer.Token;
import com.github.cannor147.itmo.software.lab06.visitor.Operator;
import com.github.cannor147.itmo.software.lab06.visitor.ParseableVisitor;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;

public class PrinterVisitor extends ParseableVisitor {
    private final OutputStream outputStream;

    public PrinterVisitor(ParserVisitor parserVisitor, OutputStream outputStream) {
        super(parserVisitor);
        this.outputStream = outputStream;
    }

    public PrinterVisitor(CharSequence charSequence, OutputStream outputStream) {
        super(charSequence);
        this.outputStream = outputStream;
    }

    public void print() {
        final Iterator<Token> tokenIterator = getIterator();
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
            outputStream.write(text.getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            throw new ParsingException();
        }
    }
}
