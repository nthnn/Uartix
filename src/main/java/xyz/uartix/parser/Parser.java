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
import xyz.uartix.ast.expr.*;
import xyz.uartix.ast.stmt.*;
import xyz.uartix.util.Convert;

import java.io.FileNotFoundException;
import java.util.*;

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

    private Token peek() throws ParserException {
        if(this.isAtEnd())
            throw new ParserException("Encountered end-of-file.");

        return this.tokens.get(this.index);
    }

    private boolean isNext(String image) throws ParserException {
        if(this.isAtEnd())
            throw new ParserException("Encountered end-of-file.");

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
            expr = new LiteralExpression(this.consume("true"), true);
        else if(this.isNext("false"))
            expr = new LiteralExpression(this.consume("false"), false);
        else if(this.isNext("maybe"))
            expr = new LiteralExpression(
                this.consume("maybe"),
                new Random().nextBoolean()
            );
        else if(this.isNext("nil"))
            expr = new LiteralExpression(this.consume("nil"), null);
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

    private Expression exprRender() throws ParserException {
        return new RenderExpression(
            this.consume("render"),
            this.expression()
        );
    }

    private Expression exprCatch() throws ParserException {
        Token address = this.consume("catch");
        Expression catchExpr = this.expression();
        this.consume("handle");

        Token handle = this.consume(TokenType.IDENTIFIER);
        Expression handleExpr = this.expression();

        BlockExpression finalBlock = null;
        if(this.isNext("then")) {
            this.consume("then");
            finalBlock = (BlockExpression) this.expression();
        }

        return new CatchHandleExpression(
            address,
            catchExpr,
            handle,
            handleExpr,
            finalBlock
        );
    }

    private Expression exprDo() throws ParserException {
        Token address = this.consume("do");
        Expression body = this.expression();

        this.consume("while");
        this.consume("(");

        Expression condition = this.expression();
        this.consume(")");

        return new DoWhileExpression(
            address,
            condition,
            body
        );
    }

    private Expression exprWhile() throws ParserException {
        Token address = this.consume("while");
        this.consume("(");

        Expression condition = this.expression();
        this.consume(")");

        return new WhileExpression(
            address,
            condition,
            this.expression()
        );
    }

    private Expression exprIf() throws ParserException {
        Token address = this.consume("if");
        this.consume("(");

        Expression condition = this.expression();
        this.consume(")");

        Expression then = this.expression(), otherwise = null;
        if(this.isNext("else")) {
            this.consume("else");
            otherwise = this.expression();
        }

        return new IfExpression(
            address,
            condition,
            then,
            otherwise
        );
    }

    private Expression exprRandom() throws ParserException {
        Token address = this.consume("random");
        Expression then = this.expression(), otherwise = null;

        if(this.isNext("else")) {
            this.consume("else");
            otherwise = this.expression();
        }

        return new RandomExpression(
            address,
            then,
            otherwise
        );
    }

    private Expression exprLoop() throws ParserException {
        Token address = this.consume("loop");
        this.consume("(");

        Expression initial = this.expression();
        this.consume(";");

        Expression condition = this.expression();
        this.consume(";");

        Expression postExpr = this.expression();
        this.consume(")");

        return new LoopExpression(
            address,
            initial,
            condition,
            postExpr,
            this.expression()
        );
    }

    private Expression exprUnless() throws ParserException {
        Token address = this.consume("unless");
        this.consume("(");

        Expression condition = this.expression();
        this.consume(")");

        Expression then = this.expression(), otherwise = null;
        if(this.isNext("else")) {
            this.consume("else");
            otherwise = this.expression();
        }

        return new UnlessExpression(
                address,
                condition,
                then,
                otherwise
        );
    }

    private Expression exprWhen() throws ParserException {
        Token address = this.consume("when");
        this.consume("(");

        Expression expr = this.expression();
        this.consume(")");
        this.consume("{");

        List<AbstractMap.SimpleEntry<Expression, Expression>> cases = new ArrayList<>();
        Expression defaultCase = null;

        while(!this.isNext("}")) {
            if(!cases.isEmpty())
                this.consume(",");

            if(this.isNext("if")) {
                this.consume("if");
                this.consume("(");

                Expression value = this.expression();
                this.consume(")");

                cases.add(new AbstractMap.SimpleEntry<>(value, this.expression()));
            }
            else if(this.isNext("else")) {
                if(defaultCase != null)
                    throw new ParserException("Cannot have more than one (1) else for when expression.");

                defaultCase = this.expression();
            }
        }

        this.consume("}");
        return new WhenExpression(
            address,
            expr,
            cases,
            defaultCase
        );
    }

    private Expression exprFunc() throws ParserException {
        Token address = this.consume("func");
        this.consume("(");

        List<Token> parameters = new ArrayList<>();
        while(!this.isNext(")")) {
            if(!parameters.isEmpty())
                this.consume(",");

            parameters.add(this.consume(TokenType.IDENTIFIER));
        }

        this.consume(")");
        return new FunctionDeclarationExpression(
            address,
            parameters,
            this.expression()
        );
    }

    private Expression exprBlock() throws ParserException {
        Token address = this.consume("{");
        List<Statement> statements = new ArrayList<>();

        while(!this.isNext("}"))
            statements.add(this.statement());
        this.consume("}");

        return new BlockExpression(
            address,
            statements
        );
    }

    private Expression exprLogic() throws ParserException {
        Expression expr = this.exprNullCoalescing();

        while(this.isNext("&&") || this.isNext("||"))
            expr = new BinaryExpression(
                expr,
                this.consume(TokenType.OPERATOR),
                this.expression()
            );

        return expr;
    }

    private Expression exprNullCoalescing() throws ParserException {
        Expression expr = this.exprEquality();

        while(this.isNext("?"))
            expr = new NulllCoalescingExpression(
                this.consume(TokenType.OPERATOR),
                expr,
                this.expression()
            );

        return expr;
    }

    private Expression exprEquality() throws ParserException {
        Expression expr = this.exprComparison();

        while(this.isNext("==") ||
            this.isNext("!=") ||
            this.isNext("=")
        )
            expr = new BinaryExpression(
                expr,
                this.consume(TokenType.OPERATOR),
                this.expression()
            );

        return expr;
    }

    private Expression exprComparison() throws ParserException {
        Expression expr = this.exprTerm();

        while(this.isNext("<") ||
            this.isNext("<=") ||
            this.isNext(">") ||
            this.isNext(">=")
        )
            expr = new BinaryExpression(
                expr,
                this.consume(TokenType.OPERATOR),
                this.expression()
            );

        return expr;
    }

    private Expression exprTerm() throws ParserException {
        Expression expr = this.exprFactor();

        while(this.isNext("+") || this.isNext("-"))
            expr = new BinaryExpression(
                expr,
                this.consume(TokenType.OPERATOR),
                this.expression()
            );

        return expr;
    }

    private Expression exprFactor() throws ParserException {
        Expression expr = this.exprPrimary();

        while(this.isNext("*") ||
            this.isNext("/") ||
            this.isNext("%"))
            expr = new BinaryExpression(
                expr,
                this.consume(TokenType.OPERATOR),
                this.expression()
            );

        return expr;
    }

    private Expression exprPrimary() throws ParserException {
        Expression expr = null;

        if(this.isNext("+") || this.isNext("-") || this.isNext("~"))
            expr = new UnaryExpression(
                this.consume(TokenType.OPERATOR),
                this.expression()
            );
        else if(this.isNext("(")) {
            Token address = this.consume("(");
            expr = new GroupedExpression(
                    address,
                    this.expression()
            );

            this.consume(")");
        }
        else if(this.peek().getType() == TokenType.IDENTIFIER)
            expr = new IdentifierExpression(this.consume(TokenType.IDENTIFIER));
        else if(this.isNext("type"))
            expr = this.exprType();
        else if(this.isNext("{"))
            expr = this.exprBlock();
        else if(this.isNext("render"))
            expr = this.exprRender();
        else if(this.isNext("catch"))
            expr = this.exprCatch();
        else if(this.isNext("do"))
            expr = this.exprDo();
        else if(this.isNext("while"))
            expr = this.exprWhile();
        else if(this.isNext("if"))
            expr = this.exprIf();
        else if(this.isNext("random"))
            expr = this.exprRandom();
        else if(this.isNext("loop"))
            expr = this.exprLoop();
        else if(this.isNext("unless"))
            expr = this.exprUnless();
        else if(this.isNext("when"))
            expr = this.exprWhen();
        else if(this.isNext("func"))
            expr = this.exprFunc();
        else expr = this.exprLiteral();

        return expr;
    }

    private Expression expression() throws ParserException {
        Expression expr = this.exprLogic();

        while(this.isNext("(")) {
            Token address = this.consume("(");

            List<Expression> args = new ArrayList<>();
            while(!this.isNext(")")) {
                if(!args.isEmpty())
                    this.consume(",");

                args.add(this.expression());
            }

            this.consume(")");
            expr = new FunctionCallExpression(
                address,
                expr,
                args
            );
        }

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
