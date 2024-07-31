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

package xyz.uartix;

import xyz.uartix.ast.ASTVisitException;
import xyz.uartix.ast.Statement;
import xyz.uartix.ast.stmt.TestStatement;
import xyz.uartix.core.Runtime;
import xyz.uartix.core.*;
import xyz.uartix.parser.LexicalAnalysisException;
import xyz.uartix.parser.Parser;
import xyz.uartix.parser.ParserException;
import xyz.uartix.uart.Uart;
import xyz.uartix.util.RuntimeArgumentParser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        try {
            if(Uart.hasNoPortAvailable())
                throw new IOException("No available serial port co-processor.");

            RuntimeArgumentParser argParser = new RuntimeArgumentParser(args);
            if(!argParser.parse())
                System.exit(1);

            SymbolTable symtab = new SymbolTable(null);
            List<Statement> statements = new ArrayList<>();

            for(String file : argParser.getInputFiles()) {
                Parser parser = Parser.fromFile(file);
                parser.parse();

                statements.addAll(parser.getGlobalStatements());
            }

            Uart.connect(argParser.getSelectedPort());
            for(Statement statement : statements) {
                if(Runtime.isTestMode() && !(statement instanceof TestStatement))
                    continue;

                statement.visit(symtab);
            }
        } catch(TerminativeSignal e) {
            if(e instanceof TerminativeBreak || e instanceof TerminativeContinue)
                System.out.println("\u001b[31mTerminative signal received.\u001b[0m");
            else if(e instanceof TerminativeThrow) {
                Object obj = ((TerminativeThrow) e).getObject();
                if(obj == null)
                    obj = "nil";

                System.out.println("\u001b[31mUncaught error\u001b[0m: " + obj);
            }
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

        try {
            Uart.disconnect();
        } catch(IOException e) {
            System.out.println("\u001b[31mI/O Error\u001b[0m: " + e.getMessage());
        }
    }
}
