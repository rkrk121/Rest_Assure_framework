package endToEndRMGYantra;

import java.sql.SQLException;
import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.tyss.RestAssured.genericUtility.BaseClass;
import com.tyss.RestAssured.genericUtility.EndPointsLibrary;
import com.tyss.RestAssured.genericUtility.IPathConstatns;

import io.restassured.response.Response;

import static io.restassured.RestAssured.*;

public class ThreeLayerTest extends BaseClass {
	
	String projectName="EasyWhiz"+jLib.randomNumber();
	String projectId="TY_EW_"+jLib.randomNumber();
	@Test
	public void threeLayers() throws SQLException, InterruptedException {
		String query="insert into project values('"+projectId+"','Abhay1','08-05-2020', '"+projectName+"' ,'Ongiong','9');";
		dbLibrary.writeDataIntoDB(query);
		
		Response response = given().spec(reqst).when().get(EndPointsLibrary.GETSINGLEPROJECT + projectId);
		String expectedPID = rLib.getJSONData(response, "projectId");
		response.then().spec(resp).log().all();
		Assert.assertEquals(expectedPID, projectId);
		
		WebDriver driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		driver.get("http://rmgtestingserver:8084/");
		driver.findElement(By.id("usernmae")).sendKeys(IPathConstatns.APPUSERNAME);
		driver.findElement(By.id("inputPassword")).sendKeys(IPathConstatns.APPPASSWORD);
		driver.findElement(By.xpath("//button[.='Sign in']")).click();
		driver.findElement(By.xpath("//a[text()='Projects']")).click();
		Thread.sleep(2000);
		
		String actualPId = driver.findElement(By.xpath("//td[text()='" + projectName + "']/preceding-sibling::td"))
				.getText();
		
		Assert.assertEquals(expectedPID, actualPId);
		System.out.println("Test case passed");
		
		driver.quit();
		
		
	}
	

}
