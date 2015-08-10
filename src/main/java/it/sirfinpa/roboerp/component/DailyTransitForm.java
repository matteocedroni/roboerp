package it.sirfinpa.roboerp.component;


import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;

/**
 * Form inserimento dati di transito.
 */
public class DailyTransitForm extends WebComponent {

    public enum TransitType{
        IN("Entrata"), OUT("Uscita");

        private String type;

        TransitType(String type){
            this.type = type;
        }

        public String getType(){ return type; }
    }

    private WebElement dialogRoot;
    private WebElement timeBox;
    private WebElement transitType;
    private WebElement saveButton;
    private WebElement exitButton;
    private WebElement[] transitList;

    public DailyTransitForm(WebDriver webDriver) {
        super(webDriver);
    }


    @Override
    protected void fromDriver() {
        dialogRoot = webDriver.findElement(By.className("z-window-modal"));

        timeBox = dialogRoot.findElement(By.xpath(".//input[@class=\"z-timebox-input\"]"));
        transitType = dialogRoot.findElement(By.xpath(".//select[@class=\"z-select\"]"));
        saveButton = dialogRoot.findElement(By.xpath(".//a[@title=\"Salva transiti\"]"));
        exitButton = dialogRoot.findElement(By.xpath(".//a[@title=\"Esci\"]"));
    }

    public static DailyTransitForm getInstance(WebDriver driver){
        DailyTransitForm out = new DailyTransitForm(driver);
        out.init();

        return out;
    }

    public DailyTransitForm addTransit(String time, TransitType type){
        logger.info(String.format("add transit [%1$s] --> [%2$s]", time, type.getType().toString()));

        timeBox.click();
        timeBox.sendKeys(Keys.ARROW_RIGHT, Keys.ARROW_RIGHT, Keys.ARROW_RIGHT, Keys.ARROW_RIGHT, Keys.ARROW_RIGHT);
        timeBox.sendKeys(Keys.BACK_SPACE, Keys.BACK_SPACE, Keys.BACK_SPACE, Keys.BACK_SPACE);
        timeBox.sendKeys(time);
        new Select( transitType ).selectByVisibleText(type.getType());
        saveButton.click();

        waitForTransitProcess();

        return getInstance(webDriver);
    }

    private void waitForTransitProcess(){
        //Attesa presenza messaggio esito positivo
        WebElement resultMessage = webDriver.findElement(By.xpath(".//div[contains(text(),\"WFW1000000\")]"));
        /*
         * Attesa termine messaggio esito positivo.
         * La combo rappresentante la tipologia transito, e' soggetta ad aggiornamento DOM dopo ogni inserimento
         * transito. Attendere il termine del messaggio, si e' dimostrato il riferimento piu' affidabile per
         * garantire di istanziare il nuovo form dopo l'aggiornamento della combo ed evitare
         * la StaleElementReferenceException nel successivo accesso al componente.
         */
        waitWithTimeout(5).until(ExpectedConditions.stalenessOf(resultMessage));
    }

    public void exit(){
        exitButton.click();
    }


}
