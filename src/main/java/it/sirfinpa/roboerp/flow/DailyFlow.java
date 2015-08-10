package it.sirfinpa.roboerp.flow;

import it.sirfinpa.roboerp.component.DailyTransitForm;
import it.sirfinpa.roboerp.component.MonthlyTransitGrid;
import it.sirfinpa.roboerp.component.MonthTab;
import it.sirfinpa.roboerp.component.SideMenu;
import it.sirfinpa.roboerp.context.ExecutionContext;
import org.openqa.selenium.WebDriver;

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
        fixedWait(5);

        SideMenu menu = SideMenu.getInstance(driver);
        menu.goToCartellino();
        fixedWait(3);

        performDaily();

        fixedWait(3);
        doLogout();
    }

    private void  performDaily(){
        if (context.getMonth()!=null && !context.getMonth().equals(context.getNowMonth())){
            logger.info("switch to month: " + context.getMonth());
            MonthTab.getInstance(driver).goTo(context.getMonth());
            fixedWait(2);
        }
        if (context.getDayList() !=null){
            logger.info("day list: " + context.getDayList());
            for (int currentDay: context.getDayList()){ performDaily(currentDay); }
            fixedWait(3);
        }
        else{ performDaily(context.getNowDay()); }

    }
    //TODO: catturare errori tramite classe z-messagebox-error ?
    private void performDaily(int dayOfMonth){
        logger.info("perform daily: " + dayOfMonth);
        logger.info("daily time profile: " + Arrays.toString(context.getDailyProfile()));

        MonthlyTransitGrid monthlyTransitGrid =
                MonthlyTransitGrid.getInstance(driver).goToPage(dayOfMonth > MonthlyTransitGrid.PAGE_SIZE ? 2 : 1);

        monthlyTransitGrid.clickDay(dayOfMonth);

        DailyTransitForm transitForm = DailyTransitForm.getInstance(driver);
        boolean odd = true;
        for (String dailyElement:context.getDailyProfile()){
            transitForm = transitForm.addTransit(dailyElement, odd ? DailyTransitForm.TransitType.IN : DailyTransitForm.TransitType.OUT);
            odd=!odd;
        }
        takeScreenShot();
        //fine
        transitForm.exit();
    }
}
