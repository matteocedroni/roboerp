package it.sirfinpa.roboerp.flow;


import it.sirfinpa.roboerp.component.ComponentHelper;
import it.sirfinpa.roboerp.component.LoginForm;
import it.sirfinpa.roboerp.context.ExecutionContext;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.io.File;

public abstract class AbstractFlow implements ErpFlow{

    protected Logger logger = Logger.getLogger(this.getClass());

    protected WebDriver driver;
    protected ExecutionContext context;


    public AbstractFlow(WebDriver driver, ExecutionContext context) {
        this.driver = driver;
        this.context = context;
    }

    protected void loadHome(){
        driver.get(context.getErpHomeUrl());
        driver.getPageSource();
    }

    protected void doLogin(){
        LoginForm.getInstance(driver)
                .submit(context.getUsername(), context.getPassword());
        waitForMainPage();
    }

    protected void doLogout() {
        findLogout().click();
    }

    protected void takeScreenShot(){
        takeScreenShot(String.format("sshot_%1$s.png", System.currentTimeMillis()));
    }

    protected void takeScreenShot(String name){
        try {
            File ss = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            FileUtils.copyFile(ss, new File(name));
        }
        catch (Exception e){
            logger.error(e);
        }
    }

    protected WebElement findLogout(){
        logger.info("find logout element...");
        WebElement element = driver.findElement(By.cssSelector("img.logout.z-image"));
        logger.info("logout element: " + element);
        return element;
    }

    protected void fixedWait(int seconds){
        try { Thread.sleep((seconds*1000)); }
        catch (InterruptedException e) {
            logger.error(e, e);
        }
    }

    protected void waitForMainPage(){
        ComponentHelper.waitWithTimeout(driver, 10).until(
                ExpectedConditions.elementToBeClickable(By.cssSelector("img.logout.z-image"))
        );
    }

}
