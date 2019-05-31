package BasicModulePackage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Parameters;

public class ForgotPassword {

	public static WebDriver driver = null;

	@Parameters({ "browser", "email"})
	@BeforeSuite(alwaysRun = true)
	public void launchbrowser(String browser, String email) throws Exception {
		if (browser.equals("firefox")) {
			System.setProperty("webdriver.gecko.driver", "D://downloads/geckodriver.exe");
			System.out.println("firefox launching");
			driver = new FirefoxDriver();
		} else {
			System.setProperty("webdriver.chrome.driver", "D://downloads/chromedriver.exe");
			System.out.println("chrome launching");
			driver = new ChromeDriver();
		}

		String baseurl = "http://kwikhire-dev.s3-website.ap-south-1.amazonaws.com/login";
		driver.manage().window().maximize();
		driver.get(baseurl);
		Thread.sleep(4000);
		driver.findElement(By.xpath("//*[@id=\"app\"]/div/div/div[2]/form/div[1]/div[3]/a")).click();
		System.out.println("clicked on forgot password");
		Thread.sleep(2000);
		driver.findElement(By.id("forgotPasswordEmail")).sendKeys(email);
		System.out.println("sending email " +email);
		driver.findElement(By.xpath("//*[@id=\"app\"]/div/div/div[2]/form/div[3]/button")).click();

		Thread.sleep(3000);
		WebElement successmsg = null;
		WebElement readerror1 = null;
		WebElement readerror2 = null;
		if (driver.findElements(By.xpath("//*[@id=\"app\"]/div/div/div[2]/div/p/span[1]")).size() != 0) {
			successmsg = driver.findElement(By.xpath("//*[@id=\"app\"]/div/div/div[2]/div/p/span[1]"));
			if(successmsg!=null) {
			String msg= driver.findElement(By.xpath("//*[@id=\"app\"]/div/div/div[2]/div/p/span[1]")).getText();
			System.out.println(msg);
			}

		}
		else {
			
			//		read the error msg if present	
			if (driver.findElements(By.xpath("//*[@id=\"app\"]/div/div/div[2]/form/div[2]/div/p")).size() != 0) {
				readerror1 = driver.findElement(By.xpath("//*[@id=\"app\"]/div/div/div[2]/form/div[2]/div/p"));
			}
			if (driver.findElements(By.xpath("//*[@id=\"app\"]/div/div/div[2]/form/div[1]/p")).size() != 0) {
				readerror2 = driver.findElement(By.xpath("//*[@id=\"app\"]/div/div/div[2]/form/div[1]/p"));
			}
			//		Empty/invalid email		
			if (readerror1 != null) {
				String ErrorMessage1 = driver
						.findElement(By.xpath("//*[@id=\"app\"]/div/div/div[2]/form/div[2]/div/p")).getText();
				if (ErrorMessage1.contains("E-mail cannot be blank")) {
					System.out.println("E-mail cannot be blank");
				} else if (ErrorMessage1.contains("E-mail format is wrong")) {
					System.out.println("E-mail format is wrong");
				}
			}
			if (readerror2 != null) {
				String ErrorMessage1 = driver
						.findElement(By.xpath("//*[@id=\"app\"]/div/div/div[2]/form/div[1]/p")).getText();
				//			wrong Email 
				if (ErrorMessage1.contains("E-Mail ID is not found in our database")) {
					System.out.println("E-Mail ID is not found in our database");
				}
				else if(ErrorMessage1.contains("Error occured while sending password reset link")) {
					System.out.println("Error occured while sending password reset link");
				}
			}
		}
	}
}