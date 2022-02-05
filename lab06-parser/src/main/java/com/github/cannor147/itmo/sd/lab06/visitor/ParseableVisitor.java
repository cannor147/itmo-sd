package com.github.cannor147.itmo.sd.lab06.visitor;

import com.github.cannor147.itmo.sd.lab06.parser.Parser;
import com.github.cannor147.itmo.sd.lab06.tokenizer.Token;
import com.github.cannor147.itmo.sd.lab06.tokenizer.Tokenizer;

import java.util.Iterator;
import java.util.function.Supplier;

public abstract class ParseableVisitor extends TokenVisitor {
    private final Supplier<Iterator<Token>> tokenIterator;

    public ParseableVisitor(Parser parser) {
        this.tokenIterator = parser.parse()::iterator;
    }

    public ParseableVisitor(CharSequence charSequence) {
        this.tokenIterator = () -> new Tokenizer(charSequence).iterator();
    }

    public Iterator<Token> getIterator() {
        return tokenIterator.get();
    }
}
