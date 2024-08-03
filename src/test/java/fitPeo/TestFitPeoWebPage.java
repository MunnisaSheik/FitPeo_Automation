package fitPeo;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;
import junit.framework.Assert;

public class TestFitPeoWebPage {

	private WebDriver driver;
	private JavascriptExecutor js;
	private String Header;
	private int i=1;
	private Point point;

	@BeforeMethod
	public void setup() {
		
		/*
		 * Launching the chromewebdriver using webdrivermanager dependencies
		 * instead of  using .exe file 
		 */
		WebDriverManager.chromedriver().setup();
		driver=new ChromeDriver();
		driver.manage().window().maximize();
		
	}

	@Test
	public void  task() throws InterruptedException {
		
		//Task-1 : Navigate to the FitPeo Homepage
		driver.get("https://www.fitpeo.com/");  //1
		
		//Task-2 : Navigate to the Revenue Calculator Page
		driver.findElement(By.xpath("//div[text()='Revenue Calculator']")).click(); //2

		Thread.sleep(3000);

		//Task-3 : Scroll Down to the Slider section
		/*
		 * Using JavascriptExecutor interface 
		 */
		js=(JavascriptExecutor) driver; //3
		js.executeScript("window.scrollBy(0,300)");


		//Task-4 : Adjust the Slider
		/*
		 * using Actions class - finding the nearest x,y points to 820 value and using keys action
		 * found the 823 value so using left arrow key --set the value to 820 
		 */
		Actions act=new Actions(driver);
		WebElement source = driver.findElement(By.xpath("//input[@aria-valuenow='200']"));
		point = source.getLocation();
		System.out.println(point.getX()+" "+point.getY()); // 803,654
		act.clickAndHold(source).moveToLocation(907,654).release().perform(); 
		while(i<=3) {

			source.sendKeys(Keys.ARROW_LEFT);
			i++;
		}
		js.executeScript("window.scrollBy(0,300)");
		Thread.sleep(1000);
		WebElement ele = driver.findElement(By.xpath("//*[@id=\":r0:\"]"));
		String input=ele.getAttribute("value");
		Assert.assertEquals("820", input); 

		//Task-5 :Update the Text Field
		/*
		 * ele.clear()- didn't work as it is still pointing to 820 
		 */
		ele.sendKeys(Keys.BACK_SPACE);
		ele.sendKeys(Keys.BACK_SPACE);
		ele.sendKeys(Keys.BACK_SPACE);
		ele.sendKeys("560");
		Thread.sleep(3000);
		
		//Task-6:Validate Slider Value
		String in= driver.findElement(By.xpath("//input[@aria-valuenow='560']")).getAttribute("aria-valuenow");
		Assert.assertEquals(in,"560");

		//Task-7 :Select CPT Codes
		/*
		 * for 820 people , calculating  total Recurring Reimbursement
		 */
		ele.sendKeys(Keys.BACK_SPACE);
		ele.sendKeys(Keys.BACK_SPACE);
		ele.sendKeys(Keys.BACK_SPACE);
		ele.sendKeys("820");
		js.executeScript("window.scrollBy(0,300)");
		driver.findElement(By.xpath("(//input[@class='PrivateSwitchBase-input css-1m9pwf3'])[1]")).click();
		driver.findElement(By.xpath("(//input[@class='PrivateSwitchBase-input css-1m9pwf3'])[2]")).click();
		driver.findElement(By.xpath("(//input[@class='PrivateSwitchBase-input css-1m9pwf3'])[3]")).click();
		js.executeScript("window.scrollBy(0,300)");
		driver.findElement(By.xpath("(//input[@class='PrivateSwitchBase-input css-1m9pwf3'])[8]")).click();

		//Task-8&9:Validate Total Recurring Reimbursement
		Header=driver.findElement(By.xpath("//div[@class='MuiToolbar-root MuiToolbar-gutters MuiToolbar-regular css-1lnu3ao']/p[4]")).getText();
	  System.out.println(Header);
		Assert.assertTrue(Header.equals("Total Recurring Reimbursement for all Patients Per Month:\n$110700"));

	}

	@AfterMethod
	public void tearDown() {
		driver.close();
	}




}
