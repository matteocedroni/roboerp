package it.sirfinpa.roboerp.component;


import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * Form inserimento dati di transito
 */
public class CartellinoForm extends WebComponent {

    public enum TransitType{
        IN("E"), OUT("U");

        private String type;

        TransitType(String type){
            this.type = type;
        }

        public String getType(){ return type; }
    }


    private WebElement timeBox;
    private WebElement timeBoxUp;
    private WebElement timeBoxDown;
    private WebElement transitType;
    private WebElement saveButton;
    private WebElement exitButton;

    public CartellinoForm(WebDriver webDriver) {
        super(webDriver);
    }


    @Override
    protected void fromDriver() {
        WebElement dialogRoot = webDriver.findElement(By.className("z-window-modal"));

        WebElement[] elements = new WebElement[6];
        timeBox = dialogRoot.findElement(By.xpath(".//input[@class=\"z-timebox-input\"]"));
        transitType = dialogRoot.findElement(By.xpath(".//select[@class=\"z-select\"]"));
        saveButton = dialogRoot.findElement(By.xpath(".//a[@title=\"Salva transiti\"]"));
        exitButton = dialogRoot.findElement(By.xpath(".//a[@title=\"Esci\"]"));
        timeBoxUp = dialogRoot.findElement(By.xpath(".//a[@class=\"z-timebox-icon z-timebox-up\"]"));
        timeBoxDown = dialogRoot.findElement(By.xpath(".//a[@class=\"z-timebox-icon z-timebox-down\"]"));
    }

    public static CartellinoForm getInstance(WebDriver driver){
        CartellinoForm out = new CartellinoForm(driver);
        out.init();

        return out;
    }

    public CartellinoForm addTransit(String time, TransitType type){
        logger.info(String.format("add transit [%1$s] --> [%2$s]", time, type.getType().toString()));

        timeBox.click();
        timeBox.sendKeys(Keys.BACK_SPACE, Keys.BACK_SPACE, Keys.BACK_SPACE, Keys.BACK_SPACE);
        timeBox.sendKeys(time);
        transitType.sendKeys(type.getType());
        saveButton.click();

        fixedWait(3);

        return getInstance(webDriver);
    }

    public void exit(){
        exitButton.click();
    }


}
