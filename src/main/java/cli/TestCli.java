package cli;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Options;
import utils.CliRunner;
import utils.CMDUtil;

/**
 * Created by Yoosan on 15/9/25.
 * All rights reserved @SYSUNLP GROUP
 */
public class TestCli implements CliRunner {
    public Options initOptions(){
        Options options = new Options();
        return options;
    }

    public static void main(String[] args) {
        CMDUtil.initRunner(args, "Test", new TestCli());
    }

    public boolean validateOptions(CommandLine commandLine) {
        return true;
    }

    public void start(CommandLine commandLine) {
        System.out.println("Hello world");
    }
}
