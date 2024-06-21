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
