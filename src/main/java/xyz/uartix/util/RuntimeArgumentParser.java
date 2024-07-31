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

import net.sourceforge.argparse4j.ArgumentParsers;
import net.sourceforge.argparse4j.impl.Arguments;
import net.sourceforge.argparse4j.inf.ArgumentParser;
import net.sourceforge.argparse4j.inf.ArgumentParserException;
import net.sourceforge.argparse4j.inf.Namespace;
import xyz.uartix.core.Runtime;
import xyz.uartix.uart.Uart;

import java.util.List;

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

            parser = ArgumentParsers.newFor("uartix").build().defaultHelp(true).description("Execute Uartix script files.");

            parser.addArgument("-p", "--port").choices(serialPorts).setDefault(serialPorts.isEmpty() ? "null" : serialPorts.getFirst()).help("Serial port device of the co-processor.");

            parser.addArgument("-t", "--test").action(Arguments.storeTrue()).help("Run test units.");

            parser.addArgument("files").nargs("*").help("List of files to execute.").required(true);

            Namespace ns = parser.parseArgs(this.args);
            this.selectedPort = ns.get("port");
            this.inputFiles = ns.get("files");

            if(ns.getBoolean("test"))
                Runtime.testMode();

            if(this.inputFiles.isEmpty())
                throw new ArgumentParserException("No input file(s).", parser);
        } catch(ArgumentParserException e) {
            parser.handleError(e);
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
