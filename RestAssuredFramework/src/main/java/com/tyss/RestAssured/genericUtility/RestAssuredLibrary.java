package com.tyss.RestAssured.genericUtility;

import io.restassured.response.Response;

/**
 * It consistes methods of restAssured
 */
public class RestAssuredLibrary {
	
	/**
	 * This method is used to get the JSON data
	 * @param response
	 * @param path
	 * @return jSONData
	 */
	
	public String getJSONData(Response response, String path) {
		String jSONData = response.jsonPath().get(path);
		return jSONData;
	}

}
