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

import java.io.IOException;
import java.util.AbstractMap;
import java.util.List;

public class WhenExpression implements Expression {
    private final Token address;
    private final Expression expression;
    private final List<AbstractMap.SimpleEntry<Expression, Expression>> cases;
    private final Expression defaultCase;

    public WhenExpression(Token address, Expression expression, List<AbstractMap.SimpleEntry<Expression, Expression>> cases, Expression defaultCase) {
        this.address = address;
        this.expression = expression;
        this.cases = cases;
        this.defaultCase = defaultCase;
    }

    public Token getAddress() {
        return this.address;
    }

    public Object visit(SymbolTable symtab) throws ASTVisitException, IOException, TerminativeSignal {
        Object expr = this.expression.visit(symtab);

        for(AbstractMap.SimpleEntry<Expression, Expression> caseCell : this.cases)
            if(caseCell.getKey().visit(symtab).equals(expr))
                return caseCell.getValue().visit(symtab);

        if(this.defaultCase != null)
            return this.defaultCase.visit(symtab);

        return null;
    }
}
