package xyz.uartix;

import xyz.uartix.util.RuntimeArgumentParser;

public class Main {
    public static void main(String[] args) {
        RuntimeArgumentParser argParser = new RuntimeArgumentParser(args);
        if(!argParser.parse())
            System.exit(1);
    }
}
