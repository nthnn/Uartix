package xyz.uartix.ast;

import xyz.uartix.parser.Token;

public interface Node {
    Token getAddress();
    Object visit() throws ASTVisitException;
}
