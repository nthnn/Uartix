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

package xyz.uartix.ast.expr;

import xyz.uartix.ast.ASTVisitException;
import xyz.uartix.ast.Expression;
import xyz.uartix.core.SymbolTable;
import xyz.uartix.core.TerminativeSignal;
import xyz.uartix.parser.Token;
import xyz.uartix.uart.UartOperation;
import xyz.uartix.util.MiscUtil;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class BinaryExpression implements Expression {
    private final Token operator;
    private final Expression left, right;

    public BinaryExpression(Expression left, Token operator, Expression right) {
        this.left = left;
        this.right = right;
        this.operator = operator;
    }

    public Token getAddress() {
        return this.operator;
    }

    @SuppressWarnings("unchecked")
    public Object visit(SymbolTable symtab)
        throws ASTVisitException,
            IOException,
            TerminativeSignal {
        ASTVisitException invalidVisit = new ASTVisitException(
            this,
            "Invalid binary expression operation."
        );

        String operator = this.operator.getImage();
        Object rightValue = this.right.visit(symtab),
            leftValue = null;

        if(!(this.left instanceof IdentifierExpression) ||
            !this.operator.getImage().equals("="))
            leftValue = this.left.visit(symtab);

        if(Objects.equals(operator, "+"))
            return switch(leftValue) {
                case Double _ when rightValue instanceof Double ->
                    UartOperation.add((double) leftValue, (double) rightValue);

                case Double _ when rightValue instanceof String ->
                    ((double) leftValue) + rightValue.toString();

                case String _ when rightValue instanceof Double ->
                    leftValue.toString() + ((double) rightValue);

                case String _ when rightValue instanceof String ->
                    leftValue.toString() + rightValue;

                case null, default ->
                    throw invalidVisit;
            };
        else if(Objects.equals(operator, "*"))
            return switch (leftValue) {
                case Double _ when rightValue instanceof Double ->
                    UartOperation.mul((double) leftValue, (double) rightValue);

                case Double _ when rightValue instanceof String ->
                    MiscUtil.multiply(rightValue.toString(), (int) (double) leftValue);

                case String _ when rightValue instanceof Double ->
                    MiscUtil.multiply(leftValue.toString(), (int) (double) rightValue);

                case null, default ->
                    throw invalidVisit;
            };
        else if(Objects.equals(operator, "-")) {
            if(leftValue instanceof Double && rightValue instanceof Double)
                return UartOperation.sub((double) leftValue, (double) rightValue);

            throw invalidVisit;
        }
        else if(Objects.equals(operator, "/")) {
            if(leftValue instanceof Double && rightValue instanceof Double)
                return UartOperation.div((double) leftValue, (double) rightValue);

            throw invalidVisit;
        }
        else if(Objects.equals(operator, "&")) {
            if(leftValue instanceof Double && rightValue instanceof Double)
                return UartOperation.and((double) leftValue, (double) rightValue);

            throw invalidVisit;
        }
        else if(Objects.equals(operator, "|")) {
            if(leftValue instanceof Double && rightValue instanceof Double)
                return UartOperation.or((double) leftValue, (double) rightValue);

            throw invalidVisit;
        }
        else if(Objects.equals(operator, "%")) {
            if(leftValue instanceof Double && rightValue instanceof Double)
                return UartOperation.rem((double) leftValue, (double) rightValue);

            throw invalidVisit;
        }
        else if(Objects.equals(operator, "^")) {
            if(leftValue instanceof Double && rightValue instanceof Double)
                return UartOperation.pow((double) leftValue, (double) rightValue);

            throw invalidVisit;
        }
        else if(Objects.equals(operator, "<")) {
            if(leftValue instanceof Double && rightValue instanceof Double)
                return UartOperation.lt((double) leftValue, (double) rightValue);

            throw invalidVisit;
        }
        else if(Objects.equals(operator, "<=")) {
            if(leftValue instanceof Double && rightValue instanceof Double)
                return UartOperation.le((double) leftValue, (double) rightValue);

            throw invalidVisit;
        }
        else if(Objects.equals(operator, ">")) {
            if(leftValue instanceof Double && rightValue instanceof Double)
                return UartOperation.gt((double) leftValue, (double) rightValue);

            throw invalidVisit;
        }
        else if(Objects.equals(operator, ">=")) {
            if(leftValue instanceof Double && rightValue instanceof Double)
                return UartOperation.ge((double) leftValue, (double) rightValue);

            throw invalidVisit;
        }
        else if(Objects.equals(operator, "<<")) {
            if(leftValue instanceof Double && rightValue instanceof Double)
                return UartOperation.shl((double) leftValue, (double) rightValue);

            throw invalidVisit;
        }
        else if(Objects.equals(operator, ">>")) {
            if(leftValue instanceof Double && rightValue instanceof Double)
                return UartOperation.shr((double) leftValue, (double) rightValue);

            throw invalidVisit;
        }
        else if(Objects.equals(operator, "&&")) {
            if(leftValue instanceof Boolean && rightValue instanceof Boolean)
                return ((boolean) leftValue) && ((boolean) rightValue);

            throw invalidVisit;
        }
        else if(Objects.equals(operator, "||")) {
            if(leftValue instanceof Boolean || rightValue instanceof Boolean)
                return ((boolean) leftValue) || ((boolean) rightValue);

            throw invalidVisit;
        }
        else if(Objects.equals(operator, "!="))
            return leftValue != rightValue;
        else if(Objects.equals(operator, "=="))
            return leftValue == rightValue;
        else if(Objects.equals(operator, "=")) {
            if(!(this.left instanceof IdentifierExpression) &&
                !(this.left instanceof ArrayAccessExpression))
                throw new ASTVisitException(
                    this.left,
                    "Cannot assign value to non-identifier and non-array access expression."
                );

            if(this.left instanceof IdentifierExpression)
                symtab.set(this.left.getAddress().getImage(), rightValue);
            else {
                ArrayAccessExpression access = (ArrayAccessExpression) this.left;
                Object objs = access.getOrigin().visit(symtab),
                    index = access.getIndex().visit(symtab);

                if(!(objs instanceof List<?>))
                    throw new ASTVisitException(this, "Setting value on non-array value.");

                if(!(index instanceof Double))
                    throw new ASTVisitException(this, "Array access index is not a number");

                ((List<Object>) objs).set((int) (double) index, rightValue);
            }
            return rightValue;
        }

        return null;
    }
}
