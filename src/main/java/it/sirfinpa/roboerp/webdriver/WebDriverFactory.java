package it.sirfinpa.roboerp.webdriver;

import it.sirfinpa.roboerp.RoboProperty;
import it.sirfinpa.roboerp.context.ExecutionContextHolder;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;

public class WebDriverFactory {

    public static WebDriver getPhantomJsDriver(){
        DesiredCapabilities caps = DesiredCapabilities.phantomjs();
        caps.setCapability(PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY, ExecutionContextHolder.getExecutionContext().getPhantomjsBinPath());
        caps.setCapability(PhantomJSDriverService.PHANTOMJS_CLI_ARGS, new String[] {"--webdriver-loglevel=ERROR"});
        caps.setCapability(PhantomJSDriverService.PHANTOMJS_GHOSTDRIVER_CLI_ARGS, new String[] {"--logLevel=ERROR"});
        return new PhantomJSDriver(caps);

    }

    public static WebDriver getFirefoxDriver(){
        return new FirefoxDriver();
    }

}
