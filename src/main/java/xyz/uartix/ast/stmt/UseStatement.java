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

package xyz.uartix.ast.stmt;

import xyz.uartix.ast.ASTVisitException;
import xyz.uartix.ast.Expression;
import xyz.uartix.ast.Statement;
import xyz.uartix.core.Runtime;
import xyz.uartix.core.SymbolTable;
import xyz.uartix.core.TerminativeSignal;
import xyz.uartix.parser.LexicalAnalysisException;
import xyz.uartix.parser.Parser;
import xyz.uartix.parser.ParserException;
import xyz.uartix.parser.Token;
import xyz.uartix.util.MiscUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class UseStatement implements Statement {
    private final Token address;
    private final List<Expression> fileNames;

    public UseStatement(Token address, List<Expression> fileNames) {
        this.address = address;
        this.fileNames = fileNames;
    }

    public Token getAddress() {
        return this.address;
    }

    public Object visit(SymbolTable symtab) throws TerminativeSignal {
        try {
            List<Statement> statements = new ArrayList<>();
            for(Expression expr : this.fileNames) {
                Object exprVal = expr.visit(symtab);

                String fileName = exprVal.toString();
                if(!MiscUtil.isValidFilePath(fileName))
                    throw new IOException("Not a valid file name: " + fileName);

                if(Runtime.hasBeenIncluded(fileName))
                    continue;

                Parser parser = Parser.fromFile(fileName);
                parser.parse();

                Runtime.pushToDependencies(fileName);
                statements.addAll(parser.getGlobalStatements());
            }

            for(Statement statement : statements)
                statement.visit(symtab);
        } catch(Exception ex) {
            String type = "";
            switch(ex) {
                case IOException ioException -> type = "I/O ";
                case LexicalAnalysisException lexicalAnalysisException -> type = "Lexical ";
                case ParserException parserException -> type = "Parser ";
                case ASTVisitException astVisitException -> type = "Execution ";
                default -> {
                }
            }

            System.out.println("\u001b[31m" + type + "Error\u001b[0m: " + ex.getMessage());
        }

        return null;
    }
}
