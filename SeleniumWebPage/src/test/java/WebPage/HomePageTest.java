package WebPage;

import org.testng.annotations.Test;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.BeforeTest;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.testng.annotations.AfterTest;

public class HomePageTest {
	public WebDriver driver;
	 
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

	
  @BeforeTest
  @Parameters("browser") 
  public void beforeTest(String browser) {
	  	if(browser.equalsIgnoreCase("Chrome")){
			System.setProperty("webdriver.chrome.driver", "C:\\selenium-java-3.141.59\\Drivers\\chromedriver.exe");
			driver =new ChromeDriver();
			driver.manage().window().maximize();
		}else if(browser.equalsIgnoreCase("IE")){
			System.setProperty("webdriver.ie.driver", "C:\\selenium-java-3.141.59\\Drivers\\IEDriverServer.exe");
			driver =new InternetExplorerDriver();
			
		}else if(browser.equalsIgnoreCase("Firefox")){
			System.setProperty("webdriver.gecko.driver", "C:\\selenium-java-3.141.59\\Drivers\\geckodriver.exe");
			driver =new FirefoxDriver();
			
		}
	  
  }

  @AfterTest
  public void afterTest() {
	 
	  driver.close();
  }

}
