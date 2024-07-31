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

package xyz.uartix.uart;

import xyz.uartix.util.Convert;

import java.io.IOException;

public final class UartOperation {
    private static double compute(UartCommand command, double x, double y) throws IOException {
        Uart.write(new byte[]{command.getCommand()});
        Uart.write(Convert.toBytes(x));
        Uart.write(Convert.toBytes(y));

        return Convert.toDouble(Uart.read());
    }

    private static double compute(UartCommand command, double v) throws IOException {
        Uart.write(new byte[]{command.getCommand()});
        Uart.write(Convert.toBytes(v));

        return Convert.toDouble(Uart.read());
    }

    private static double compute(UartCommand command) throws IOException {
        Uart.write(new byte[]{command.getCommand()});
        return Convert.toDouble(Uart.read());
    }

    public static double add(double x, double y) throws IOException {
        return UartOperation.compute(UartCommand.ADD, x, y);
    }

    public static double sub(double x, double y) throws IOException {
        return UartOperation.compute(UartCommand.SUB, x, y);
    }

    public static double div(double x, double y) throws IOException {
        return UartOperation.compute(UartCommand.DIV, x, y);
    }

    public static double mul(double x, double y) throws IOException {
        return UartOperation.compute(UartCommand.MUL, x, y);
    }

    public static double and(double x, double y) throws IOException {
        return UartOperation.compute(UartCommand.AND, x, y);
    }

    public static double or(double x, double y) throws IOException {
        return UartOperation.compute(UartCommand.OR, x, y);
    }

    public static double rem(double x, double y) throws IOException {
        return UartOperation.compute(UartCommand.REM, x, y);
    }

    public static double pow(double x, double y) throws IOException {
        return UartOperation.compute(UartCommand.POW, x, y);
    }

    public static double gt(double x, double y) throws IOException {
        return UartOperation.compute(UartCommand.GT, x, y);
    }

    public static double ge(double x, double y) throws IOException {
        return UartOperation.compute(UartCommand.GE, x, y);
    }

    public static double lt(double x, double y) throws IOException {
        return UartOperation.compute(UartCommand.LT, x, y);
    }

    public static double le(double x, double y) throws IOException {
        return UartOperation.compute(UartCommand.LE, x, y);
    }

    public static double shl(double x, double y) throws IOException {
        return UartOperation.compute(UartCommand.SHL, x, y);
    }

    public static double shr(double x, double y) throws IOException {
        return UartOperation.compute(UartCommand.SHR, x, y);
    }

    public static double pos(double v) throws IOException {
        return UartOperation.compute(UartCommand.POS, v);
    }

    public static double neg(double v) throws IOException {
        return UartOperation.compute(UartCommand.NEG, v);
    }

    public static double not(double v) throws IOException {
        return UartOperation.compute(UartCommand.NOT, v);
    }

    public static double rnd() throws IOException {
        return UartOperation.compute(UartCommand.RND);
    }

    public static boolean rnb() throws IOException {
        return UartOperation.compute(UartCommand.RNB) == 1.0;
    }
}
