package cli;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Options;
import utils.CliRunner;

/**
 * Created by Yoosan on 15/9/19.
 * All rights reserved @SYSUNLP GROUP
 */
public class Word2VecCli implements CliRunner {

    public boolean validateOptions(CommandLine commandLine) {
        return true;
    }

    public Options initOptions() {
        return new Options();
    }

    public void start(CommandLine commandLine) {

    }
}
