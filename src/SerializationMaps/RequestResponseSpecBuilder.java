package SerializationMaps;

import static io.restassured.RestAssured.given;

import java.util.ArrayList;
import java.util.List;

import POJO.AddPlaceApiRequest;
import POJO.Location;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class RequestResponseSpecBuilder {

	public static void main(String[] args) {
			
		//now create an object for main POJO class
		AddPlaceApiRequest ar= new AddPlaceApiRequest();
		ar.setAccuracy(50);
		ar.setAddress("private String lng;");
		ar.setLanguage("French-IN");
		ar.setName("Frontline house");
		ar.setPhone_number("(+91) 983 893 3937");
		ar.setWebsite("http://google.com");
		
		//here types- return type is list so need to create an list
		List<String> myList = new ArrayList<String>();
		myList.add("shoe park");
		myList.add("shop");
		ar.setTypes(myList);
		
		//here location - return type is class, so need to create an object for location class
		Location lo = new Location();
		lo.setLat(-38.383494);
		lo.setLng(33.427362);
		ar.setLocation(lo);
		
		//RestAssured.baseURI = "https://rahulshettyacademy.com";
		
		//what all attached with given() is called as request
		RequestSpecification req = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
				.addQueryParam("key", "qaclick123").setContentType(ContentType.JSON).build();
		
		//what all attached with then() is called as response
		ResponseSpecification resp = new ResponseSpecBuilder().expectStatusCode(200).expectContentType(ContentType.JSON).build();
		
		
		Response response = given().spec(req).body(ar)
				.when().post("/maps/api/place/add/json")
				.then().spec(resp).extract().response();
		
		String respon = response.asString();
		System.out.println("Response: "+respon);

	}

	}
