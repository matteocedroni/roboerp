package it.sirfinpa.roboerp.component;


import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.LinkedList;
import java.util.List;

/**
 * Tab switch mese anno corrente.
 */
public class MonthTab extends WebComponent {

    private static String[] MONTH_LABELS = new String[]{"Gen", "Feb", "Mar", "Apr", "Mag", "Giu", "Lug", "Ago", "Set", "Ott", "Nov", "Dic"};

    private WebElement[] monthElements;


    public MonthTab(WebDriver webDriver) {
        super(webDriver);
    }

    @Override
    protected void fromDriver(){
        //WebElement root = webDriver.findElement(By.cssSelector("div.nav_tabs"));

        List<WebElement> elements = new LinkedList<WebElement>();
        for (String monthLabel:MONTH_LABELS) {
            elements.add(webDriver.findElement(By.xpath("//div[text()='" + monthLabel + "']")));
        }
        this.monthElements = elements.toArray(new WebElement[0]);
    }


    public static MonthTab getInstance(WebDriver driver) {
        MonthTab out = new MonthTab(driver);
        out.init();

        return out;
    }

    public void goTo(int monthIndex){
        monthElements[monthIndex-1].click();
    }



}
