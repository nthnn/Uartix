package xyz.uartix.ast;

import xyz.uartix.parser.Token;

import java.io.IOException;

public interface Node {
    Token getAddress();
    Object visit() throws ASTVisitException, IOException;
}
