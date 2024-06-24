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
