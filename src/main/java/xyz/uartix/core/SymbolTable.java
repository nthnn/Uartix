package xyz.uartix.core;

import java.util.HashMap;

public class SymbolTable {
    private final SymbolTable parent;
    private final HashMap<String, Object> table;

    public SymbolTable(SymbolTable parent) {
        this.parent = parent;
        this.table = new HashMap<>();
    }

    public boolean has(String name) {
        if(this.parent != null)
            return this.parent.has(name);

        return this.table.containsKey(name);
    }

    public Object get(String name) {
        if(this.parent != null)
            return this.parent.get(name);

        return this.table.get(name);
    }

    public void set(String name, Object value) {
        if(this.parent != null && this.parent.has(name)) {
            this.parent.set(name, value);
            return;
        }

        this.table.put(name, value);
    }
}
