package endToEndRMGYantra;

import java.sql.SQLException;
import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.tyss.RestAssured.genericUtility.BaseClass;
import com.tyss.RestAssured.genericUtility.DatabaseLibrary;
import com.tyss.RestAssured.genericUtility.EndPointsLibrary;
import com.tyss.RestAssured.genericUtility.IPathConstatns;
import com.tyss.RestAssured.genericUtility.JavaLibrary;

import POJOPackage.PojoClass;
import io.restassured.response.Response;

import static io.restassured.RestAssured.*;

public class RMGYantraFullTest extends BaseClass {
//  JavaLibrary jLib= new JavaLibrary();
//  DatabaseLibrary dLib= new DatabaseLibrary();
	String projectName = "EAzyWhizzz" + jLib.randomNumber();

	@Test
	public void fullSDETRMGYantra() throws InterruptedException, SQLException {
		WebDriver driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		driver.get("http://rmgtestingserver:8084/");

		driver.findElement(By.id("usernmae")).sendKeys(IPathConstatns.APPUSERNAME);
		driver.findElement(By.id("inputPassword")).sendKeys(IPathConstatns.APPPASSWORD);
		driver.findElement(By.xpath("//button[.='Sign in']")).click();
		driver.findElement(By.xpath("//a[text()='Projects']")).click();
		Thread.sleep(2000);
		driver.findElement(By.xpath("//span[text()='Create Project']")).click();

		driver.findElement(By.name("projectName")).sendKeys(projectName);
		driver.findElement(By.name("createdBy")).sendKeys("Alex");
		WebElement dropDown = driver.findElement(By.name("status"));
		Select sel = new Select(dropDown);
		sel.selectByValue("Created");

		driver.findElement(By.xpath("//input[@value='Add Project']")).click();

		String projectId = driver.findElement(By.xpath("//td[text()='" + projectName + "']/preceding-sibling::td"))
				.getText();

		// API Request

		Response res = when().get("http://rmgtestingserver:8084" + EndPointsLibrary.GETSINGLEPROJECT + projectId);
		String expDta = rLib.getJSONData(res, "projectId");
		res.then().log().all();

		String qurey="select * from project;";
		dbLibrary.readDataFromDBAndVerify(qurey, 1, expDta);
		Assert.assertEquals(expDta, projectId);
		
		//UpdateThe project using API
		
		PojoClass pObj = new PojoClass("Abhay", projectName, "Onging", 22);
	Response response=given().spec(reqst).body(pObj).when().put(EndPointsLibrary.UPDATEPROJECT+projectId);
	
	response.then().spec(resp).log().all();
		
		
	}

}
