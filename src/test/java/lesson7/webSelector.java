package test.java.lesson7;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class webSelector {
    public static void main(String[] args) throws InterruptedException {
        System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("start-maximized");
        WebDriver driver = new ChromeDriver(chromeOptions);

        driver.get("https://www.amazon.com/");

        Thread.sleep(1000);

        By byTodayDeal = By.xpath("//a[@data-csa-c-slot-id=\"nav_cs_0\"]");
        WebElement todayDeals = driver.findElement(byTodayDeal);
        todayDeals.click();
        Thread.sleep(1000);

        WebElement item = driver.findElement(By.xpath("//*[@id=\"dealImage\"]/div/div/div[2]"));
        item.click();
        Thread.sleep(1000);
        WebElement itemActive = driver.findElement(By.xpath("//*[@id=\"octopus-dlp-asin-stream\"]/ul/li[1]/span/div/div[1]/a/div"));
        itemActive.click();

        WebElement currentPrice = driver.findElement(By.xpath("//*[@id=\"priceblock_dealprice\"]"));
        WebElement oldprice = driver.findElement(By.xpath("//*[@id=\"price\"]/table/tbody/tr[1]/td[2]/span[1]"));

        String currentPriceText = currentPrice.getText();
        String oldPriceText = oldprice.getText();

        System.out.printf("New price: %s, old price: %s", currentPriceText.substring(1),oldPriceText.substring(1));
        Thread.sleep(3000);
        driver.quit();
    }
}
