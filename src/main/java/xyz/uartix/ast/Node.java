package xyz.uartix.ast;

import xyz.uartix.core.SymbolTable;
import xyz.uartix.core.TerminativeSignal;
import xyz.uartix.parser.Token;

import java.io.IOException;

public interface Node {
    Token getAddress();
    Object visit(SymbolTable symtab) throws
        ASTVisitException,
        IOException,
        TerminativeSignal;
}
