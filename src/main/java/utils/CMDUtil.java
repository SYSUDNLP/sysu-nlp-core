package utils;

/**
 * Created by Yoosan on 15/9/25.
 * All rights reserved @SYSUNLP GROUP
 */

import org.apache.commons.cli.*;

/**
 * The utility of Commandline
 */
public class CMDUtil {

    public static final String CLI_PARAM_I = "i";
    public static final String CLI_PARAM_O = "o";
    public static final String CLI_PARAM_HELP = "help";

    private static HelpFormatter formatter = new HelpFormatter();

    /**
     * Start runner
     * @param args
     * @param cmdName
     * @param runner
     */
    public static void initRunner(String[] args, String cmdName, CliRunner runner) {
        CommandLineParser parser = new GnuParser();
        Options options = runner.initOptions();
        try {
            CommandLine commandLine = parser.parse(options, args);
            if (!runner.validateOptions(commandLine) || commandLine.hasOption(CLI_PARAM_HELP)) {
                formatter.printHelp(cmdName, options);
                return;
            }
            runner.start(commandLine);
        } catch (ParseException e) {
            System.out.println("[Unexpected exception]" + e.getMessage());
            formatter.printHelp(cmdName, options);
            e.printStackTrace();
        }
    }
}
