package it.sirfinpa.roboerp.flow;

import it.sirfinpa.roboerp.component.SideMenu;
import it.sirfinpa.roboerp.context.ExecutionContext;
import org.openqa.selenium.WebDriver;


/**
 * Flusso elementare Login/Logout.
 *
 * Consente un test di accesso al sistema e produce uno screenshot della pagina
 * principale.
 */
public class LoginLogoutFlow extends AbstractFlow implements ErpFlow {


    public LoginLogoutFlow(WebDriver driver, ExecutionContext context) {
        super(driver, context);
    }

    @Override
    public void execute() {
        loadHome();
        doLogin();
        SideMenu.getInstance(driver);
        takeScreenShot("welcome.png");
        doLogout();
    }
}
