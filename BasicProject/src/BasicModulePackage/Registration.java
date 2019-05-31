package BasicModulePackage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Parameters;

public class Registration {
	public static WebDriver driver = null;
	
	@BeforeSuite(alwaysRun = true)
	@Parameters({ "browser", "email", "pass" ,"cpass" })
	public void launchbrowser(String browser, String email, String password, String cpass) throws Exception {
		if (browser.equals("firefox")) {
			System.setProperty("webdriver.gecko.driver", "D://downloads/geckodriver.exe");
			System.out.println("firefox launching");
			driver = new FirefoxDriver();
		} else {
			System.setProperty("webdriver.chrome.driver", "D://downloads/chromedriver.exe");
			System.out.println("chrome launching");
			driver = new ChromeDriver();
		}

		String baseurl = "http://kwikhire-dev.s3-website.ap-south-1.amazonaws.com/";
		driver.manage().window().maximize();
		driver.get(baseurl);
		Thread.sleep(4000);
//		 Registration
		System.out.println("In Registration Page");
		driver.findElement(By.xpath("//*[@id=\"app\"]/div/div/div[1]/div/a[3]")).click();
//		email
		driver.findElement(By.id("signupEmail")).clear();
		driver.findElement(By.id("signupEmail")).sendKeys(email);
		System.out.println("sending email " +email);
		Thread.sleep(2000);
//		password
		driver.findElement(By.xpath("//*[@id=\"app\"]/div/div/div[2]/div[4]/input")).clear();
		driver.findElement(By.xpath("//*[@id=\"app\"]/div/div/div[2]/div[4]/input")).sendKeys(password);
		System.out.println("sending password " );
		Thread.sleep(2000);
//		 re-enter password
		driver.findElement(By.xpath("//*[@id=\"app\"]/div/div/div[2]/div[5]/input")).clear();
		driver.findElement(By.xpath("//*[@id=\"app\"]/div/div/div[2]/div[5]/input")).sendKeys(cpass);
		System.out.println("re-enter password " +cpass);
//		Sign up
		driver.findElement(By.xpath("//*[@id=\"app\"]/div/div/div[2]/form/button")).click();
		System.out.println("signup");

		WebElement successmsg = null;
		if (driver.findElements(By.xpath("//*[@id=\"app\"]/div/div/div[2]/div[3]/p")).size() != 0) {
			successmsg = driver.findElement(By.xpath("//*[@id=\"app\"]/div/div/div[2]/div/p/span[1]"));
			if(successmsg!=null) {
				String msg= driver.findElement(By.xpath("//*[@id=\"app\"]/div/div/div[2]/div/p/span[1]")).getText();
				System.out.println(msg);
			}

		}
		else {
			WebElement readerror1 = null;

//					read the error msg if present	
			if (driver.findElements(By.xpath("//*[@id=\"app\"]/div/div/div[2]/div[3]/p")).size() != 0) {
				readerror1 = driver.findElement(By.xpath("//*[@id=\"app\"]/div/div/div[2]/div[3]/p"));
			}

//					Empty/invalid email		
			if (readerror1 != null) {
				String ErrorMessage1 = driver
						.findElement(By.xpath("//*[@id=\"app\"]/div/div/div[2]/div[3]/p")).getText();
				if (ErrorMessage1.contains("E-mail cannot be blank")) {
					System.out.println("E-mail cannot be blank");
				} else if (ErrorMessage1.contains("E-mail format is wrong")) {
					System.out.println("E-mail format is wrong");
				}
			}
			WebElement readerror2 = null;
//			password
			if (driver.findElements(By.xpath("//*[@id=\"app\"]/div/div/div[2]/div[4]/p")).size() != 0) {
				readerror2 = driver.findElement(By.xpath("//*[@id=\"app\"]/div/div/div[2]/div[4]/p"));
			}
			if (readerror2 != null) {
				String ErrorMessage = driver
						.findElement(By.xpath("//*[@id=\"app\"]/div/div/div[2]/div[4]/p")).getText();
				if (ErrorMessage.contains("Password cannot be blank")) {
					System.out.println("Password cannot be blank");
				}
				else if (ErrorMessage.contains("Password length should be more than 8 letters")) {
					System.out.println("Password length should be more than 8 letters");
				}

			}
//			password policy
			WebElement readerror3 = null;
			if (driver.findElements(By.xpath("//*[@id=\"app\"]/div/div/div[2]/div[2]")).size() != 0) {
				readerror3 = driver.findElement(By.xpath("//*[@id=\"app\"]/div/div/div[2]/div[2]"));
			}
			if(readerror3!=null) {
				String errormsg = driver.findElement(By.xpath("//*[@id=\"app\"]/div/div/div[2]/div[2]")).getText();
				if(errormsg.contains("Password and Confirm passwords should be same")) {
					System.out.println("Password and Confirm passwords should be same");
				}
				else if(errormsg.contains("Password must contain atleast a small letter, a capital letter and a number and should not contain spaces")) {
					System.out.println("Password must contain atleast a small letter, a capital letter and a number and should not contain spaces");
				}
			}
//			re enter password
			WebElement readerror4 = null;
			if (driver.findElements(By.xpath("//*[@id=\"app\"]/div/div/div[2]/div[5]/p")).size() != 0) {
				readerror4 = driver.findElement(By.xpath("//*[@id=\"app\"]/div/div/div[2]/div[5]/p"));
			}
			if(readerror4!=null) {
				String errormsg = driver.findElement(By.xpath("//*[@id=\"app\"]/div/div/div[2]/div[5]/p")).getText();
				if(errormsg.contains("Password cannot be blank")) {
					System.out.println("re enter Password cannot be blank");
				}
			}
		}
	}
	@AfterSuite
	public void afterSuite() throws Exception {
		Thread.sleep(3000);
		System.out.println("End of suite");

		// driver.quit();
	}
}

