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

import java.util.Arrays;
import java.util.List;

public final class OpsKeys {
    public static List<String> operators = Arrays.asList(
        "+", "-", "*", "/",
        "!", "!=", "&", "&&",
        "|", "||", "^", "%",
        "(", ")", "[", "]",
        "{", "}", "=", "==",
        ":", ";", "'", "\"",
        "<", "<<", "<=", ">",
        ">>", ">=", ",", ".",
        "::", "?"
    );

    public static List<String> keywords = Arrays.asList(
        "while", "do", "loop", "render",
        "unless", "if", "else", "when",
        "break", "continue", "random",
        "func", "ret", "true", "false",
        "nil", "catch", "handle", "throw",
        "maybe"
    );
}
