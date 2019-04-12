package commonFunctions;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.Augmenter;

import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;
import ru.yandex.qatools.ashot.shooting.ShootingStrategies;
import javax.imageio.ImageIO;
//import org.apache.commons.io.FileUtils;

import com.google.common.io.Files;

public class CommonFunc {
	
	public static void Screenshot(WebDriver driver, String filePath,String browser) throws IOException{
		//Screenshot fpScreenshot;
		//To capture full page screenshot
		if(browser.equalsIgnoreCase("IE")){
			 ((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight)");
         File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
         FileUtils.copyFile(scrFile, new File(filePath));
		}else{
			
			Screenshot fpScreenshot = new AShot().shootingStrategy(ShootingStrategies.viewportPasting(1000)).takeScreenshot(driver);
		    ImageIO.write(fpScreenshot.getImage(),"PNG",new File(filePath));
		}
		
		/*
		WebDriver augmentedDriver = new Augmenter().augment(driver);
		File screenFile = ((TakesScreenshot) augmentedDriver).getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(screenFile, new File(filePath));
		*/
	    //JavascriptExecutor js = ((JavascriptExecutor)driver);
	    //js.executeScript("window.scrollTo(0, -document.body.scrollHeight)");
	/*	TakesScreenshot s1 = ((TakesScreenshot)driver);
		File srcFile = s1.getScreenshotAs(OutputType.FILE);
		File destFile = new File(filePath);
		//FileUtils.copyFile(srcFile,destFile);
		Files.copy(srcFile, destFile);
		*/
	}

}
