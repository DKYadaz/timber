package testCase;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import utility.ExcelUtils;

public class TestCaseClassOne {

	private String sTestCaseName;
	private int iTestCaseRow;
	static WebDriver driver;

	

	@Test(dataProvider = "Authentication")

	public void UploadImgFile(String sUserName, String sPassword) throws InterruptedException, IOException {
		
		navigateToFile(sUserName, sPassword);

		upload(element("//form[@id='file_manager_dropzone']"),element("//a[@class='active'][contains(text(),'Files')]"), element("//a[contains(text(),'Add Images')]"));
		//element("//a[@class='active'][contains(text(),'Files')]").click();
		List<WebElement> ListElements = elementList("//ul[@id='js-file-manager-results']");
		
		System.out.println(ListElements.size() + "Doc is Uploaded");
		

	} 
	

	@Test(dataProvider = "Authentication")

	public void deleteImgFile(String sUserName, String sPassword) throws InterruptedException, IOException {
		
		navigateToFile(sUserName, sPassword);

		upload(element("//form[@id='file_manager_dropzone']"),element("//a[@class='active'][contains(text(),'Files')]"), element("//a[contains(text(),'Add Images')]"));
		//element("//a[@class='active'][contains(text(),'Files')]").click();
		List<WebElement> ListElements = elementList("//ul[@id='js-file-manager-results']");
		
		System.out.println(ListElements.size() + "Doc is Uploaded");
		
		for(WebElement we : ListElements) {
			element("//ul[@id='js-file-manager-results']//li[1]//section[6]//a[1]").click();
		}
		
		

	} 
	
	

	public void navigateToFile(String sUserName, String sPassword) throws InterruptedException, IOException {

		//String currentDir = System.getProperty("user.dir");

		element("//input[@id='user']").click();

		element("//input[@id='user']").sendKeys(sUserName);

		element("//input[@id='password']").click();

		element("//input[@id='password']").sendKeys(sPassword);

		element("//input[@value='Login']").click();

		if( element("//a[contains(text(),'Dashboard')]").isDisplayed()) {
			System.out.println("sucesfully LogedIn");
		}

		element("//ul//a[contains(text(),'Files')]").click();

		element("//a[contains(text(),'Add Images')]").click();

		element("//form[@id='file_manager_dropzone']").click();
	}


	@DataProvider

	public Object[][] Authentication() throws Exception{

		ExcelUtils.setExcelFile(System.getProperty("user.dir")+"\\src\\test\\java\\testData\\TestData.xlsx","LogInData");
		sTestCaseName = this.toString();

		sTestCaseName = ExcelUtils.getTestCaseName(this.toString());

		iTestCaseRow = ExcelUtils.getRowContains(sTestCaseName,0);
		Object[][] testObjArray = ExcelUtils.getTableArray(System.getProperty("user.dir")+"\\src\\test\\java\\testData\\TestData.xlsx","LogInData", iTestCaseRow);
		return (testObjArray);
	}
	
	@BeforeMethod

	public void beforeMethod() throws Exception {

		System.setProperty("webdriver.gecko.driver", System.getProperty("user.dir")+"\\src\\test\\java\\drivers\\geckodriver.exe");
		driver = new FirefoxDriver();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		driver.get("https://demo.bigtreecms.org/admin/login/");	 

	}

	@AfterMethod

	public void afterMethod() {
		driver.close();
	}

	public WebElement element(String xpath) {
		WebDriverWait wait=new WebDriverWait(driver, 20);
		return wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
	}
	
	public List<WebElement> elementList(String xpath) {
		WebDriverWait wait=new WebDriverWait(driver, 20);
		return (List<WebElement>) wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
	}

	public void upload(WebElement element, WebElement element2, WebElement element3 ) throws InterruptedException, IOException {
		File file = new File(System.getProperty("user.dir")+"\\src\\test\\java\\files\\");
		File[] files = file.listFiles();
		for(File f: files){

			String filename = f.getName();
			element.click();

			Thread.sleep(3000);
			//Runtime.getRuntime().exec(System.getProperty("user.dir")+"\\src\\test\\java\\utility\\FileUpload.exe"+","+System.getProperty("user.dir")+"\\src\\test\\java\\files\\" +filename);
			uploadFileWithRobot(filename);

			Thread.sleep(3000);
			element("//input[@value='Continue']").click();
			Thread.sleep(3000);
			//element2.click();
			//element3.click();
		}

	}

	public void uploadFileWithRobot (String fileName) {
		StringSelection stringSelection = new StringSelection(System.getProperty("user.dir")+"\\src\\test\\java\\files\\"+fileName);
		Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
		clipboard.setContents(stringSelection, null);

		Robot robot = null;

		try {
			robot = new Robot();
		} catch (AWTException e) {
			e.printStackTrace();
		}

		robot.delay(250);

		robot.keyPress(KeyEvent.VK_ENTER);
		robot.keyRelease(KeyEvent.VK_ENTER);
		robot.keyPress(KeyEvent.VK_CONTROL);
		robot.keyPress(KeyEvent.VK_V);
		robot.keyRelease(KeyEvent.VK_V);
		robot.keyRelease(KeyEvent.VK_CONTROL);
		robot.keyPress(KeyEvent.VK_ENTER);
		robot.delay(150);
		robot.keyRelease(KeyEvent.VK_ENTER);
	}

}
