package com.tyss.RestAssured.genericUtility;

import java.util.Random;

public class JavaLibrary {
	
	public int randomNumber() {
		Random r = new Random();
		int ran = r.nextInt(500);
		return ran;
	}

}
