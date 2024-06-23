/*
 * This file is part of the Arduino dynaconfig library (https://github.com/nthnn/dynaconfig).
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

#include <Arduino.h>
#include "uartix.h"

#define uartix_wait_for_data(x) \
    while(Serial.available() != x)

union uartix_d2b8_u {
    double number;
    byte bytes[8];
};

inline void uartix_d2b8(double value, byte bytes[8]) {
    uartix_d2b8_u a_data;

    a_data.number = value;
    memcpy(bytes, a_data.bytes, 8);
}

inline double uartix_b82d(byte bytes[8]) {
    uartix_d2b8_u a_data;

    memcpy(a_data.bytes, bytes, 8);
    return a_data.number;
}

inline void uartix_read_number(byte buffer[8]) {
    Serial.readBytes(buffer, 8);
}

inline void uartix_write_number(double number) {
    byte b[8];

    uartix_d2b8(number, b);
    Serial.write(b, 8);
}

inline void uartix_add() {
    byte x[8], y[8];

    uartix_wait_for_data(16);
    uartix_read_number(x);
    uartix_read_number(y);

    double dx = uartix_b82d(x),
        dy = uartix_b82d(y);
    uartix_write_number(dx + dy);
}

inline void uartix_sub() {
    byte x[8], y[8];

    uartix_wait_for_data(16);
    uartix_read_number(x);
    uartix_read_number(y);

    double dx = uartix_b82d(x),
        dy = uartix_b82d(y);
    uartix_write_number(dx - dy);
}

inline void uartix_div() {
    byte x[8], y[8];

    uartix_wait_for_data(16);
    uartix_read_number(x);
    uartix_read_number(y);

    double dx = uartix_b82d(x),
        dy = uartix_b82d(y);
    uartix_write_number(dx / dy);
}

inline void uartix_mul() {
    byte x[8], y[8];

    uartix_wait_for_data(16);
    uartix_read_number(x);
    uartix_read_number(y);

    double dx = uartix_b82d(x),
        dy = uartix_b82d(y);
    uartix_write_number(dx * dy);
}

inline void uartix_and() {
    byte x[8], y[8];

    uartix_wait_for_data(16);
    uartix_read_number(x);
    uartix_read_number(y);

    long dx = (long) floor(uartix_b82d(x)),
        dy = (long) floor(uartix_b82d(y));
    uartix_write_number((double) (dx & dy));
}

inline void uartix_or() {
    byte x[8], y[8];

    uartix_wait_for_data(16);
    uartix_read_number(x);
    uartix_read_number(y);

    long dx = (long) floor(uartix_b82d(x)),
        dy = (long) floor(uartix_b82d(y));
    uartix_write_number((double) (dx | dy));
}

inline void uartix_rem() {
    byte x[8], y[8];

    uartix_wait_for_data(16);
    uartix_read_number(x);
    uartix_read_number(y);

    long dx = (long) floor(uartix_b82d(x)),
        dy = (long) floor(uartix_b82d(y));
    uartix_write_number((double) (dx % dy));
}

inline void uartix_pow() {
    byte x[8], y[8];

    uartix_wait_for_data(16);
    uartix_read_number(x);
    uartix_read_number(y);

    long dx = (long) floor(uartix_b82d(x)),
        dy = (long) floor(uartix_b82d(y));
    uartix_write_number((double) (dx ^ dy));
}

uartix_action uartix_actions[] = {
    [UARTIX_CMD_ADD] = uartix_add,
    [UARTIX_CMD_SUB] = uartix_sub,
    [UARTIX_CMD_DIV] = uartix_div,
    [UARTIX_CMD_MUL] = uartix_mul,
    [UARTIX_CMD_AND] = uartix_and,
    [UARTIX_CMD_OR]  = uartix_or,
    [UARTIX_CMD_POW] = uartix_pow,
    [UARTIX_CMD_REM] = uartix_rem
};
