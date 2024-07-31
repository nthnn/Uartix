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

package xyz.uartix.util;

import java.nio.ByteBuffer;

public final class Convert {
    private static void reverse(byte[] array) {
        int left = 0, right = array.length - 1;
        while(left < right) {
            byte temp = array[left];

            array[left] = array[right];
            array[right] = temp;

            left++;
            right--;
        }
    }

    public static double toDouble(byte[] bytes) {
        if(bytes == null || bytes.length != 8)
            throw new IllegalArgumentException("Byte array must be non-null and have length 8");

        reverse(bytes);
        return ByteBuffer.wrap(bytes).getDouble();
    }

    public static byte[] toBytes(double number) {
        ByteBuffer buffer = ByteBuffer.allocate(8);
        buffer.putDouble(number);

        byte[] bytes = buffer.array();
        reverse(bytes);

        return bytes;
    }

    public static double translateDigit(String image) {
        if(image == null || image.isEmpty())
            throw new IllegalArgumentException("Input string is null or empty");

        if(image.startsWith("0b"))
            return Convert.parseBinary(image.substring(2));
        else if(image.startsWith("0t"))
            return Convert.parseBase3(image.substring(2));
        else if(image.startsWith("0c"))
            return Convert.parseOctal(image.substring(2));
        else if(image.startsWith("0x"))
            return Convert.parseHex(image.substring(2));

        return Double.parseDouble(image);
    }

    private static double parseBinary(String str) {
        return Double.parseDouble(Integer.toString(Integer.parseInt(str, 2)));
    }

    private static double parseBase3(String str) {
        return Double.parseDouble(Integer.toString(Integer.parseInt(str, 3)));
    }

    private static double parseOctal(String str) {
        return Double.parseDouble(Integer.toString(Integer.parseInt(str, 8)));
    }

    private static double parseHex(String str) {
        return Double.parseDouble(Integer.toString(Integer.parseInt(str, 16)));
    }
}
