package xyz.uartix.core;

import xyz.uartix.ast.ASTVisitException;
import xyz.uartix.ast.Statement;
import xyz.uartix.parser.Token;

import java.io.IOException;
import java.util.List;

public class Function {
    private final Token address;
    private final String name;
    private final List<String> parameters;
    private final Statement body;

    public Function(
        Token address,
        String name,
        List<String> parameters,
        Statement body
    ) {
        this.address = address;
        this.name = name;
        this.parameters = parameters;
        this.body = body;
    }

    public Object invoke(SymbolTable table, List<Object> arguments)
        throws TerminativeSignal,
            ASTVisitException,
            IOException {
        SymbolTable symtab = new SymbolTable(table);
        if(this.parameters.size() != arguments.size())
            throw new ASTVisitException(
                this.address,
                "Expecting " + this.parameters.size() +
                ", but got " + arguments.size() + " arguments."
            );

        for(int i = 0; i < this.parameters.size(); i++)
            symtab.set(this.parameters.get(i), arguments.get(i));
        return this.body.visit(symtab);
    }
}
