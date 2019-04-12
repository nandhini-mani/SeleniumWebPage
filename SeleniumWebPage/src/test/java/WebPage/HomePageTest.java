package WebPage;

import org.testng.annotations.Test;

import commonFunctions.CommonFunc;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.BeforeTest;

import java.io.IOException;
import java.io.File;
import java.io.FileInputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.apache.commons.*;
import org.apache.commons.io.FileUtils;

public class HomePageTest extends CommonFunc {
	public WebDriver driver;
	public CommonFunc snapshot ;
	public static String screenshotPath = "C://selenium-java-3.141.59/Output/Screenshots/";
	//JavascriptExecutor js;
	Properties prop;
	public static int count =0;
/*	
 * Test case 1 : To verify the hyperlinks present in Selenium Home page	 
 */
	@Test(priority=0)
  public void VerifyHomePageLinks(){
		String url ="";
		driver.get("https://www.seleniumhq.org/");
		
		HttpURLConnection huc = null;
        int respCode = 200;
        List<WebElement> links = driver.findElements(By.tagName("a"));
        Iterator<WebElement> it = links.iterator();
      
      while(it.hasNext()){
          
          url = it.next().getAttribute("href");
          
          System.out.println(url);
      
          if(url == null || url.isEmpty()){
System.out.println("URL is either not configured for anchor tag or it is empty");
              continue;
          }
          
          if(!url.startsWith("https://www.seleniumhq.org/")){
              System.out.println("URL belongs to another domain, skipping it.");
              continue;
          }
          
          try {
              huc = (HttpURLConnection)(new URL(url).openConnection());
              
              huc.setRequestMethod("HEAD");
              
              huc.connect();
              
              respCode = huc.getResponseCode();
              
              if(respCode >= 400){
                  System.out.println(url+" is a broken link");
              }
              else{
                  System.out.println(url+" is a valid link");
              }
                  
          } catch (MalformedURLException e) {
              // TODO Auto-generated catch block
              e.printStackTrace();
          } catch (IOException e) {
              // TODO Auto-generated catch block
              e.printStackTrace();
          }
  }
	}
/* Test case 2 : To verfiy the search option on top right corner of Selenium Webpage
 */
	@Test(priority=1)
	 @Parameters("browser") 
	public void VerifySearchOption(String browser) throws InterruptedException, IOException{
		FileInputStream file1 = new FileInputStream(System.getProperty("user.dir")+"\\object.properties");
		prop.load(file1);
		driver.get("https://www.seleniumhq.org/");
		System.out.println("URL opened");
		driver.manage().timeouts().implicitlyWait(10,TimeUnit.SECONDS) ;
		//Close the Ticker present at the top
		if(driver.findElement(By.id("promo")).isDisplayed()){
			driver.findElement(By.id("close")).click();
		}
		driver.manage().timeouts().implicitlyWait(10,TimeUnit.SECONDS) ;
		System.out.println("waited");
		
		//Scroll to the bottom of the webpage
		JavascriptExecutor js = ((JavascriptExecutor)driver);
		Thread.sleep(3000);
		js.executeScript("window.scrollTo(0, document.body.scrollHeight)");
		Thread.sleep(5000);
		//System.out.println("waited123");
		//Scroll to the top of the page
		js.executeScript("window.scrollTo(0, -document.body.scrollHeight)");
		Thread.sleep(5000);
		snapshot.Screenshot(driver, screenshotPath +"OpenUrl_"+browser+".png",browser);
		//System.out.println("Took snapshot");
		driver.findElement(By.id(prop.getProperty("searchTextBox"))).sendKeys("Webdriver");
		snapshot.Screenshot(driver, screenshotPath +"EnterText_"+browser+".png",browser);
		driver.findElement(By.xpath(prop.getProperty("searchGoButton"))).click();
		if(browser.equalsIgnoreCase("firefox")){
			System.out.println("Handling alert message present in firefox browser");
			Alert alert = driver.switchTo().alert();
			alert.accept();
			System.out.println("Accepted alert message");
		}
		Thread.sleep(5000);
		String expectedTitle = "Google Custom Search";
		String actualTitle = driver.getTitle();
		snapshot.Screenshot(driver, screenshotPath +"GoogleSearch_"+browser+".png",browser);
		Assert.assertEquals(actualTitle, expectedTitle);
		
	}
	
	
	
  @BeforeTest
  @Parameters("browser") 
  public void beforeTest(String browser) throws IOException {
	  	prop = new Properties();
		if(browser.equalsIgnoreCase("Chrome")){
			//System.setProperty("webdriver.chrome.driver", "C:\\selenium-java-3.141.59\\Drivers\\chromedriver.exe");
			//driver =new ChromeDriver();
	  		ChromeOptions options = new ChromeOptions();
	  		options.setCapability("platform", Platform.WINDOWS);
			driver = new RemoteWebDriver(new URL("http://192.168.43.140:5566/wd/hub"),options);
			driver.manage().window().maximize();
			count++;
		}else if(browser.equalsIgnoreCase("IE")){
			//System.setProperty("webdriver.ie.driver", "C:\\selenium-java-3.141.59\\Drivers\\IEDriverServer.exe");
			//driver =new InternetExplorerDriver();
			InternetExplorerOptions options = new InternetExplorerOptions();
			options.setCapability("platform", Platform.WINDOWS);
			driver = new RemoteWebDriver(new URL("http://192.168.43.140:5566/wd/hub"),options);	
			count++;
		}else if(browser.equalsIgnoreCase("Firefox")){
			//DesiredCapabilities capability = DesiredCapabilities.firefox();
			//capability.setBrowserName("firefox");
			//capability.setPlatform(Platform.VISTA);
			//System.setProperty("webdriver.gecko.driver", "C:\\selenium-java-3.141.59\\Drivers\\geckodriver.exe");
			//driver =new FirefoxDriver();
			FirefoxOptions options = new FirefoxOptions();
			options.setCapability("platform", Platform.WINDOWS);
			driver = new RemoteWebDriver(new URL("http://192.168.43.140:5566/wd/hub"),options);
			count++;
			
		}
	  	 //js = ((JavascriptExecutor)driver);
		File f1 = new File(screenshotPath);
		if(count==1){
		FileUtils.cleanDirectory(f1);
		}
  }

  @AfterTest
  public void afterTest() {
	 
	  driver.close();
  }

}
