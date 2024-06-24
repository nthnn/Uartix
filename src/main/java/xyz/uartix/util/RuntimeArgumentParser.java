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

import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.util.List;

import net.sourceforge.argparse4j.ArgumentParsers;
import net.sourceforge.argparse4j.inf.ArgumentParser;
import net.sourceforge.argparse4j.inf.ArgumentParserException;
import net.sourceforge.argparse4j.inf.Namespace;

import xyz.uartix.uart.Uart;

public class RuntimeArgumentParser {
    private String selectedPort;
    private List<String> inputFiles;
    private final String[] args;

    public RuntimeArgumentParser(String[] args) {
        this.args = args;
    }

    public boolean parse() {
        ArgumentParser parser = null;
        try {
            List<String> serialPorts = Uart.listSerialPorts();
            String jarName = Paths.get(this.getClass()
                .getProtectionDomain()
                .getCodeSource()
                .getLocation()
                .toURI()
                .getPath()
            ).getFileName()
                .toString();

            parser = ArgumentParsers.newFor(jarName).build()
                    .defaultHelp(true)
                    .description("Execute Uartix script files.");

            parser.addArgument("-p", "--port")
                    .choices(serialPorts)
                    .setDefault(serialPorts.isEmpty() ? "null" : serialPorts.getFirst())
                    .help("Serial port device of the co-processor.");

            parser.addArgument("files").nargs("*")
                    .help("List of files to execute.");

            Namespace ns = parser.parseArgs(this.args);
            this.selectedPort = ns.get("port");
            this.inputFiles = ns.get("files");
        }
        catch(ArgumentParserException e) {
            parser.handleError(e);
            return false;
        }
        catch(URISyntaxException e) {
            System.out.println(e.getMessage());
            return false;
        }

        return true;
    }

    public String getSelectedPort() {
        return this.selectedPort;
    }

    public List<String> getInputFiles() {
        return this.inputFiles;
    }
}
