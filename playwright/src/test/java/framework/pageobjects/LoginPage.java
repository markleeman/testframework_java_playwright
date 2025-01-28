package framework.pageobjects;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import framework.User;

import java.nio.file.Paths;

public class LoginPage {
    private Page page;

    Locator usernameTextbox;
    Locator passwordTextbox;
    Locator alert;

    public LoginPage(Page page) {
        this.page = page;

        usernameTextbox = page.getByLabel("Username");
        passwordTextbox = page.getByLabel("Password");
        alert = page.locator("id=flash");
    }

    public LoginPage navigate() {
        page.navigate("https://the-internet.herokuapp.com/login");
        return this;
    }

    public SecureAreaPage loginAs(User user) {
        usernameTextbox.fill(user.getUsername());
        passwordTextbox.fill(user.getPassword());

        passwordTextbox.press("Enter");

        return new SecureAreaPage(page);
    }

    public LoginPage failLoginAs(User user) {
        usernameTextbox.fill(user.getUsername());
        passwordTextbox.fill(user.getPassword());
        passwordTextbox.press("Enter");

        return this;
    }

    public String getAlertText() {
        return alert.innerText();
    }
}