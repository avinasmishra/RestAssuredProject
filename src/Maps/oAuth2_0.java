package Maps;

import static io.restassured.RestAssured.*;

import io.restassured.path.json.JsonPath;

public class oAuth2_0 {

	public static void main(String[] args) {
		/*System.setProperty("webdriver.chrome.driver", "C:/Users/Avinash/SeleniumTraining/Driver/chromedriver.exe");
		WebDriver driver = new ChromeDriver();
		driver.get("https://accounts.google.com/o/oauth2/v2/auth?scope=https://www.googleapis.com/auth/userinfo.email&auth_url=https://accounts.google.com/o/oauth2/v2/auth&client_id=692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com&response_type=code&redirect_uri=https://rahulshettyacademy.com/getCourse.php");
		driver.findElement(By.xpath("//input[@type='email']")).sendKeys("akhilesh4mishra@gmail.com");
		driver.findElement(By.xpath("//input[@type='email']")).sendKeys(Keys.ENTER);

		driver.findElement(By.xpath("//input[@type='password']")).sendKeys("Akhilesh4Mishra");
		driver.findElement(By.xpath("//input[@type='password']")).sendKeys(Keys.ENTER);

		String url = driver.getCurrentUrl();
		System.out.println(url); */

		//this url I am getting after entering email & password
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

	
		//3rd API - Actual Request
		String actualResponse = given().log().all()
				.queryParam("access_token", access_token)
				.when().log().all().get("https://rahulshettyacademy.com/getCourse.php")
				.then().log().all().assertThat().statusCode(200).extract().response().asString();

	}

}
