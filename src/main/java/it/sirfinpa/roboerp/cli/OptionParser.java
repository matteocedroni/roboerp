package it.sirfinpa.roboerp.cli;


import org.apache.log4j.Logger;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;

public class OptionParser {
    private static Logger logger = Logger.getLogger(OptionParser.class);

    private CliOptions options;


    public OptionParser(String[] cliArgs) throws CmdLineException {
        options = new CliOptions();
        CmdLineParser parser = new CmdLineParser(options);
        try {
            parser.parseArgument(cliArgs);
            if (options.isHelp()){
                parser.printUsage(System.out);
            }
        }
        catch (CmdLineException e) {
            logger.error(e.getLocalizedMessage());
            parser.printUsage(System.out);
            throw e;
        }
    }

    public CliOptions getOptions() {
        return options;
    }
}
