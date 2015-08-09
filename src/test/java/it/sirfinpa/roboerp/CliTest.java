package it.sirfinpa.roboerp;


import it.sirfinpa.roboerp.cli.OptionParser;
import org.junit.Test;
import org.kohsuke.args4j.CmdLineException;

public class CliTest {


    @Test
    public void testCli(){
        try {
            OptionParser cliParser = new OptionParser(new String[]{"-d=1,2,3", "-m=1"});
        } catch (CmdLineException e) {
            e.printStackTrace();
        }
    }


    @Test
    public void testCliHelp(){
        try {
            OptionParser cliParser = new OptionParser(new String[]{"-h"});
        } catch (CmdLineException e) {
            e.printStackTrace();
        }
    }

}
