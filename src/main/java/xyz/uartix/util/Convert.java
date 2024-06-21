package xyz.uartix.util;

import java.nio.ByteBuffer;

public final class Convert {
    private static void reverse(byte[] array) {
        int left = 0, right = array.length - 1;
        while (left < right) {
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
}
