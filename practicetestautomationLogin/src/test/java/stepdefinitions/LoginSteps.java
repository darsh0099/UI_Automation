package stepdefinitions;

import io.cucumber.java.en.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.time.Duration;
import utils.DriverFactory;

public class LoginSteps {

    WebDriver driver;
    WebDriverWait wait;
    private static final int WAIT_TIME = 10;

    @Given("user opens browser")
    public void user_opens_browser() {
        driver = DriverFactory.getDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(WAIT_TIME));
    }

    @When("user navigates to the login page")
    public void user_navigates_to_the_login_page() {
        driver.get(DriverFactory.getUrl());
        // Wait for the login form to be visible
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("username")));
        System.out.println("Login page loaded successfully");
    }

    @And("user enters username {string}")
    public void user_enters_username(String username) {
        // Wait for username field to be visible and clickable
        WebElement usernameField = wait.until(ExpectedConditions.elementToBeClickable(By.id("username")));
        usernameField.clear();
        usernameField.sendKeys(username);
        System.out.println("Username entered: " + username);
    }

    @And("user enters password {string}")
    public void user_enters_password(String password) {
        // Wait for password field to be visible and clickable
        WebElement passwordField = wait.until(ExpectedConditions.elementToBeClickable(By.id("password")));
        passwordField.clear();
        passwordField.sendKeys(password);
        System.out.println("Password entered");
    }

    @And("user clicks login button")
    public void user_clicks_login_button() {
        // Wait for submit button to be visible and clickable
        WebElement loginButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("submit")));
        loginButton.click();
        System.out.println("Login button clicked");
        
        // Wait for page navigation to complete - wait for success message element
        wait.until(ExpectedConditions.presenceOfElementLocated(By.className("post-title")));
        System.out.println("Page navigation completed");
    }

    @Then("user should be logged in successfully")
    public void user_should_be_logged_in_successfully() {
        // Wait for success message to be visible
        WebElement successMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("post-title")));
        String messageText = successMessage.getText();
        System.out.println("Success message: " + messageText);
        
        String pageTitle = driver.getTitle();
        System.out.println("Current page title: " + pageTitle);
    }

    @And("user takes a screenshot of successful login")
    public void user_takes_a_screenshot_of_successful_login() {
        // Wait for the page to be fully rendered before taking screenshot
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("post-title")));
        try {
            Thread.sleep(1000); // Small delay for any final rendering
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        DriverFactory.takeScreenshot("successful_login");
        DriverFactory.closeDriver();
    }
}