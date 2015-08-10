package it.sirfinpa.roboerp;


import it.sirfinpa.roboerp.cli.OptionParser;
import it.sirfinpa.roboerp.context.ExecutionContextHolder;
import it.sirfinpa.roboerp.flow.DailyFlow;
import it.sirfinpa.roboerp.flow.ErpFlow;
import it.sirfinpa.roboerp.webdriver.WebDriverFactory;
import org.junit.Before;
import org.junit.Test;
import org.kohsuke.args4j.CmdLineException;
import org.openqa.selenium.WebDriver;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class ErpFlowTest {

    RoboProperty property;

    @Before
    public void setup() throws IOException {
        property = RoboProperty.loadFrom( new FileInputStream( System.getProperty("cfgFile")) );
    }

    @Test
    public void testCurrentDay() throws CmdLineException {
        ExecutionContextHolder.init(new OptionParser(new String[]{}).getOptions(), property);

        ErpFlow erpFlow = new DailyFlow(initDriver(), ExecutionContextHolder.getExecutionContext());
        erpFlow.execute();
    }

    @Test
    public void testDayList() throws CmdLineException {
        ExecutionContextHolder.init(new OptionParser(new String[]{"-d=7", "-m=8"}).getOptions(), property);

        ErpFlow erpFlow = new DailyFlow(initDriver(), ExecutionContextHolder.getExecutionContext());
        erpFlow.execute();
    }

    private WebDriver initDriver(){
        WebDriver webDriver = WebDriverFactory.getPhantomJsDriver();
        webDriver.manage().deleteAllCookies();
        webDriver.manage().window().maximize();
        webDriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        return webDriver;
    }

}
