package it.sirfinpa.roboerp;

import it.sirfinpa.roboerp.cli.CliOptions;
import it.sirfinpa.roboerp.cli.CliHeader;
import it.sirfinpa.roboerp.cli.OptionParser;
import it.sirfinpa.roboerp.context.ExecutionContextHolder;
import it.sirfinpa.roboerp.flow.DailyFlow;
import it.sirfinpa.roboerp.flow.ErpFlow;
import it.sirfinpa.roboerp.webdriver.WebDriverFactory;
import org.apache.log4j.Logger;
import org.kohsuke.args4j.CmdLineException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;

import java.io.*;
import java.util.concurrent.TimeUnit;

public class RopoErp {

    private static Logger logger = Logger.getLogger(RopoErp.class);

    public static final int EXIT_CODE_OK = 0;
    public static final int EXIT_CODE_KO = 1;

    private WebDriver driver;
    private CliOptions cliOptions;
    private ErpFlow erpFlow;

    public static void main(String[] args) {
        int exitCode = EXIT_CODE_KO;

        RopoErp ropoErp = new RopoErp();
        try {
            CliHeader.showHeader();
            ropoErp.parseCommandLine(args);
            if (!ropoErp.cliOptions.isHelp()){
                ropoErp.init(args);
                ropoErp.erpFlow.execute();
            }
            exitCode = EXIT_CODE_OK;
        }
        catch (Exception e){
            logger.error(e, e);
        }
        finally {
            if (ropoErp!=null){ ropoErp.close(); }
            System.exit( exitCode );
        }
    }

    private void parseCommandLine(String[] args) throws CmdLineException {
        cliOptions = new OptionParser(args).getOptions();
    }

    private void init(String[] args) throws IOException {
        RoboProperty property = RoboProperty.loadFrom(findPropertiesFile());

        ExecutionContextHolder.init(cliOptions, property);

        java.util.logging.Logger pjsLogger = java.util.logging.Logger.getLogger(PhantomJSDriverService.class.getName());
        pjsLogger.setUseParentHandlers( false );

        driver = Boolean.TRUE.equals(property.getHeadless())
                ? WebDriverFactory.getPhantomJsDriver() : WebDriverFactory.getFirefoxDriver();

        driver.manage().deleteAllCookies();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        erpFlow = new DailyFlow(driver, ExecutionContextHolder.getExecutionContext());
    }

    private void close(){
        if (driver!=null){
            driver.close();
            driver.quit();
        }
    }

    private InputStream findPropertiesFile() throws FileNotFoundException {
        InputStream out = this.getClass().getResourceAsStream("/setting.properties");
        if (out==null){
            File modulePath = new File(this.getClass().getProtectionDomain().getCodeSource().getLocation().getPath());
            out = new FileInputStream(modulePath.getParent() + File.separator + "setting.properties");
        }

        return out;
    }

}
