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
