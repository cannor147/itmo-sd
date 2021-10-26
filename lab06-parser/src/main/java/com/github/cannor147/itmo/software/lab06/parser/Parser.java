package com.github.cannor147.itmo.software.lab06.parser;

import com.github.cannor147.itmo.software.lab06.tokenizer.Token;

import java.util.List;

public interface Parser {
    List<Token> parse();
}
