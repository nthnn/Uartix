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

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import com.fazecast.jSerialComm.SerialPort;
import com.fazecast.jSerialComm.SerialPortDataListener;
import com.fazecast.jSerialComm.SerialPortEvent;
import com.fazecast.jSerialComm.SerialPortIOException;

public final class Uart {
    private static boolean hasNewData = false;
    private static SerialPort serialPort = null;
    private static OutputStream serialOutputStream = null;
    private static InputStream serialInputStream = null;
    private static final ByteArrayOutputStream data = new ByteArrayOutputStream();

    public static List<String> listSerialPorts() {
        List<String> serialPortList = new ArrayList<>();

        for(SerialPort port : SerialPort.getCommPorts())
            serialPortList.add(port.getSystemPortName());
        return serialPortList;
    }

    public static void connect(String commPort) throws SerialPortIOException {
        Uart.serialPort = SerialPort.getCommPort(commPort);
        if(Uart.serialPort == null)
            throw new SerialPortIOException("Serial port not found: " + commPort);

        Uart.serialPort.setComPortParameters(
            115200, 8, 1,
            SerialPort.NO_PARITY);
        Uart.serialPort.openPort();

        Uart.serialPort.addDataListener(new SerialPortDataListener() {
            @Override
            public int getListeningEvents() {
                return SerialPort.LISTENING_EVENT_DATA_AVAILABLE;
            }

            @Override
            public void serialEvent(SerialPortEvent event) {
                int size = event.getSerialPort().bytesAvailable();
                byte[] buffer = new byte[size];

                event.getSerialPort().readBytes(buffer, size);
                try {
                    Uart.data.write(buffer);
                    Uart.hasNewData = true;
                }
                catch(IOException _) { }
            }
        });

        Uart.serialOutputStream = Uart.serialPort.getOutputStream();
        Uart.serialInputStream = Uart.serialPort.getInputStream();
    }

    public static void disconnect() throws IOException {
        Uart.hasNewData = false;
        Uart.data.reset();

        Uart.serialOutputStream.close();
        Uart.serialInputStream.close();

        Uart.serialPort.removeDataListener();
        Uart.serialPort.clearBreak();
        Uart.serialPort.clearDTR();
        Uart.serialPort.clearRTS();
        Uart.serialPort.closePort();
    }

    public static void write(byte[] bytes)
        throws IOException
    {
        Uart.serialOutputStream.write(bytes);
        Uart.serialOutputStream.close();
    }

    public static byte[] read() {
        while(!Uart.hasNewData)
            Thread.yield();

        byte[] data = Uart.data.toByteArray();

        Uart.hasNewData = false;
        Uart.data.reset();

        return data;
    }
}
