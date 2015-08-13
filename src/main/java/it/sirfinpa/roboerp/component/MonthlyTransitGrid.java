package it.sirfinpa.roboerp.component;


import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

/**
 * Pagina corrente griglia riepilogo transiti mensili
 */
public class MonthlyTransitGrid extends WebComponent {

    public static final int PAGE_SIZE = 16;

    private WebElement[] dayRows;
    private WebElement pagingInput;

    public MonthlyTransitGrid(WebDriver webDriver) {
        super(webDriver);
    }

    @Override
    protected void fromDriver(){
        List<WebElement> elements = webDriver.findElements(By.cssSelector("td[title=\"Timbrature\"]"));
        this.dayRows = elements.toArray(new WebElement[0]);

        WebElement pagingInput = webDriver.findElement(By.cssSelector("input.z-paging-input"));
        this.pagingInput = pagingInput;
    }

    public void goToPage(int page){
        if (getCurrentPageNumber()!=page) {
            pagingInput.click();
            pagingInput.clear();
            pagingInput.sendKeys(String.valueOf(page));
            pagingInput.sendKeys(Keys.TAB);

            //attesa rimozione ultimo elemento griglia
            ComponentHelper.waitWithTimeout(webDriver, 5).until(ExpectedConditions.stalenessOf(dayRows[dayRows.length - 1]));
        }
    }

    public void clickDay(int day){
        dayRows[day>PAGE_SIZE ? day-1-PAGE_SIZE : day-1].click();
    }


    public static MonthlyTransitGrid getInstance(WebDriver driver){
        MonthlyTransitGrid out = new MonthlyTransitGrid(driver);
        out.init();

        return out;
    }

    public int getCurrentPageNumber(){
        return Integer.valueOf(pagingInput.getAttribute("value"));
    }

}
