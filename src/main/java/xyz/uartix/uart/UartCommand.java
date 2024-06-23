package xyz.uartix.uart;

public enum UartCommand {
    ADD((byte) 0x00),
    SUB((byte) 0x01),
    DIV((byte) 0x02),
    MUL((byte) 0x03),
    AND((byte) 0x04),
    OR((byte) 0x05),
    POW((byte) 0x06),
    REM((byte) 0x07),
    GT((byte) 0x08),
    GE((byte) 0x09),
    LT((byte) 0x0a),
    LE((byte) 0x0b),
    SHL((byte) 0x0c),
    SHR((byte) 0x0d),
    POS((byte) 0x0e),
    NEG((byte) 0x0f),
    NOT((byte) 0x10);

    private final byte command;

    UartCommand(byte command) {
        this.command = command;
    }

    public byte getCommand() {
        return this.command;
    }
}
