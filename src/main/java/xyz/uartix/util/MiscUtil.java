package xyz.uartix.util;

public final class MiscUtil {
    public static String multiply(String string, int count) {
        StringBuilder result = new StringBuilder();

        for(int i = 0; i < count; i++)
            result.append(string);
        return result.toString();
    }
}
