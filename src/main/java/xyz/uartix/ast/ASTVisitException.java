package xyz.uartix.ast;

import xyz.uartix.parser.Token;

public class ASTVisitException extends Exception {
    public ASTVisitException(Node node, String message) {
        super(message + " " + node.getAddress());
    }

    public ASTVisitException(Token token, String message) {
        super(message + " " + token);
    }
}
