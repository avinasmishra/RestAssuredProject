package Maps;

import static io.restassured.RestAssured.*;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static org.hamcrest.Matchers.*;

import org.testng.Assert;

import Files.Payload;

public class SimplifyAddPlace {

	public static void main(String[] args) {
		//Rest Assured works on only 3 principle

		//given - take all input details(eg- queryParam, header,body,authorization,etc)
		//when - submit the API - it takes 2 input[resource, http method(get,post,put,....)]
		//then = validate the response

		//Step1: set base URI
		RestAssured.baseURI = "https://rahulshettyacademy.com";

		//step 2: need to import "import static io.restassured.RestAssured.*;" because given,when,then is static 
		// adding log().all()  - to see details on console
		String response = given().log().all().queryParam("key", "qaclick123").header("Content-Type", "application/json")
		.body(new Payload().AppPlacePayload())
		.when().post("/maps/api/place/add/json")
		.then().assertThat().statusCode(200)
		//matching/validating response body and headers
		.body("scope",equalTo("APP"))
		.header("Server", "Apache/2.4.52 (Ubuntu)")
		//get all response in string
		.extract().response().asString();

		System.out.println("Response body: " +response);
		
		//In entire response body I want specific value
		JsonPath js = new JsonPath(response);
		String placeId = js.getString("place_id");
		
		System.out.println("Place ID: "+placeId);
		
		//UpdatePlace Address 2nd API
		String expectedAddress = "Darbhanga, Bihar";
		given().log().all().queryParam("key", "qaclick123").header("Content-Type", "application/json")
		.body("{\r\n"
				+ "\"place_id\":\""+placeId+"\",\r\n"
				+ "\"address\":\""+expectedAddress+"\",\r\n"
				+ "\"key\":\"qaclick123\"\r\n"
				+ "}")
		.when().put("/maps/api/place/update/json")
		.then().log().all().assertThat().statusCode(200).body("msg", equalTo("Address successfully updated"));
		
		
		//getPlace api 3rd API
		String getResponse = given().log().all().queryParam("key", "qaclick123").queryParam("place_id", placeId)
		.when().get("/maps/api/place/get/json")
		.then().log().all().assertThat().statusCode(200).extract().response().asString();
		
		System.out.println("Get Response: "+getResponse);
		JsonPath js1 = new JsonPath(getResponse);
		String actualAddress = js1.getString("address");
		System.out.println("Actual Address: "+actualAddress);
		
		Assert.assertEquals(actualAddress, expectedAddress);
		
	}

}
