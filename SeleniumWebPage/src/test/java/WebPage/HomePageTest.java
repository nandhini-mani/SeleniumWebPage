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
		//driver.get("https://www.seleniumhq.org/");
		
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
		//FileInputStream file1 = new FileInputStream(System.getProperty("user.dir")+"\\object.properties");
		//prop.load(file1);
		//driver.get("https://www.seleniumhq.org/");
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
		snapshot.Screenshot(driver, screenshotPath +browser+"_OpenUrl"+".png",browser);
		//System.out.println("Took snapshot");
		driver.findElement(By.id(prop.getProperty("searchTextBox"))).sendKeys("Webdriver");
		snapshot.Screenshot(driver, screenshotPath +browser+"_EnterText"+".png",browser);
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
		snapshot.Screenshot(driver, screenshotPath +browser+"_GoogleSearch"+".png",browser);
		Assert.assertEquals(actualTitle, expectedTitle);
		
	}
	
	@Test(priority=2)
	@Parameters("browser")
	public void VerifyMenu_HomePage(String browser) throws IOException{
		
		for(int i=1;i<6;i++){
			driver.findElement(By.id(prop.getProperty("menu["+i+"]"))).click();
			driver.manage().timeouts().implicitlyWait(10,TimeUnit.SECONDS) ;
			String expectedTitle="";
			switch(i)
			{
				case 1 :
					expectedTitle = "Selenium Projects";
					break;
					
				case 2 :
					expectedTitle = "Downloads";
					break;
					
				case 3 :
					expectedTitle = "Selenium Documentation — Selenium Documentation";
					break;
					
				case 4 :
					expectedTitle = "Getting Help";
					break;
					
				case 5 :
					expectedTitle = "About Selenium";
					break;
					
			}	
			String actualTitle = driver.getTitle();
			snapshot.Screenshot(driver, screenshotPath +browser+"_Menu_"+i+".png",browser);
			Assert.assertEquals(actualTitle, expectedTitle);
			if(driver.findElement(By.id(prop.getProperty("menuText"))).isDisplayed()){
				System.out.println("Text displayed");
			}else{
				System.out.println("Text not present");
			}
			driver.navigate().back();
		}
		/*
		//Click on Projects tab/menu in Home page 
		driver.findElement(By.id(prop.getProperty("menuProjects"))).click();
		driver.manage().timeouts().implicitlyWait(10,TimeUnit.SECONDS) ;
		String expectedTitle = "Selenium Projects";
		String actualTitle = driver.getTitle();
		snapshot.Screenshot(driver, screenshotPath +browser+"_ProjectsMenu"+".png",browser);
		Assert.assertEquals(actualTitle, expectedTitle);
		//verify whether the text is present under projects
		if(driver.findElement(By.id(prop.getProperty("projectText"))).isDisplayed()){
			System.out.println("Text displayed under projects tab");
		}else{
			System.out.println("Text not present under projects tab");
		}
		driver.navigate().back();
		
		//Click on Download tab/menu in Home page
		driver.findElement(By.id(prop.getProperty("menuDownload"))).click();
		driver.manage().timeouts().implicitlyWait(10,TimeUnit.SECONDS) ;
		expectedTitle = "Downloads";
		actualTitle = driver.getTitle();
		snapshot.Screenshot(driver, screenshotPath +browser+"_DownloadsMenu"+".png",browser);
		Assert.assertEquals(actualTitle, expectedTitle);
		//verify whether the text is present under Downloads
		if(driver.findElement(By.id(prop.getProperty("downloadText"))).isDisplayed()){
			System.out.println("Text displayed under Download tab");
		}else{
			System.out.println("Text not present under Download tab");
		}
		driver.navigate().back();
		
		//Click on Documentation tab/menu in Home page 
				driver.findElement(By.id(prop.getProperty("menuDocumentation"))).click();
				driver.manage().timeouts().implicitlyWait(10,TimeUnit.SECONDS) ;
				expectedTitle = "Selenium Documentation — Selenium Documentation";
				actualTitle = driver.getTitle();
				snapshot.Screenshot(driver, screenshotPath +browser+"_DocumentationMenu"+".png",browser);
				Assert.assertEquals(actualTitle, expectedTitle);
				//verify whether the text is present under projects
				if(driver.findElement(By.id(prop.getProperty("documentText"))).isDisplayed()){
					System.out.println("Text displayed under Documentation tab");
				}else{
					System.out.println("Text not present under Documentation tab");
				}
				driver.navigate().back();
				
				//Click on Support tab/menu in Home page 
				driver.findElement(By.id(prop.getProperty("menuSupport"))).click();
				driver.manage().timeouts().implicitlyWait(10,TimeUnit.SECONDS) ;
				expectedTitle = "Getting Help";
				actualTitle = driver.getTitle();
				snapshot.Screenshot(driver, screenshotPath +browser+"_SupportMenu"+".png",browser);
				Assert.assertEquals(actualTitle, expectedTitle);
				//verify whether the text is present under projects
				if(driver.findElement(By.id(prop.getProperty("supportText"))).isDisplayed()){
					System.out.println("Text displayed under Support tab");
				}else{
					System.out.println("Text not present under Support tab");
				}
				driver.navigate().back();
				
				//Click on About tab/menu in Home page 
				driver.findElement(By.id(prop.getProperty("menuAbout"))).click();
				driver.manage().timeouts().implicitlyWait(10,TimeUnit.SECONDS) ;
				expectedTitle = "About Selenium";
				actualTitle = driver.getTitle();
				snapshot.Screenshot(driver, screenshotPath +browser+"_AboutMenu"+".png",browser);
				Assert.assertEquals(actualTitle, expectedTitle);
				//verify whether the text is present under projects
				if(driver.findElement(By.id(prop.getProperty("aboutText"))).isDisplayed()){
					System.out.println("Text displayed under About tab");
				}else{
					System.out.println("Text not present under About tab");
				}
				driver.navigate().back();
		*/		
	}
	
	@Test(priority=3)
	@Parameters("browser")
	public void Verify_Footer(String browser) throws IOException{
				int count = 4;
				//Verify the presence of Footer at the bottom
				Assert.assertTrue(driver.findElement(By.id(prop.getProperty("footer"))).isDisplayed());
				snapshot.Screenshot(driver, screenshotPath +browser+"_VerifyFooter"+".png",browser);
				for (int i=1; i<=4;i++){
					Assert.assertTrue((driver.findElement(By.xpath(prop.getProperty("footerSubHead["+i+"]")))).isDisplayed(),"Footer subheadings are displayed");
					String s = driver.findElement(By.xpath(prop.getProperty("footerSubHead["+i+"]"))).getText();
					if(s.equalsIgnoreCase("Documentation")){
							count = 3;
					}else{
						count=4;
					}
					for(int j =1; j<=count;j++){
						//System.out.println("HELLO"+i+" "+j);
						Assert.assertTrue((driver.findElement(By.xpath(prop.getProperty("footerSubHead["+i+"]["+j+"]")))).isDisplayed(),s+" subheadings are displayed");
					}
				}
				
		}

	@Test(priority=4)
	@Parameters("browser")
	public void Verify_sidebar(String browser) throws IOException{
		//Verify the side bar present in home page, Projects page and Support page
		Assert.assertTrue(driver.findElement(By.xpath(prop.getProperty("sidebar"))).isDisplayed());
		for(int i= 1; i<5;i++){
			driver.findElement(By.id(prop.getProperty("menu["+i+"]"))).click();
			Assert.assertTrue(driver.findElement(By.xpath(prop.getProperty("sidebar"))).isDisplayed());
			snapshot.Screenshot(driver, screenshotPath +browser+"_VerifySidebar"+".png",browser);
			//System.out.println("sidebar"+i);
			if(i==1)
				i=3;
		}
	}
	
	@Test(priority=5)
	@Parameters("browser")
	public void EditThisPageLink(String browser) throws IOException{
		driver.findElement(By.xpath(prop.getProperty("editThisPage"))).click();
		String expectedTitle = "Sign in to GitHub · GitHub";
		String actualTitle = driver.getTitle();
		snapshot.Screenshot(driver, screenshotPath +browser+"_EditThisPage"+".png",browser);
		Assert.assertEquals(actualTitle, expectedTitle);
	}
	
  @BeforeTest
  @Parameters("browser") 
  public void beforeTest(String browser) throws IOException {
	  	prop = new Properties();
	  	FileInputStream file1 = new FileInputStream(System.getProperty("user.dir")+"\\object.properties");
		prop.load(file1);
		if(browser.equalsIgnoreCase("Chrome")){
			//System.setProperty("webdriver.chrome.driver", "C:\\selenium-java-3.141.59\\Drivers\\chromedriver.exe");
			//driver =new ChromeDriver();
	  		ChromeOptions options = new ChromeOptions();
	  		options.setCapability("platform", Platform.WINDOWS);
			driver = new RemoteWebDriver(new URL("http://192.168.43.140:5566/wd/hub"),options);
			driver.manage().window().maximize();
			count++;
			//driver.get("https://www.seleniumhq.org/");
		}else if(browser.equalsIgnoreCase("IE")){
			//System.setProperty("webdriver.ie.driver", "C:\\selenium-java-3.141.59\\Drivers\\IEDriverServer.exe");
			//driver =new InternetExplorerDriver();
			InternetExplorerOptions options = new InternetExplorerOptions();
			options.setCapability("platform", Platform.WINDOWS);
			driver = new RemoteWebDriver(new URL("http://192.168.43.140:5566/wd/hub"),options);	
			count++;
			//driver.get("https://www.seleniumhq.org/");
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
			//driver.get("https://www.seleniumhq.org/");
		}
		driver.get("https://www.seleniumhq.org/");
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
