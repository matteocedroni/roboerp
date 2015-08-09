package it.sirfinpa.roboerp.component;


import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * Pagina corrente griglia riepilogo transiti giornalieri
 */
public class CartellinoGrid extends WebComponent {

    public static final int PAGE_SIZE = 16;

    private WebElement[] dayRows;
    private WebElement pagingInput;

    public CartellinoGrid(WebDriver webDriver) {
        super(webDriver);
    }

    @Override
    protected void fromDriver(){
        List<WebElement> elements = webDriver.findElements(By.cssSelector("td[title=\"Timbrature\"]"));
        this.dayRows = elements.toArray(new WebElement[0]);

        WebElement pagingInput = webDriver.findElement(By.cssSelector("input.z-paging-input"));
        this.pagingInput = pagingInput;
    }

    public CartellinoGrid goToPage(int page){
        pagingInput.click();
        pagingInput.clear();
        pagingInput.sendKeys( String.valueOf(page) );
        pagingInput.sendKeys(Keys.TAB);

        fixedWait(2);
        return CartellinoGrid.getInstance(webDriver);
    }

    public void clickDay(int day){
        dayRows[day>PAGE_SIZE ? day-1-PAGE_SIZE : day-1].click();
    }


    public static CartellinoGrid getInstance(WebDriver driver){
        CartellinoGrid out = new CartellinoGrid(driver);
        out.init();

        return out;
    }

}
