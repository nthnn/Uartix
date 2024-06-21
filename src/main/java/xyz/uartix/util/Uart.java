package xyz.uartix.util;

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

    public static void connect() throws SerialPortIOException {
        List<String> ports = Uart.listSerialPorts();
        if(ports.isEmpty())
            throw new SerialPortIOException("No available serial port device.");

        Uart.connect(ports.getFirst());
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
