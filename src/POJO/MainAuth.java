package POJO;

import static io.restassured.RestAssured.given;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.testng.Assert;

import io.opentelemetry.exporter.logging.SystemOutLogRecordExporter;
import io.restassured.parsing.Parser;
import io.restassured.path.json.JsonPath;

public class MainAuth {

	public static void main(String[] args) {
		
		String URL = "https://rahulshettyacademy.com/getCourse.php?code=4%2F0AfJohXnHYpsPQKqulpIBAojPezxPMsDLcz7V9GSLw4WRpe0OZ-EcJXjTg_GQvSayoZ4FcQ&scope=email+https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fuserinfo.email+openid&authuser=1&prompt=none";

		//extract code value - using split method
		String subCode = URL.split("code=")[1];
		String code = subCode.split("&scope")[0];
		System.out.println("Code: "+code);


		//2nd API- GET Access Token		
		String accessTokenResponse = given().log().all().urlEncodingEnabled(false)
				.queryParam("code", code)
				.queryParam("client_id", "692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com")
				.queryParam("client_secret", "erZOWM9g3UtwNRj340YYaK_W")
				.queryParam("redirect_uri", "https://rahulshettyacademy.com/getCourse.php")
				.queryParam("grant_type", "authorization_code")
				.queryParam("scope", "https://www.googleapis.com/auth/userinfo.email")
				.queryParam("response_type", "code")
				.when().log().all().post("https://www.googleapis.com/oauth2/v4/token")
				.then().log().all().assertThat().extract().response().asString();

		JsonPath js = new JsonPath(accessTokenResponse);
		String access_token = js.getString("access_token");
		System.out.println("Access Token: "+access_token);

	
		String accessToken = "ya29.a0AfB_byCfL0Ei5s-e4fQp9iiO15ISVS4-iofyc6gw3n2DgxQAY3ulYRFOZJi9O3XVjT0-20ZRqqUwioPBHWPuMEBwbQQdXZxTiAdd1lMCRW1wVdyFknaW7f8eA8pBjEAh6wJafK1Qw8tPtJGFSZRbHIKpopR4ZJI0gvYaCgYKAfYSARESFQGOcNnCo73oJWUPxk21sOU6T9_KcA0170";
		
		//3rd API - Actual Request
		getCourse gc = given()
				.queryParam("access_token", accessToken).expect().defaultParser(Parser.JSON)
				.when().get("https://rahulshettyacademy.com/getCourse.php")
				.as(getCourse.class);
		

		System.out.println("Instructor: "+gc.getInstructor());
		System.out.println("LinkedIn: "+gc.getLinkedIn());
		System.out.println("Expertise: "+gc.getExpertise());
		gc.getCourses().getWebAutomation();
		
		//extracting courses details
		System.out.println("API Course[0] CourseTitle: "+gc.getCourses().getApi().get(0).getCourseTitle());

		//Scenario 1: getAll CourseTitle for WebAutomation course
		List<WebAutomation> webAuto = gc.getCourses().getWebAutomation();
		for(int i=0;i<webAuto.size();i++)
		{
			System.out.println(webAuto.get(i).getCourseTitle());
			
		}
		//Scenario 2: getprice for soapUI Webservices testing course
		List<Api> apiCourse = gc.getCourses().getApi();
		for(int i=0;i<apiCourse.size();i++)
		{
			if(apiCourse.get(i).getCourseTitle().equalsIgnoreCase("SoapUI Webservices testing"))
			{
				System.out.println("Price: "+apiCourse.get(i).getPrice());				
			}
		}
		
		//Scenario 3: compare actual and expected coures title for soapUI Webservices testing course
		
		//step1
		String[] expectedCourse = {"Selenium Webdriver Java","Cypress","Protractor"};
		
		//step2- collect all the actual title- for that create ArrayList- for dynamic list we have to use ArrayList
		ArrayList<String>actualCourse = new ArrayList<String>();
		//to add element in Arraylist use .add() method
		
		List<WebAutomation> webAutomationCourse = gc.getCourses().getWebAutomation();
		for(int i=0;i<webAutomationCourse.size();i++)
		{
			actualCourse.add(webAuto.get(i).getCourseTitle());			
		}
		
		//step3- now compare but the problem is one is array ad another is arraylist - convert array to araylist
		List<String> expectedList = Arrays.asList(expectedCourse);
		
		Assert.assertTrue(actualCourse.equals(expectedList));
		
	}

	}

