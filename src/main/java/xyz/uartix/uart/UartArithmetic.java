package xyz.uartix.uart;

import xyz.uartix.util.Convert;

import java.io.IOException;

public final class UartArithmetic {
    private static double compute(
        UartArithmeticCommand command,
        double x, double y
    ) throws IOException {
        Uart.write(new byte[] { command.getCommand() });
        Uart.write(Convert.toBytes(x));
        Uart.write(Convert.toBytes(y));

        return Convert.toDouble(Uart.read());
    }

    public static double add(double x, double y) throws IOException {
        return UartArithmetic.compute(
            UartArithmeticCommand.ADD,
            x, y
        );
    }

    public static double sub(double x, double y) throws IOException {
        return UartArithmetic.compute(
                UartArithmeticCommand.SUB,
                x, y
        );
    }

    public static double div(double x, double y) throws IOException {
        return UartArithmetic.compute(
                UartArithmeticCommand.DIV,
                x, y
        );
    }

    public static double mul(double x, double y) throws IOException {
        return UartArithmetic.compute(
                UartArithmeticCommand.MUL,
                x, y
        );
    }

    public static double and(double x, double y) throws IOException {
        return UartArithmetic.compute(
                UartArithmeticCommand.AND,
                x, y
        );
    }

    public static double or(double x, double y) throws IOException {
        return UartArithmetic.compute(
                UartArithmeticCommand.OR,
                x, y
        );
    }

    public static double rem(double x, double y) throws IOException {
        return UartArithmetic.compute(
                UartArithmeticCommand.REM,
                x, y
        );
    }

    public static double pow(double x, double y) throws IOException {
        return UartArithmetic.compute(
                UartArithmeticCommand.POW,
                x, y
        );
    }

    public static double gt(double x, double y) throws IOException {
        return UartArithmetic.compute(
                UartArithmeticCommand.GT,
                x, y
        );
    }

    public static double ge(double x, double y) throws IOException {
        return UartArithmetic.compute(
                UartArithmeticCommand.GE,
                x, y
        );
    }

    public static double lt(double x, double y) throws IOException {
        return UartArithmetic.compute(
                UartArithmeticCommand.LT,
                x, y
        );
    }

    public static double le(double x, double y) throws IOException {
        return UartArithmetic.compute(
                UartArithmeticCommand.LE,
                x, y
        );
    }

    public static double shl(double x, double y) throws IOException {
        return UartArithmetic.compute(
                UartArithmeticCommand.SHL,
                x, y
        );
    }

    public static double shr(double x, double y) throws IOException {
        return UartArithmetic.compute(
                UartArithmeticCommand.SHR,
                x, y
        );
    }
}
