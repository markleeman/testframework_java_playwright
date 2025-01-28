package framework.pageobjects;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class SecureAreaPage {

    private Page page;

    Locator alert;

    public SecureAreaPage(Page page) {
        this.page = page;

        alert = page.locator("id=flash");
    }

    public String getAlertText() {
        return alert.innerText();
    }
}