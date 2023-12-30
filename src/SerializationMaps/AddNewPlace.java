package SerializationMaps;

import io.restassured.RestAssured;
import static io.restassured.RestAssured.*;

import java.util.ArrayList;
import java.util.List;

import POJO.AddPlaceApiRequest;
import POJO.Location;

public class AddNewPlace {

	public static void main(String[] args) {
		
		RestAssured.baseURI = "https://rahulshettyacademy.com";
		
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
		
		
		
		String response = given().log().all().queryParam("key", "qaclick123")
				.body(ar)
				.when().post("/maps/api/place/add/json")
				.then().assertThat().extract().response().asString();
		System.out.println("Response: "+response);

	}

}

/* below are request payload in json format- but we create POJO classes and set the value and got the output
 {
  "location": {
    "lat": -38.383494,
    "lng": 33.427362
  },
  "accuracy": 50,
  "name": "Frontline house",
  "phone_number": "(+91) 983 893 3937",
  "address": "29, side layout, cohen 09",
  "types": [
    "shoe park",
    "shop"
  ],
  "website": "http://google.com",
  "language": "French-IN"
}

 */
