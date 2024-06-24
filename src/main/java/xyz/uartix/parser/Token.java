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

package xyz.uartix.parser;

public final class Token {
    private final String image, fileName;
    private final int line, column;
    private final TokenType type;

    public Token(String image, String fileName, int line, int column, TokenType type) {
        this.image = image;
        this.fileName = fileName;
        this.line = line;
        this.column = column;
        this.type = type;
    }

    public String getImage() {
        return this.image;
    }

    public String getFileName() {
        return this.fileName;
    }

    public int getLine() {
        return this.line;
    }

    public int getColumn() {
        return this.column;
    }

    public TokenType getType() {
        return this.type;
    }

    @Override
    public String toString() {
        return "\"" + this.getImage() +
            "\" (line " + this.getLine() +
            ", column " + this.getColumn() +
            ") from " + this.getFileName();
    }
}
