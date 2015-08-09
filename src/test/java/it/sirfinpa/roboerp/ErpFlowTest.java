package it.sirfinpa.roboerp;


import it.sirfinpa.roboerp.cli.OptionParser;
import it.sirfinpa.roboerp.context.ExecutionContextHolder;
import it.sirfinpa.roboerp.flow.DailyFlow;
import it.sirfinpa.roboerp.flow.ErpFlow;
import it.sirfinpa.roboerp.webdriver.WebDriverFactory;
import org.junit.Before;
import org.junit.Test;
import org.kohsuke.args4j.CmdLineException;

import java.io.FileInputStream;
import java.io.IOException;

public class ErpFlowTest {

    RoboProperty property;

    @Before
    public void setup() throws IOException {
        property = RoboProperty.loadFrom( new FileInputStream( System.getProperty("cfgFile")) );
    }

    @Test
    public void testCurrentDay() throws CmdLineException {
        ExecutionContextHolder.init(new OptionParser(new String[]{}).getOptions(), property);

        ErpFlow erpFlow = new DailyFlow(
                WebDriverFactory.getPhantomJsDriver(),
                ExecutionContextHolder.getExecutionContext());
    }

    @Test
    public void testDayList() throws CmdLineException {
        ExecutionContextHolder.init(new OptionParser(new String[]{"-d=1", "-m=1"}).getOptions(), property);

        ErpFlow erpFlow = new DailyFlow(
                WebDriverFactory.getPhantomJsDriver(),
                ExecutionContextHolder.getExecutionContext());
    }

}
