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
}
