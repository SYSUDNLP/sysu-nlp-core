package cli;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import utils.CliRunner;
import utils.CMDUtil;
import utils.TextUtil;

/**
 * Created by Yoosan on 15/9/26.
 * All rights reserved @SYSUNLP GROUP
 */
public class TokenizerCli implements CliRunner {

    public static void main(String[] args) {
        CMDUtil.initRunner(args, "Tokenizer", new TokenizerCli());
    }

    @Override
    public Options initOptions() {
        Options options = new Options();
        options.addOption(new Option(CMDUtil.CLI_PARAM_I, true, "[INFO] Input file."));
        options.addOption(new Option(CMDUtil.CLI_PARAM_O, true, "[INFO] Output file."));
        return options;
    }

    @Override
    public boolean validateOptions(CommandLine cmdLine) {
        return true;
    }

    @Override
    public void start(CommandLine cmdLine) {
        String inputFile = cmdLine.getOptionValue(CMDUtil.CLI_PARAM_I);
        String outputFile = cmdLine.getOptionValue(CMDUtil.CLI_PARAM_O);
        TextUtil.textTokenizer(inputFile, outputFile);
    }
}
