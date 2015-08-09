package it.sirfinpa.roboerp.component;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * Form autenticazione utenza.
 */
public class LoginForm extends WebComponent {

    private WebElement username;
    private WebElement password;
    private WebElement loginButton;

    public LoginForm(WebDriver webDriver) {
        super(webDriver);
    }

    @Override
    protected void fromDriver(){
        List<WebElement> fields = webDriver.findElements(By.className("z-textbox"));
        this.username = fields.get(0);
        this.password = fields.get(1);
        this.loginButton = webDriver.findElements(By.className("z-toolbarbutton")).get(0);
    }

    public static LoginForm getInstance(WebDriver driver){
        LoginForm out = new LoginForm(driver);
        out.init();

        return out;
    }

    public void submit(String username, String password){
        this.username.sendKeys( username );
        this.password.sendKeys( password );
        loginButton.click();
    }

}
