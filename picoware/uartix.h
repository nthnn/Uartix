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
    UARTIX_CMD_MUL = 0x03
};

typedef void (*uartix_action)();
extern uartix_action uartix_actions[];

inline void uartix_overclock_sys() {
    vreg_set_voltage(VREG_VOLTAGE_MAX);
    set_sys_clock_khz(262000, true);
}

inline void uartix_underclock_sys() {
    set_sys_clock_khz(72000, true);
    vreg_set_voltage(VREG_VOLTAGE_DEFAULT);
}

#endif