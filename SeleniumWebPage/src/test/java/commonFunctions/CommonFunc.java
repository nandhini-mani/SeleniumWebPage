package commonFunctions;

import java.io.File;
import java.io.IOException;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
//import org.apache.commons.io.FileUtils;

import com.google.common.io.Files;

public class CommonFunc {
	
	public static void Screenshot(WebDriver driver, String FilePath) throws IOException{
		TakesScreenshot s1 = ((TakesScreenshot)driver);
		File srcFile = s1.getScreenshotAs(OutputType.FILE);
		File destFile = new File(FilePath);
		//FileUtils.copyFile(srcFile,destFile);
		Files.copy(srcFile, destFile);
	}

}
