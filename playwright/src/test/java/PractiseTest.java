import com.microsoft.playwright.Browser;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.assertions.PlaywrightAssertions;
import com.microsoft.playwright.options.AriaRole;
import org.testng.annotations.Test;

import java.nio.file.Paths;
import java.util.regex.Pattern;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class PractiseTest {

    @Test
    public void simpletest() {

        try (Playwright playwright = Playwright.create()) {

            Browser browser = playwright.chromium().launch();
            Page page = browser.newPage();
            page.navigate("http://playwright.dev");

            // Expect a title "to contain" a substring.
            assertThat(page).hasTitle(Pattern.compile("Playwright"));

            // create a locator
            Locator getStarted = page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Get Started"));

            // Expect an attribute "to be strictly equal" to the value.
            assertThat(getStarted).hasAttribute("href", "/docs/intro");

            // Click the get started link.
            getStarted.click();

            // Expects page to have a heading with the name of Installation.
            assertThat(page.getByRole(AriaRole.HEADING,
                    new Page.GetByRoleOptions().setName("Installation"))).isVisible();
        }
    }

    @Test
    public void login() {

        try (Playwright playwright = Playwright.create()) {

            //PlaywrightAssertions.setDefaultAssertionTimeout(10_000);

            Browser browser = playwright.chromium().launch();
            Page page = browser.newPage();
            page.navigate("https://the-deposit-service.sandbox.b7h.io/auth/login");

            page.getByLabel("Email").fill("");
            page.getByLabel("Password").fill("");

            //page.getByLabel("Password").press("Enter");
            page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Login")).click();

            page.waitForURL("https://the-deposit-service.sandbox.b7h.io");

            page.screenshot(new Page.ScreenshotOptions()
                    .setPath(Paths.get("screenshot.png"))
                    .setFullPage(true));

            assertThat(page).hasTitle(Pattern.compile("My Profile"));

        }
    }
}
