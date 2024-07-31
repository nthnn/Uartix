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
import xyz.uartix.core.TerminativeThrow;
import xyz.uartix.parser.Token;

import java.io.IOException;

public class CatchHandleExpression implements Expression {
    private final Token address, handle;
    private final Expression catchBlock, handleBlock, finalStmt;

    public CatchHandleExpression(Token address, Expression catchBlock, Token handle, Expression handleBlock, Expression finalStmt) {
        this.address = address;
        this.catchBlock = catchBlock;
        this.handle = handle;
        this.handleBlock = handleBlock;
        this.finalStmt = finalStmt;
    }

    public Token getAddress() {
        return this.address;
    }

    public Object visit(SymbolTable symtab) throws ASTVisitException, IOException, TerminativeSignal {
        Object value = null;
        try {
            value = this.catchBlock.visit(symtab);
        } catch(TerminativeThrow thrownObj) {
            String handleName = this.handle.getImage();
            if(symtab.has(handleName))
                throw new ASTVisitException(this.handle, "Handle name already in use.");

            symtab.set(handleName, thrownObj.getObject());
            value = this.handleBlock.visit(symtab);
        }

        if(this.finalStmt != null)
            value = this.finalStmt.visit(symtab);

        return value;
    }
}
