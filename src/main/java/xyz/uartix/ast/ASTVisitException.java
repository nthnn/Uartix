package xyz.uartix.ast;

public class ASTVisitException extends Exception {
    public ASTVisitException(Node node, String message) {
        super(message + " " + node.getAddress());
    }
}
