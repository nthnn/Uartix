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
        "nil", "catch", "handle", "throw"
    );
}
