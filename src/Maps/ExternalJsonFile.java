package Maps;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ExternalJsonFile {

	public static void main(String[] args) throws IOException {
		
		RestAssured.baseURI = "https://rahulshettyacademy.com";
		//convert content of file to string(expectation) but how?
		//1. convert the content of file into Bytes
		//2. then convert Bytes to string
		
		String response = given().log().all().queryParam("key", "qaclick123").header("Content-Type","application/json")
		.body(new String(Files.readAllBytes(Paths.get("C:\\Users\\Avinash\\Desktop\\Selenium by Rahul Shethy\\API Testing\\JsonFile\\addPlace.json"))))
		.when().post("/maps/api/place/add/json")
		.then().log().all().assertThat().statusCode(200).extract().response().asString();
		
		System.out.println("Response Data: "+response);
		
		JsonPath js = new JsonPath(response);
		String placeId = js.get("place_id");
		System.out.println("PlaceID: "+placeId);

	}

}
