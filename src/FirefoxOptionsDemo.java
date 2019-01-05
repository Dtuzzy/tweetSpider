import java.io.File;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;

public class FirefoxOptionsDemo {

public static void main(String[] args) {
System.setProperty("webdriver.gecko.driver", "E:\\software and tools\\geckodriver.exe");
FirefoxProfile profile =new FirefoxProfile(new File("C:\\Users\\sys\\AppData\\Roaming\\Mozilla\\Firefox\\Profiles\\ejtrne37.QAProfile"));
FirefoxOptions option=new FirefoxOptions();
option.setProfile(profile);
// Initialize Firefox driver
WebDriver driver = new FirefoxDriver(option);
//Maximize browser window
driver.manage().window().maximize();
//Go to URL which you want to navigate
driver.get("http://www.google.com");
//Set  timeout  for 5 seconds so that the page may load properly within that time
driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
//close firefox browser
driver.close();
}
}