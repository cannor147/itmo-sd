package com.github.cannor147.itmo.sd.lab06.evaluator;

import com.github.cannor147.itmo.sd.lab06.tokenizer.Token;
import com.github.cannor147.itmo.sd.lab06.visitor.Operator;
import com.github.cannor147.itmo.sd.lab06.visitor.ParseableVisitor;
import com.github.cannor147.itmo.sd.lab06.exception.EvaluationException;
import com.github.cannor147.itmo.sd.lab06.parser.ParserVisitor;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Iterator;

public class EvaluatorVisitor extends ParseableVisitor {
    private final Deque<Long> result;

    public EvaluatorVisitor(ParserVisitor parserVisitor) {
        super(parserVisitor);
        this.result = new ArrayDeque<>();
    }

    @SuppressWarnings("unused")
    public EvaluatorVisitor(CharSequence charSequence) {
        super(charSequence);
        this.result = new ArrayDeque<>();
    }

    public Long evaluate() {
        final Iterator<Token> iterator = getIterator();
        while (iterator.hasNext()) {
            visit(iterator.next());
        }

        if (result.size() != 1) {
            throw new EvaluationException("No expression provided.");
        }
        return result.pop();
    }

    @Override
    protected void visitNumber(Number number) {
        result.push(number.longValue());
    }

    @Override
    protected void visitOperator(Operator operator) {
        if (result.size() < 2) {
            throw new EvaluationException("Unexpected operator " + operator + ".");
        }
        final long right = result.pop();
        final long left = result.pop();
        final long value = switch (operator) {
            case ADDITION -> left + right;
            case SUBTRACTION -> left - right;
            case MULTIPLICATION -> left * right;
            case DIVISION -> {
                if (right == 0) {
                    throw new EvaluationException("Division by zero.");
                }
                yield left / right;
            }
            case MODULO -> {
                if (right == 0) {
                    throw new EvaluationException("Division by zero.");
                }
                yield left % right;
            }
        };
        result.push(value);
    }

    @Override
    protected void visitBracket(boolean opening) {
        // No operations.
    }
}
