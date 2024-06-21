package xyz.uartix.parser;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public final class Tokenizer {
    private final String source, fileName;
    private final List<Token> tokens;
    private final int length;
    private int index = 0;

    public Tokenizer(String source, String fileName) {
        this.source = source;
        this.fileName = fileName;

        this.length = this.source.length();
        this.tokens = new ArrayList<>();
    }

    public static Tokenizer loadFile(String filePath)
        throws FileNotFoundException {
        StringBuilder content = new StringBuilder();
        Scanner scanner = new Scanner(new File(filePath));

        while(scanner.hasNextLine())
            content.append(scanner.nextLine()).append("\n");

        return new Tokenizer(content.toString(), filePath);
    }

    private static boolean isWhitespace(char ch) {
        return switch (ch) {
            case ' ', '\t', '\r', '\n', '\f' -> true;
            default -> false;
        };
    }

    private static boolean isDigit(char ch) {
        return ch >= '0' && ch <= '9';
    }

    private static boolean isBinaryDigit(char ch) {
        return ch == '0' || ch == '1';
    }

    private static boolean isTrinaryDigit(char ch) {
        return ch >= '0' && ch <= '2';
    }

    private static boolean isOctalDecimalDigit(char ch) {
        return ch >= '0' && ch <= '7';
    }

    private static boolean isHexadecimalDigit(char ch) {
        return (ch >= '0' && ch <= '9') ||
            (ch >= 'a' && ch <= 'f') ||
            (ch >= 'A' && ch <= 'F');
    }

    private static boolean isAlphabet(char ch) {
        return (ch >= 'a' && ch <= 'z') ||
            (ch >= 'A' && ch <= 'Z');
    }

    private static boolean isOperator(char ch) {
        return switch(ch) {
            case '!', '~', '`', '#', '%', '^',
                 '&', '*', '(', ')', '-', '=',
                 '+', '[', ']', '{', '}', '|',
                 '"', ':', ';', '<', ',', '>',
                 '.', '?', '/' -> true;
            default -> false;
        };
    }

    private static boolean isKeyword(String image) {
        return OpsKeys.keywords.contains(image);
    }

    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    private boolean isAtEnd() {
        return this.index == this.length;
    }

    public void scan() throws LexicalAnalysisException {
        if(Objects.equals(this.source, ""))
            return;

        int line = 1, column = 0;
        while(!this.isAtEnd()) {
            char currentChar = this.source.charAt(this.index++);
            column++;

            if(Tokenizer.isWhitespace(currentChar)) {
                if(currentChar == '\n') {
                    line++;
                    column = 0;
                    continue;
                }

                column++;
            }
            else if(Tokenizer.isOperator(currentChar)) {
                if(currentChar == '#') {
                    while(!this.isAtEnd() &&
                        this.source.charAt(index) != '\n')
                        index++;

                    column = 0;
                    continue;
                }
                else if(currentChar == '"') {
                    StringBuilder string = new StringBuilder();
                    int currentColumn = column;

                    while(!this.isAtEnd() && this.source.charAt(index) != '"') {
                        if(this.source.charAt(index) == '\n')
                            throw new LexicalAnalysisException("Found new line inside string literal.");

                        string.append(this.source.charAt(index++));
                        column++;

                        if(this.isAtEnd())
                            throw new LexicalAnalysisException("Unterminated string literal on line " + line + ".");
                    }

                    index++;
                    column++;
                    this.tokens.add(new Token(
                        string.toString(),
                        this.fileName,
                        line,
                        currentColumn,
                        TokenType.STRING
                    ));
                    continue;
                }

                StringBuilder operator = new StringBuilder();
                operator.append(currentChar);

                int currentColumn = column;
                while(!this.isAtEnd() &&
                    OpsKeys.operators.contains(
                            operator.toString() +
                            this.source.charAt(this.index)
                    )) {
                    operator.append(this.source.charAt(this.index));

                    this.index++;
                    column++;
                }

                this.tokens.add(new Token(
                    operator.toString(),
                    this.fileName,
                    line,
                    currentColumn,
                    TokenType.OPERATOR
                ));
            }
            else if(Tokenizer.isDigit(currentChar)) {
                int currentColumn = column;

                StringBuilder digit = new StringBuilder();
                digit.append(currentChar);

                if(currentChar == '0') {
                    switch(this.source.charAt(index)) {
                        case 'b':
                            digit.append('b');
                            index++;
                            column++;

                            while(!this.isAtEnd() &&
                                    Tokenizer.isBinaryDigit(this.source.charAt(index))) {
                                digit.append(this.source.charAt(index));

                                index++;
                                column++;
                            }
                            break;

                        case 't':
                            digit.append('t');
                            index++;
                            column++;

                            while(!this.isAtEnd() &&
                                Tokenizer.isTrinaryDigit(this.source.charAt(index))) {
                                digit.append(this.source.charAt(index));

                                index++;
                                column++;
                            }
                            break;

                        case 'c':
                            digit.append('c');
                            index++;
                            column++;

                            while(!this.isAtEnd() &&
                                    Tokenizer.isOctalDecimalDigit(this.source.charAt(index))) {
                                digit.append(this.source.charAt(index));

                                index++;
                                column++;
                            }
                            break;

                        case 'x':
                            digit.append('x');
                            index++;
                            column++;

                            while(!this.isAtEnd() &&
                                    Tokenizer.isHexadecimalDigit(this.source.charAt(index))) {
                                digit.append(this.source.charAt(index));

                                index++;
                                column++;
                            }
                            break;

                        default:
                            while(!this.isAtEnd() &&
                                    Tokenizer.isDigit(this.source.charAt(index))) {
                                digit.append(this.source.charAt(index));
                                index++;
                                column++;
                            }
                            break;
                    }
                }
                else while(!this.isAtEnd() &&
                    Tokenizer.isDigit(this.source.charAt(index))) {
                    digit.append(this.source.charAt(index));
                    index++;
                    column++;
                }

                this.tokens.add(new Token(
                        digit.toString(),
                        this.fileName,
                        line,
                        currentColumn,
                        TokenType.DIGIT
                ));
            }
            else if(Tokenizer.isAlphabet(currentChar)) {
                StringBuilder token = new StringBuilder();
                token.append(currentChar);

                int currentColumn = column;
                while(!this.isAtEnd() &&
                    Tokenizer.isAlphabet(this.source.charAt(this.index))
                ) {
                    token.append(this.source.charAt(this.index));

                    this.index++;
                    column++;
                }

                String image = token.toString();
                this.tokens.add(new Token(
                    image, this.fileName,
                    line, currentColumn,
                    Tokenizer.isKeyword(image) ?
                        TokenType.KEYWORD : TokenType.IDENTIFIER
                ));
            }
        }
    }

    public List<Token> getTokens() {
        return this.tokens;
    }
}
