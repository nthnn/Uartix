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

package xyz.uartix.util;

public final class MiscUtil {
    public static String multiply(String string, int count) {
        return String.valueOf(string).repeat(Math.max(0, count));
    }

    public static String unescapeCharacters(String string) {
        StringBuilder sb = new StringBuilder();

        for(int i = 0; i < string.length(); i++) {
            char ch = string.charAt(i);

            if(ch == '\\' && i + 1 < string.length()) {
                char nextChar = string.charAt(i + 1);
                switch (nextChar) {
                    case 'r':
                        sb.append('\r');
                        i++;
                        break;

                    case 'n':
                        sb.append('\n');
                        i++;
                        break;

                    case 't':
                        sb.append('\t');
                        i++;
                        break;

                    case 'b':
                        sb.append('\b');
                        i++;
                        break;

                    case 'f':
                        sb.append('\f');
                        i++;
                        break;

                    case '"':
                        sb.append('"');
                        i++;
                        break;

                    case '\\':
                        sb.append('\\');
                        i++;
                        break;

                    case 'u':
                        if(i + 5 < string.length()) {
                            String unicode = string.substring(i + 2, i + 6);
                            sb.append((char) Integer.parseInt(unicode, 16));
                            i += 5;
                        }
                        break;

                    default:
                        sb.append(ch);
                        break;
                }
            }
            else sb.append(ch);
        }

        return sb.toString();
    }
}
