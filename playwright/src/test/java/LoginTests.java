import com.microsoft.playwright.*;
import framework.User;
import framework.pageobjects.LoginPage;
import framework.pageobjects.SecureAreaPage;
import org.testng.annotations.*;

import static org.testng.Assert.assertTrue;

public class LoginTests {

    Playwright playwright;
    Browser browser;
    BrowserContext context;
    Page page;

    @BeforeClass
    void launchBrowser() {
        playwright = Playwright.create();
        browser = playwright.chromium().launch();
    }

    @BeforeMethod
    void createContextAndPage() {
        context = browser.newContext();
        page = context.newPage();
    }

    @Test
    public void validLogin() {

        User validUser = User.createNewRandomUser();
        // Ideally we'd have users stored in a config file or some other data store which we can simply load, but
        // for ease I'm just hard coding values
        validUser.setUsername("tomsmith");
        validUser.setPassword("SuperSecretPassword!");

        SecureAreaPage secure = new LoginPage(page).navigate().loginAs(validUser);

        assertTrue(secure.getAlertText().contains("You logged into a secure area!"));
    }

    @Test
    public void invalidUsername() {

        User invalidUser = User.createNewRandomUser();
        invalidUser.setUsername("nottomsmith");
        invalidUser.setPassword("SuperSecretPassword!");

        LoginPage login = new LoginPage(page).navigate().failLoginAs(invalidUser);

        assertTrue(login.getAlertText().contains("Your username is invalid!"));
    }

    @Test
    public void invalidPassword() {

        User invalidUser = User.createNewRandomUser();
        invalidUser.setUsername("tomsmith");
        invalidUser.setPassword("WrongPassword");

        LoginPage login = new LoginPage(page).navigate().failLoginAs(invalidUser);

        assertTrue(login.getAlertText().contains("Your password is invalid!"));
    }

    @Test
    public void emptyUsername() {

        User invalidUser = User.createNewRandomUser();
        invalidUser.setUsername("");
        invalidUser.setPassword("WrongPassword");

        LoginPage login = new LoginPage(page).navigate().failLoginAs(invalidUser);

        assertTrue(login.getAlertText().contains("Your username is invalid!"));
    }

    @Test
    public void emptyPassword() {

        User invalidUser = User.createNewRandomUser();
        invalidUser.setUsername("tomsmith");
        invalidUser.setPassword("");

        LoginPage login = new LoginPage(page).navigate().failLoginAs(invalidUser);

        assertTrue(login.getAlertText().contains("Your password is invalid!"));
    }

    @AfterMethod
    void closeContext() {
        context.close();
    }

    @AfterClass
    void closeBrowser() {
        playwright.close();
    }

}
