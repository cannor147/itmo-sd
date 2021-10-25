package com.github.cannor147.itmo.software.lab06.tokenizer;

import static com.github.cannor147.itmo.software.lab06.tokenizer.TokenType.BEGIN;
import static com.github.cannor147.itmo.software.lab06.tokenizer.TokenType.END;

@SuppressWarnings("unused")
public record Token(TokenType type, String value) {
    private static final Token BEGIN_TOKEN = new Token(BEGIN, "");
    private static final Token END_TOKEN = new Token(END, "");

    public static Token begin() {
        return BEGIN_TOKEN;
    }

    public static Token end() {
        return END_TOKEN;
    }

    public boolean isBegin() {
        return this.type == BEGIN;
    }

    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    public boolean isEnd() {
        return this.type == END;
    }
}
