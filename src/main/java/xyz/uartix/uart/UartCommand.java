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

public enum UartCommand {
    ADD((byte) 0x00), SUB((byte) 0x01), DIV((byte) 0x02), MUL((byte) 0x03), AND((byte) 0x04), OR((byte) 0x05), POW((byte) 0x06), REM((byte) 0x07), GT((byte) 0x08), GE((byte) 0x09), LT((byte) 0x0a), LE((byte) 0x0b), SHL((byte) 0x0c), SHR((byte) 0x0d), POS((byte) 0x0e), NEG((byte) 0x0f), NOT((byte) 0x10), RND((byte) 0x11), RNB((byte) 0x12);

    private final byte command;

    UartCommand(byte command) {
        this.command = command;
    }

    public byte getCommand() {
        return this.command;
    }
}
