package StepDefs;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import Utilities.UtilFactory;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class AmazonStepDef {

	WebDriver driver = null;

	UtilFactory utilfactory = new UtilFactory();

	@Given("browser is open")
	public void browser_is_open() {
		String path = System.getProperty("user.dir");
		System.out.println("path" + path);
		System.setProperty("webdriver.chrome.driver", path + "/src/test/resources/Drivers/chromedriver.exe");
		driver = new ChromeDriver();
		driver.manage().window().maximize();
	}

	@Given("user move to the login url")
	public void user_move_to_the_login_url() throws InterruptedException {

		try {

			driver.get("https://www.amazon.in/");
			Thread.sleep(3000);
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-logo-sprites")));
		} catch (Exception e) {
			driver.quit();
		}
	}

	@When("user check login page is loaded properly or not")
	public void user_check_login_page_is_loaded_properly_or_not() throws InterruptedException {
		WebElement account = driver.findElement(By.id("nav-link-accountList"));
		utilfactory.highlight(account, driver);
		account.click();
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("return document.readyState").toString().equals("complete");
		Thread.sleep(3000);
		Assert.assertTrue(driver.findElement(By.xpath("//h1[contains(text(),'Sign in')]")).isDisplayed());
	}

	@When("user enter username and password")
	public void user_enter_username_and_password(DataTable dataTable) throws InterruptedException {

		List<Map<String, String>> user = dataTable.asMaps(String.class, String.class);

		String userName = user.get(0).get("username");
		String Password = user.get(0).get("password");
		WebElement username = driver.findElement(By.name("email"));
		utilfactory.highlight(username, driver);
		Thread.sleep(2000);
		username.sendKeys(userName);
		WebElement Continue = driver.findElement(By.id("continue"));
		utilfactory.highlight(Continue, driver);
		Continue.click();
		Thread.sleep(3000);
		WebElement password = driver.findElement(By.name("password"));
		Thread.sleep(2000);
		utilfactory.highlight(password, driver);
		password.sendKeys(Password);
		Thread.sleep(2000);
	}

	@Then("user clicks on login button")
	public void user_clicks_on_login_button() throws InterruptedException {
		WebElement loginButton = driver.findElement(By.id("signInSubmit"));
		Thread.sleep(2000);
		utilfactory.highlight(loginButton, driver);
		loginButton.click();
		Thread.sleep(4000);
	}

	@Then("user should redirect to home page")
	public void user_should_redirect_to_dashboard() {
		
//		Assert.assertTrue(driver.getCurrentUrl().equalsIgnoreCase("https://www.amazon.in/?ref_=nav_signin"));
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-logo-sprites")));
	}
	
	@Then("user logged out from the system")
	public void user_user_logged_out_from_the_system() throws InterruptedException {
		WebElement close = driver.findElement(By.id("attach-close_sideSheet-link"));
		utilfactory.highlight(close, driver);
		close.click();
		Thread.sleep(3000);
		WebElement accountList = driver.findElement(By.id("nav-link-accountList"));
		utilfactory.highlight(accountList, driver);
		Actions action = new Actions(driver);
		action.moveToElement(accountList).perform();
		WebElement signOut = driver.findElement(By.id("nav-item-signout"));
		utilfactory.highlight(signOut, driver);
		signOut.click();
	}
	
	@Then("user search for below item")
	public void user_search_for_below_item(DataTable dataTable) throws InterruptedException {
		List<List<String>> rows = dataTable.asLists(String.class);
		String item = rows.get(0).get(1);
		WebElement searchtextbox = driver.findElement(By.id("twotabsearchtextbox"));
		utilfactory.highlight(searchtextbox, driver);
		searchtextbox.sendKeys(item);
		WebElement searchButton = driver.findElement(By.id("nav-search-submit-button"));
		utilfactory.highlight(searchButton, driver);
		searchButton.click();
		Thread.sleep(5000);
		
	}


	@Then("user click on the product")
	public void user_click_on_product() throws InterruptedException {
		Thread.sleep(2000);
		WebElement firstItem = driver.findElement(By.xpath("((//span[contains(text(),'RESULTS')])[1]//ancestor::div//a[@class='a-link-normal s-underline-text s-underline-link-text s-link-style a-text-normal'])[1]"));
		utilfactory.highlight(firstItem, driver);
		Thread.sleep(2000);
		firstItem.click();
	}

	@Then("user add the given number of quanity")
	public void user_add_the_given_number_of_quanity(DataTable dataTable) throws InterruptedException {
		List<List<String>> rows = dataTable.asLists(String.class);
		String quantity = rows.get(0).get(1);
		final ArrayList<String> tabs1 = new ArrayList<String>(driver.getWindowHandles());
        driver.switchTo().window((String)tabs1.get(1));
        Thread.sleep(4000);
        Select quantityDropDown = new Select(driver.findElement(By.xpath("//select[@name='quantity']")));
        quantityDropDown.selectByVisibleText(quantity);
     }

	@Then("user click on add to cart button")
	public void user_clicks_on_add_to_cart() throws InterruptedException {
		Thread.sleep(2000);
		WebElement addToCart = driver.findElement(By.id("add-to-cart-button"));
		utilfactory.highlight(addToCart, driver);
		addToCart.click();
		Thread.sleep(2000);
	}

	
	@Then("user close the driver")
	public void user_close_the_driver() {
		driver.quit();
	}
	
}
