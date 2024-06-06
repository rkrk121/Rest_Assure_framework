package APIRequestValidations;

import static io.restassured.RestAssured.*;

import java.sql.SQLException;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.tyss.RestAssured.genericUtility.BaseClass;
import com.tyss.RestAssured.genericUtility.EndPointsLibrary;
import POJOPackage.PojoClass;
import io.restassured.response.Response;

public class CreateProjectAPI extends BaseClass {
	
	@Test
	public void createProject() throws SQLException {
		
		PojoClass pObj = new PojoClass("Abhay_kr", "EazyWhiz12113"+jLib.randomNumber(), "Ongoing", 9);
				
		
		Response res = given().spec(reqst).body(pObj).post(EndPointsLibrary.CREATEPROJECT);
		
		String expData = rLib.getJSONData(res, "projectId");
		System.out.println("Expected data :"+ expData);
		
		res.then().log().all();
		
		
		String query="select * from project;";
		String actData = dbLibrary.readDataFromDBAndVerify(query, 1, expData);
		Assert.assertEquals(actData, expData);
		
		
	}

}
