package xyz.uartix.uart;

public enum UartArithmeticCommand {
    ADD((byte) 0x00),
    SUB((byte) 0x01),
    DIV((byte) 0x02),
    MUL((byte) 0x03);

    private final byte command;

    UartArithmeticCommand(byte command) {
        this.command = command;
    }

    public byte getCommand() {
        return this.command;
    }
}
