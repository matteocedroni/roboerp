package it.sirfinpa.roboerp.component;


import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.Collection;
import java.util.concurrent.TimeUnit;

public class ComponentHelper {

    protected static Logger logger = Logger.getLogger(ComponentHelper.class);

    public static String printElement(WebElement element){
        return (element!=null ? element.toString() : null);
    }

    public static String printElements(Collection<WebElement> elements){
        return printElements(elements.toArray(new WebElement[]{}));
    }

    public static String printElements(WebElement[] elements){
        StringBuilder stringBuilder = new StringBuilder();

        if (elements!=null && elements.length>0){
            for (WebElement element:elements){
                stringBuilder.append("\n").append(printElement(element));
            }
        }

        return stringBuilder.toString();
    }

    public static void waitForApplicationMessage(WebDriver webDriver, String containing){
        WebElement resultMessage = webDriver.findElement(By.xpath(".//div[@class=\"z-notification-content\" and contains(text(),\""+containing+"\")]"));
        waitWithTimeout(webDriver, 5).pollingEvery(100, TimeUnit.MILLISECONDS)
                .until(ExpectedConditions.stalenessOf(resultMessage));
    }

    public static WebDriverWait waitWithTimeout(WebDriver webDriver, int seconds){
        return new WebDriverWait(webDriver, seconds);
    }

}
