package com.tyss.RestAssured.genericUtility;

import java.sql.SQLException;

import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class BaseClass { 
	
	public DatabaseLibrary dbLibrary=new DatabaseLibrary();
	public RestAssuredLibrary rLib= new RestAssuredLibrary();
	public JavaLibrary jLib= new JavaLibrary();
	public RequestSpecification reqst;
	public ResponseSpecification resp;
	
	@BeforeSuite
	public void dataBaseConnect() throws SQLException {
		dbLibrary.connectToDB();
		
		reqst = new RequestSpecBuilder().setBaseUri("http://rmgtestingserver:8084")
		.setContentType(ContentType.JSON).build();
		
		resp = new ResponseSpecBuilder().expectContentType(ContentType.JSON).build();
	}
	
	
	@AfterSuite
	public void closeDBConnection() throws SQLException {
		dbLibrary.closeDBConnection();
	}

}
