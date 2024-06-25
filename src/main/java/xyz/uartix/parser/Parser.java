/*
 * This file is part of the Uartix programming language (https://github.com/nthnn/Uartix).
 * Copyright (c) 2024 Nathanne Isip.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, version 3.
 *
 * This program is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */

package xyz.uartix.parser;

import xyz.uartix.ast.Expression;
import xyz.uartix.ast.Statement;
import xyz.uartix.ast.expr.IdentifierExpression;
import xyz.uartix.ast.expr.LiteralExpression;
import xyz.uartix.ast.expr.TypeExpression;
import xyz.uartix.ast.expr.UnaryExpression;
import xyz.uartix.ast.stmt.*;
import xyz.uartix.util.Convert;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public final class Parser {
    private final List<Statement> globalStatements;
    private final List<Token> tokens;
    private final int length;
    private int index = 0;

    public Parser(List<Token> tokens) {
        this.tokens = tokens;
        this.length = this.tokens.size();

        this.globalStatements = new ArrayList<>();
    }

    public static Parser fromFile(String fileName)
        throws LexicalAnalysisException,
            FileNotFoundException {
        Tokenizer tokenizer = Tokenizer.loadFile(fileName);
        tokenizer.scan();

        return new Parser(tokenizer.getTokens());
    }

    private boolean isAtEnd() {
        return this.index == this.length;
    }

    private void advance() {
        this.index++;
    }

    private Token peek() {
        return this.tokens.get(this.index);
    }

    private boolean isNext(String image) {
        return Objects.equals(this.peek().getImage(), image);
    }

    private Token consume(String image) throws ParserException {
        if(this.isAtEnd())
            throw new ParserException(
                "Expecting \"" + image + "\", encountered end-of-code."
            );

        Token peeked = this.peek();
        String peekedImg = peeked.getImage();

        if(!peekedImg.equals(image))
            throw new ParserException(
                "Expecting \"" + image + "\", encountered \"" + peekedImg + "\"."
            );

        this.index++;
        return peeked;
    }

    private Token consume(TokenType type) throws ParserException {
        if(this.isAtEnd())
            throw new ParserException(
                    "Expecting " + type + ", encountered end-of-code."
            );

        Token peeked = this.peek();
        TokenType peekedType = peeked.getType();

        if(peekedType != type)
            throw new ParserException(
                    "Expecting " + type + ", encountered " + peekedType + "."
            );

        this.index++;
        return peeked;
    }

    public List<Statement> getGlobalStatements() {
        return this.globalStatements;
    }

    private Expression exprLiteral() throws ParserException {
        LiteralExpression expr = null;

        if(this.isNext("true"))
            expr = new LiteralExpression(this.consume(TokenType.KEYWORD), true);
        else if(this.isNext("false"))
            expr = new LiteralExpression(this.consume(TokenType.KEYWORD), false);
        else if(this.isNext("maybe"))
            expr = new LiteralExpression(
                this.consume(TokenType.KEYWORD),
                new Random().nextBoolean()
            );
        else if(this.isNext("nil"))
            expr = new LiteralExpression(this.consume(TokenType.KEYWORD), null);
        else if(this.peek().getType() == TokenType.STRING) {
            Token stringToken = this.consume(TokenType.STRING);
            expr = new LiteralExpression(stringToken, stringToken.getImage());
        }
        else if(this.peek().getType() == TokenType.DIGIT) {
            Token digitToken = this.consume(TokenType.DIGIT);
            expr = new LiteralExpression(
                digitToken,
                Convert.translateDigit(digitToken.getImage())
            );
        }

        if(expr == null)
            throw new ParserException("Expecting expression.");

        return expr;
    }

    private Expression exprType() throws ParserException {
        return new TypeExpression(
            this.consume("type"),
            this.expression()
        );
    }

    private Expression expression() throws ParserException {
        Expression expr = null;

        if(this.isNext("+") || this.isNext("-") || this.isNext("~"))
            expr = new UnaryExpression(
                this.consume(TokenType.OPERATOR),
                this.expression()
            );
        else if(this.peek().getType() == TokenType.IDENTIFIER)
            expr = new IdentifierExpression(this.consume(TokenType.IDENTIFIER));
        else if(this.isNext("type"))
            expr = this.exprType();
        else expr = this.exprLiteral();

        return expr;
    }

    private Statement statement() throws ParserException {
        Statement stmt = null;

        if(this.isNext("break")) {
            Token address = this.consume("break");
            stmt = new BreakStatement(address);

            this.consume(";");
        }
        else if(this.isNext("continue")) {
            Token address = this.consume("continue");
            stmt = new ContinueStatement(address);

            this.consume(";");
        }
        else if(this.isNext("ret")) {
            Token address = this.consume("ret");
            stmt = new ReturnStatement(
                address,
                this.expression()
            );

            this.consume(";");
        }
        else if(this.isNext("throw")) {
            Token address = this.consume("throw");
            stmt = new ThrowStatement(
                    address,
                    this.expression()
            );

            this.consume(";");
        }

        if(stmt == null) {
            Expression expr = this.expression();

            stmt = new ExpressionStatement(
                expr.getAddress(),
                expr
            );
            this.consume(";");
        }

        return stmt;
    }

    public void parse() throws ParserException {
        while(!this.isAtEnd())
            this.globalStatements.add(this.statement());
    }
}
