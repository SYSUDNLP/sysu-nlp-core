package cli;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import utils.CliRunner;
import utils.CMDUtil;
import utils.TextUtil;

/**
 * Created by Yoosan on 15/9/25.
 * All rights reserved @SYSUNLP GROUP
 */

/**
 * Read amazon reviews and store the results to text file
 */
public class ReadJsonCli implements CliRunner {

    public static void main(String[] args) {
        CMDUtil.initRunner(args, "readfile", new ReadJsonCli());
    }

    public Options initOptions() {
        Options options = new Options();
        options.addOption(new Option(CMDUtil.CLI_PARAM_I, true, "[INFO] InputFile which are amazon reviews"));
        options.addOption(new Option(CMDUtil.CLI_PARAM_O, true, "[INFO] OutFile format is txt"));
        return options;
    }

    public boolean validateOptions(CommandLine commandLine) {
        return true;
    }

    public void start(CommandLine commandLine) {

        String inPutFile = commandLine.getOptionValue(CMDUtil.CLI_PARAM_I);
        String outPutFile = commandLine.getOptionValue(CMDUtil.CLI_PARAM_O);
        TextUtil.amazonReviewTokenizer(inPutFile, outPutFile);

    }
}