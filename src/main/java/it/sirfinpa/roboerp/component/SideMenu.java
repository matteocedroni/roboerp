package it.sirfinpa.roboerp.component;


import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * Menu' applicativo laterale.
 */
public class SideMenu extends WebComponent {

    private WebElement[] menuElements;


    public SideMenu(WebDriver webDriver) {
        super(webDriver);
    }

    @Override
    protected void fromDriver(){
        WebElement root = webDriver.findElement(By.cssSelector("div.nav_tabs"));

        List<WebElement> elements = root.findElements(By.xpath(".//a[@class=\"z-toolbarbutton\"]"));
        this.menuElements = elements.toArray(new WebElement[0]);
    }


    public static SideMenu getInstance(WebDriver driver) {
        SideMenu out = new SideMenu(driver);
        out.init();

        return out;
    }

    public void goToCartellino(){
        menuElements[3].click();
    }



}
