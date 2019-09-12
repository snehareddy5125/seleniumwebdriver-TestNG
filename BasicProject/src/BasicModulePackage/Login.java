package BasicModulePackage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Parameters;

public class Login {

	public static WebDriver driver = null;
	//@Test
	@Parameters({ "browser", "email", "password" })
	@BeforeSuite(alwaysRun = true)
	public void launchbrowser(String browser, String email, String password) throws Exception {
		if (browser.equals("firefox")) {
			System.setProperty("webdriver.gecko.driver", "D://downloads/geckodriver.exe");
			System.out.println("firefox launching");
			driver = new FirefoxDriver();
		} else {
			System.setProperty("webdriver.chrome.driver", "D://downloads/chromedriver.exe");
			System.out.println("chrome launching");
			driver = new ChromeDriver();
		}

		String baseurl = "http://url";
		driver.manage().window().maximize();
		driver.get(baseurl);
		Thread.sleep(4000);
		// login
		System.out.println("In login page");
		driver.findElement(By.xpath("//*[@id=\"app\"]/div/div/div[1]/div/a[2]")).click();
		
		driver.findElement(By.id("loginEmail")).clear();
		driver.findElement(By.id("loginEmail")).sendKeys(email);
		System.out.println("sending email");
		Thread.sleep(2000);
		driver.findElement(By.xpath("//*[@id=\"app\"]/div/div/div[2]/form/div[1]/div[2]/input")).clear();
		driver.findElement(By.xpath("//*[@id=\"app\"]/div/div/div[2]/form/div[1]/div[2]/input")).sendKeys(password);
		System.out.println("sending password");
		Thread.sleep(3000);
		// login button
		driver.findElement(By.xpath("//*[@id=\"app\"]/div/div/div[2]/form/div[2]/button")).click();
		System.out.println("login");

		String url = driver.getCurrentUrl();
		System.out.println(url);

		Thread.sleep(3000);
		if (url.contains("http://url")) {
			// System.out.println("url passed");

			WebElement element1 = null;
			WebElement element2 = null;
			WebElement element3 = null;

//			Email error handling
			if (driver.findElements(By.xpath("//*[@id=\"app\"]/div/div/div[2]/form/div[1]/div[1]/p")).size() != 0) {
				element1 = driver.findElement(By.xpath("//*[@id=\"app\"]/div/div/div[2]/form/div[1]/div[1]/p"));
			}

//			Password error handling
			if (driver.findElements(By.xpath("//*[@id=\"app\"]/div/div/div[2]/form/div[1]/div[2]/p")).size() != 0) {
				element2 = driver.findElement(By.xpath("//*[@id=\"app\"]/div/div/div[2]/form/div[1]/div[2]/p"));
			}

//			Invalid credentials thing here
			if (driver.findElements(By.xpath("//*[@id=\"app\"]/div/div/div[2]/form/div[1]/p[1]/span")).size() != 0) {
				element3 = driver.findElement(By.xpath("//*[@id=\"app\"]/div/div/div[2]/form/div[1]/p[1]/span"));
			}



			if (element1 != null) {
				String ErrorMessage1 = driver
						.findElement(By.xpath("//*[@id=\"app\"]/div/div/div[2]/form/div[1]/div[1]/p")).getText();
				if (ErrorMessage1.contains("E-mail cannot be blank")) {
					System.out.println("Handling Invalid Inputs – E-mail cannot be blank");
				} else if (ErrorMessage1.contains("E-mail format is wrong")) {
					System.out.println("Handling Invalid Inputs – E-mail format is wrong");
				}
			}

			else if (element2 != null) {
				String ErrorMessage2 = driver
						.findElement(By.xpath("//*[@id=\"app\"]/div/div/div[2]/form/div[1]/div[2]/p")).getText();
				if (ErrorMessage2.contains("Password cannot be blank")) {
					System.out.println("Handling Invalid Inputs – Password cannot be blank");
				}
			}
			else if (element3 != null) {
				String ErrorMessage3 = driver
						.findElement(By.xpath("//*[@id=\"app\"]/div/div/div[2]/form/div[1]/p[1]/span")).getText();
				if (ErrorMessage3.contains("Invalid Credentials")) {
					System.out.println("Handling Invalid Inputs – Invalid Credentials");
				}

				else {
					System.out.println("Not Handling Invalid Inputs – test failed");
				}
			}

			else {
				System.out.println("Login Success");
			}
			System.out.println("\n");
		}

	}

	@AfterSuite
	public void afterSuite() throws Exception {
		Thread.sleep(3000);
		System.out.println("End of suite");
		Thread.sleep(3000);

		// driver.quit();
	}
}
