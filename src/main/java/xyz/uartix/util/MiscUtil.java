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

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public final class MiscUtil {
    public static String multiply(String string, int count) {
        return String.valueOf(string).repeat(Math.max(0, count));
    }

    public static boolean isValidFilePath(String pathString) {
        if(pathString == null || pathString.trim().isEmpty())
            return false;

        for(char c : "<>:\"|?*".toCharArray())
            if(pathString.indexOf(c) >= 0)
                return false;

        try {
            Path path = Paths.get(pathString);
            return Files.exists(path) || !Files.isDirectory(path) || path.isAbsolute();
        } catch(Exception _) {
        }

        return false;
    }

    public static String computeFileHash(String filePath) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            try(FileInputStream fis = new FileInputStream(filePath)) {
                byte[] buffer = new byte[1024];
                int bytesRead;

                while((bytesRead = fis.read(buffer)) != -1)
                    digest.update(buffer, 0, bytesRead);
            }

            byte[] hashBytes = digest.digest();
            StringBuilder hashString = new StringBuilder();

            for(byte b : hashBytes)
                hashString.append(String.format("%02x", b));
            return hashString.toString();
        } catch(NoSuchAlgorithmException _) {
        } catch(IOException ex) {
            System.out.println("\u001b[31mI/O Error\u001b[0m: " + ex.getMessage());
            System.exit(0);
        }

        return null;
    }

    public static String unescapeCharacters(String string) {
        StringBuilder sb = new StringBuilder();

        for(int i = 0; i < string.length(); i++) {
            char ch = string.charAt(i);

            if(ch == '\\' && i + 1 < string.length()) {
                char nextChar = string.charAt(i + 1);
                switch(nextChar) {
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
            else
                sb.append(ch);
        }

        return sb.toString();
    }
}
