package ru.netology.web;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class NegativeCardTest {
    private WebDriver driver;

    @BeforeAll
    static void setUpAll() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");
        options.addArguments("--headless");
        driver = new ChromeDriver(options);
        driver.get("http://localhost:9999");
    }

    @AfterEach
    void tearDown() {
        driver.quit();
        driver = null;
    }

    @Test
    void shouldBeFailedIncorrectName() {
        List<WebElement> elements = driver.findElements(By.className("input__control"));
        elements.get(0).sendKeys("Asaulka Ksenia");
        elements.get(1).sendKeys("+79283159595");
        driver.findElement(By.className("checkbox__box")).click();
        driver.findElement(By.className("button")).click();

        assertEquals("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.",
                driver.findElement(By.cssSelector("[data-test-id=name].input_invalid .input__sub")).getText().trim());
    }
    @Test
    void shouldBeFailedIncorrectNameNull() {
        List<WebElement> elements = driver.findElements(By.className("input__control"));
        elements.get(0).sendKeys("");
        elements.get(1).sendKeys("+79283159595");
        driver.findElement(By.className("checkbox__box")).click();
        driver.findElement(By.className("button")).click();

        assertEquals("Поле обязательно для заполнения",
                driver.findElement(By.cssSelector("[data-test-id=name].input_invalid .input__sub")).getText().trim());
    }
    @Test
    void shouldBeFailedIncorrectPhoneMinSymbols() {
        List<WebElement> elements = driver.findElements(By.className("input__control"));
        elements.get(0).sendKeys("Асаулка Ксения");
        elements.get(1).sendKeys("+792831");
        driver.findElement(By.className("checkbox__box")).click();
        driver.findElement(By.className("button")).click();

        assertEquals("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.",
                driver.findElement(By.cssSelector("[data-test-id=phone].input_invalid .input__sub")).getText().trim());
    }
    @Test
    void shouldBeFailedIncorrectPhoneNoSymbols() {
        List<WebElement> elements = driver.findElements(By.className("input__control"));
        elements.get(0).sendKeys("Асаулка Ксения");
        elements.get(1).sendKeys("79283159595");
        driver.findElement(By.className("checkbox__box")).click();
        driver.findElement(By.className("button")).click();

        assertEquals("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.",
                driver.findElement(By.cssSelector("[data-test-id=phone].input_invalid .input__sub")).getText().trim());
    }
    @Test
    void shouldBeFailedIncorrectPhoneExtraSymbols() {
        List<WebElement> elements = driver.findElements(By.className("input__control"));
        elements.get(0).sendKeys("Асаулка Ксения");
        elements.get(1).sendKeys("+79283159-595");
        driver.findElement(By.className("checkbox__box")).click();
        driver.findElement(By.className("button")).click();

        assertEquals("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.",
                driver.findElement(By.cssSelector("[data-test-id=phone].input_invalid .input__sub")).getText().trim());
    }
    @Test
    void shouldBeFailedIncorrectPhoneNull() {
        List<WebElement> elements = driver.findElements(By.className("input__control"));
        elements.get(0).sendKeys("Асаулка Ксения");
        elements.get(1).sendKeys("");
        driver.findElement(By.className("checkbox__box")).click();
        driver.findElement(By.className("button")).click();

        assertEquals("Поле обязательно для заполнения",
                driver.findElement(By.cssSelector("[data-test-id=phone].input_invalid .input__sub")).getText().trim());
    }
    @Test
    void shouldBeFailedIncorrectCheckbox() {
        //List<WebElement> elements = driver.findElements(By.className("input__control"));
        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Асаулка Ксения");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+79283159595");
        driver.findElement(By.className("button")).click();

        assertTrue(driver.findElement(By.cssSelector("[data-test-id=agreement].input_invalid")).isDisplayed());
    }

}