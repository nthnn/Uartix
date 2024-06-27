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

#ifndef UARTIX_H
#define UARTIX_H

#include <Arduino.h>
#include <pico/stdlib.h>

#include <hardware/vreg.h>
#include <hardware/clocks.h>

enum uartix_commands {
    UARTIX_CMD_ADD = 0x00,
    UARTIX_CMD_SUB = 0x01,
    UARTIX_CMD_DIV = 0x02,
    UARTIX_CMD_MUL = 0x03,
    UARTIX_CMD_AND = 0x04,
    UARTIX_CMD_OR  = 0x05,
    UARTIX_CMD_POW = 0x06,
    UARTIX_CMD_REM = 0x07,
    UARTIX_CMD_GT  = 0x08,
    UARTIX_CMD_GE  = 0x09,
    UARTIX_CMD_LT  = 0x0a,
    UARTIX_CMD_LE  = 0x0b,
    UARTIX_CMD_SHL = 0x0c,
    UARTIX_CMD_SHR = 0x0d,
    UARTIX_CMD_POS = 0x0e,
    UARTIX_CMD_NEG = 0x0f,
    UARTIX_CMD_NOT = 0x10,
    UARTIX_CMD_RND = 0x11,
    UARTIX_CMD_RNB = 0x12
};

typedef void (*uartix_action)();
extern uartix_action uartix_actions[];

void uartix_init_rng();

inline void uartix_overclock_sys() {
    vreg_set_voltage(VREG_VOLTAGE_MAX);
    set_sys_clock_khz(262000, true);
}

inline void uartix_underclock_sys() {
    set_sys_clock_khz(72000, true);
    vreg_set_voltage(VREG_VOLTAGE_DEFAULT);
}

#endif