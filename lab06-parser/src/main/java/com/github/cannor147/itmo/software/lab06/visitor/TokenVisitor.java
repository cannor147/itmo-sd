package com.github.cannor147.itmo.software.lab06.visitor;

import com.github.cannor147.itmo.software.lab06.tokenizer.Token;

public abstract class TokenVisitor {
    protected abstract void visitNumber(Number number);
    protected abstract void visitOperator(Operator operator);
    protected abstract void visitBracket(boolean opening);

    protected void visit(Token token) {
        switch (token.type()) {
            case NUMBER -> visitNumber(Long.parseLong(token.value()));
            case PLUS, MINUS, ASTERISK, SLASH, PERCENT -> visitOperator(Operator.fromToken(token));
            case OPENING_BRACKET -> visitBracket(true);
            case CLOSING_BRACKET -> visitBracket(false);
        }
    }
}
