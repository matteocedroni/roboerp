package it.sirfinpa.roboerp.flow;

import it.sirfinpa.roboerp.component.*;
import it.sirfinpa.roboerp.context.ExecutionContext;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.Arrays;

/**
 * Flusso di compilazione presenze giornaliere.
 */
public class DailyFlow extends AbstractFlow implements ErpFlow {


    public DailyFlow(WebDriver driver, ExecutionContext context) {
        super(driver, context);
    }

    @Override
    public void execute() {
        loadHome();
        doLogin();

        openCartellino();
        openMonth();
        performDaily();

        fixedWait(3);
        doLogout();
    }

    //TODO: catturare errori tramite classe z-messagebox-error ?
    private void performDaily() {
        logger.info("daily time profile: " + Arrays.toString(context.getDailyProfile()));
        int[] daysToProcess = getDaysToProcess();
        logger.info("day list:" + Arrays.toString(daysToProcess));

        MonthlyTransitGrid monthlyTransitGrid;
        for (int currentDay : daysToProcess) {
            monthlyTransitGrid = prepareMonthlyGrid(currentDay);
            addTransit(currentDay, monthlyTransitGrid);
        }
    }

    /**
     * Aggiunge i transiti giornalieri della giornata <code>dayOfMonth</code> alla griglia mensile.
     */
    private void addTransit(int dayOfMonth, MonthlyTransitGrid monthlyTransitGrid) {
        logger.info("add transit for day: " + dayOfMonth);

        monthlyTransitGrid.clickDay(dayOfMonth);
        DailyTransitForm transitForm = DailyTransitForm.getInstance(driver);
        addTransit(transitForm);
        transitForm.exit();
    }

    /**
     * Aggiunge i transiti al <code>transitForm</code> corrente, in base al profilo orario giornaliero.
     */
    private void addTransit(DailyTransitForm transitForm) {
        boolean odd = true;
        for (String dailyElement : context.getDailyProfile()) {
            transitForm.addTransit(dailyElement, odd ? DailyTransitForm.TransitType.IN : DailyTransitForm.TransitType.OUT);
            /*
             * Attesa presenza e termine messaggio esito positivo.
             * La combo rappresentante la tipologia transito, e' soggetta ad aggiornamento DOM dopo ogni inserimento
             * transito. Attendere il termine del messaggio, si e' dimostrato il riferimento piu' affidabile per
             * garantire di istanziare il nuovo form dopo l'aggiornamento della combo ed evitare
             * la StaleElementReferenceException nel successivo accesso al componente.
             */
            ComponentHelper.waitForApplicationMessage(driver, "WFW1000000");
            transitForm = DailyTransitForm.getInstance(driver);
            odd = !odd;
        }
        takeScreenShot();
    }

    private void openMonth() {
        if (context.getMonth() != null && !context.getMonth().equals(context.getNowMonth())) {
            logger.info("switch to month: " + context.getMonth());
            MonthTab.getInstance(driver).goTo(context.getMonth());
            fixedWait(2);
        }
    }

    private int[] getDaysToProcess() {
        return context.getDayList() != null ? context.getDayList() : new int[]{context.getNowDay()};
    }

    private MonthlyTransitGrid prepareMonthlyGrid(int dayToEdit) {
        MonthlyTransitGrid monthlyTransitGrid = MonthlyTransitGrid.getInstance(driver);
        //se la griglia non contiene il giorno da editare, viene cambiata pagina
        if (dayToEdit > MonthlyTransitGrid.PAGE_SIZE && monthlyTransitGrid.getCurrentPageNumber() != 2) {
            monthlyTransitGrid.goToPage(2);
            monthlyTransitGrid = MonthlyTransitGrid.getInstance(driver);
        }
        return monthlyTransitGrid;
    }

    private void openCartellino() {
        SideMenu.getInstance(driver).goToCartellino();
        ComponentHelper.waitWithTimeout(driver, 5).until(
                ExpectedConditions.presenceOfElementLocated(
                        By.xpath(".//div[@class=\"z-window-header\" and contains(text(),\"Cartellino\")]")
                ));
    }
}