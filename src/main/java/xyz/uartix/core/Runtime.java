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

package xyz.uartix.core;

import xyz.uartix.util.MiscUtil;

import java.util.ArrayList;
import java.util.List;

public final class Runtime {
    private static final List<String> dependencies = new ArrayList<>();
    private static boolean testMode = false;

    public static boolean hasBeenIncluded(String fileName) {
        return Runtime.dependencies.contains(MiscUtil.computeFileHash(fileName));
    }

    public static void pushToDependencies(String fileName) {
        Runtime.dependencies.add(MiscUtil.computeFileHash(fileName));
    }

    public static void testMode() {
        Runtime.testMode = true;
    }

    public static boolean isTestMode() {
        return Runtime.testMode;
    }
}
