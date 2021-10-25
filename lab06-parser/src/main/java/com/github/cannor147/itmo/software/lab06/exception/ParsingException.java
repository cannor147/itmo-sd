package com.github.cannor147.itmo.software.lab06.exception;

public class ParsingException extends IllegalStateException {
    public ParsingException() {
        super("Unknown parsing exception.");
    }
}
