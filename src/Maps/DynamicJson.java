package Maps;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import Files.Payload;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import static io.restassured.RestAssured.*;


public class DynamicJson {
	
	@Test(dataProvider="BooksData")
	public void addBookPost(String isbn, String aisle)
	{
		RestAssured.baseURI = "https://rahulshettyacademy.com";
		String response = given().log().all().header("Content-Type","application/json")
				.body(Payload.addBook(isbn,aisle))
				.when().post("/Library/Addbook.php")
				.then().log().all().assertThat().extract().response().asString();
		
		JsonPath js = new JsonPath(response);
		String Id = js.getString("ID");
		System.out.println("AddBook Response Value: "+response);
		
		//delete  Book
		Response resp = given().log().all().header("Content-Type","application/json")
				.body("{\r\n"
						+ "    \"ID\": \""+Id+"\"\r\n"
						+ "}")
				.when().delete("/Library/DeleteBook.php")
				.then().log().all().assertThat()	
				.extract().response();

		System.out.println("DeletedBook Response Value: "+resp.asString());
		
	}
	
	//parameterize API test with multiple datasets
	@DataProvider(name="BooksData")
	public Object[][] getData()
	{
		//This is multidimensional array= collections of array is called MD array
		//parameter should be same for each entry, here we are passing 2 parameters
		//Once it done- goto addBookPost() method and do the changes from addBookPost() -> addBookPost(String a, String b)
		//because we are passing 2 parameters that why, it is necessary
		return new Object[][]
				{
			{"asd","123"},{"qwe","231"},{"wed","143"},{"awe","134"}			
	};	
	}
}
