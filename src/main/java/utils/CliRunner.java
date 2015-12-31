package utils;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Options;

/**
 * Created by Yoosan on 15/9/24.
 * All rights reserved @SYSUNLP GROUP
 */
public interface CliRunner {
    /**
     * Init the options
     *
     * @return Options
     */
    public Options initOptions();

    /**
     * @param cmdLine
     * @return validate result
     */
    public boolean validateOptions(CommandLine cmdLine);

    /**
     * start the program
     *
     * @param cmdLine
     */
    public void start(CommandLine cmdLine);
}
