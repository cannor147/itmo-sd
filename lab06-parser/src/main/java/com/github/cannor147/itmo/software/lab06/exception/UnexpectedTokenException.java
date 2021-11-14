package com.github.cannor147.itmo.software.lab06.exception;

import com.github.cannor147.itmo.software.lab06.tokenizer.Token;

public class UnexpectedTokenException extends IllegalStateException {
    public UnexpectedTokenException(Token token) {
        super("Unexpected token: " + token + ".");
    }
}
