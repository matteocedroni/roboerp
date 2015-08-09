package it.sirfinpa.roboerp;

import it.sirfinpa.roboerp.cli.CliOptions;
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
/*
    private void cartellino() {
        loadHome();
        doLogin();
        fixedWait(5);

        SideMenu menu = SideMenu.getInstance(driver);
        menu.goToCartellino();
        fixedWait(3);

        performDaily();

        fixedWait(3);
        doLogout();
    }
*/
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

/*
    private WebElement findLogout(){
        logger.info("find logout element...");
        WebElement element = driver.findElement(By.cssSelector("img.logout.z-image"));
        logger.info("logout element: " + element);
        return element;
    }
    private void loadHome(){
        driver.get(RoboProperty.getErpHomeUrl());
        driver.getPageSource();
    }

    private void doLogin(){
        LoginForm.getInstance(driver)
                .submit(RoboProperty.getUsername(), RoboProperty.getPassword());
    }

    private void doLogout() {
        findLogout().click();
    }


    private void  performDaily(){
        if (cliOptions.getMonth()!=null){
            logger.info("switch to month: " + cliOptions.getMonth());
            MonthTab.getInstance(driver).goTo(cliOptions.getMonth());
            fixedWait(2);
        }
        if (cliOptions.getDayList() !=null){
            logger.info("day list: " + cliOptions.getDayList());
            for (String currentDay: cliOptions.getDayList().split(",")){ performDaily(Integer.valueOf(currentDay)); }
            fixedWait(3);
        }
        else{ performDaily(determineDayOfMonth()); }

    }

    private void performDaily(int dayOfMonth){
        logger.info("perform daily: " + dayOfMonth);
        logger.info("daily time profile: " + Arrays.toString(RoboProperty.getDailyProfile()));

        CartellinoGrid cartellinoGrid =
                CartellinoGrid.getInstance(driver).goToPage(dayOfMonth > CartellinoGrid.PAGE_SIZE ? 2 : 1);

        cartellinoGrid.clickDay(dayOfMonth);

        CartellinoForm cartellinoForm = CartellinoForm.getInstance(driver);
        boolean odd = true;
        for (String dailyElement:RoboProperty.getDailyProfile()){
            cartellinoForm = cartellinoForm.addTransit(dailyElement, odd ? CartellinoForm.TransitType.IN : CartellinoForm.TransitType.OUT);
            odd=!odd;
        }
        takeScreenShot();
        //fine
        cartellinoForm.exit();
    }

    private void fixedWait(int seconds){
        try { Thread.sleep((seconds*1000)); }
        catch (InterruptedException e) {
            logger.error(e, e);
        }
    }

    private int determineDayOfMonth(){
        return new GregorianCalendar().get(GregorianCalendar.DAY_OF_MONTH);
    }
    */

    private void close(){
        if (driver!=null){
            driver.close();
            driver.quit();
        }
    }

    /*
    private void takeScreenShot(){
        try {
            File ss = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            FileUtils.copyFile(ss, new File("sshot_" + System.currentTimeMillis() + ".png"));
        }
        catch (Exception e){
            logger.error(e);
        }
    }
*/
    private InputStream findPropertiesFile() throws FileNotFoundException {
        InputStream out = this.getClass().getResourceAsStream("/setting.properties");
        if (out==null){
            File modulePath = new File(this.getClass().getProtectionDomain().getCodeSource().getLocation().getPath());
            out = new FileInputStream(modulePath.getParent() + File.separator + "setting.properties");
        }

        return out;
    }
}
