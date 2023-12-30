package Maps;

import io.restassured.RestAssured;
import static io.restassured.RestAssured.*;

public class AddPlace {

	public static void main(String[] args) {
		//Rest Assured works on only 3 principle
		
		//given - take all input details(eg- queryParam, header,body,authorization,etc)
		//when - submit the API - it takes 2 input[resource, http method(get,post,put,....)]
		//then = validate the response
		
		//Step1: set base URI
		RestAssured.baseURI = "https://rahulshettyacademy.com";
		
		//step 2: need to import "import static io.restassured.RestAssured.*;" because given,when,then is static 
		// adding log().all()  - to see details on console
		given().log().all().queryParam("key", "qaclick123").header("Content-Type", "application/json")
		.body("{\r\n"
				+ "  \"location\": {\r\n"
				+ "    \"lat\": -38.383494,\r\n"
				+ "    \"lng\": 33.427362\r\n"
				+ "  },\r\n"
				+ "  \"accuracy\": 50,\r\n"
				+ "  \"name\": \"Jai Shree Ram\",\r\n"
				+ "  \"phone_number\": \"(+91) 983 893 3937\",\r\n"
				+ "  \"address\": \"29, side layout, cohen 09\",\r\n"
				+ "  \"types\": [\r\n"
				+ "    \"shoe park\",\r\n"
				+ "    \"shop\"\r\n"
				+ "  ],\r\n"
				+ "  \"website\": \"http://google.com\",\r\n"
				+ "  \"language\": \"French-IN\"\r\n"
				+ "}\r\n"
				+ "")
		.when().post("/maps/api/place/add/json")
		.then().log().all().assertThat().statusCode(200);

	}

}
