package Maps;

import org.testng.Assert;

import Files.Payload;
import io.restassured.path.json.JsonPath;

public class ComplexJsonParse {

	public static void main(String[] args) {
		
		JsonPath js = new JsonPath(Payload.coursePrice());
		
		
	/*	{
			"dashboard": {
			"purchaseAmount": 910,
			"website": "rahulshettyacademy.com"
			},
			"courses": [
			{
			"title": "Selenium Python",
			"price": 50,
			"copies": 6
			},
			{
			"title": "Cypress",
			"price": 40,
			"copies": 4
			},
			{
			"title": "RPA",
			"price": 45,
			"copies": 10
			}
			]
			}  */
		
		//1. Print No of courses returned by API
		int courseCount = js.getInt("courses.size()");
		System.out.println("Course Count: "+courseCount);

		//2.Print Purchase Amount
		int totalCourseAmount = js.getInt("dashboard.purchaseAmount");
		System.out.println("Total Course Amount: "+totalCourseAmount);
		
		//3. Print Title of the first course
		String firstCourseTitle = js.getString("courses[0].title");
		System.out.println("First Course Title: "+firstCourseTitle);

		//4. Print All course titles and their respective Prices
		for(int i=0;i<courseCount;i++)
		{
			String courseTitle = js.getString("courses["+i+"].title");
			int coursePrice = js.getInt("courses["+i+"].price");
			System.out.println("Course Title: "+courseTitle+ " and Course Price: "+coursePrice);
		}

		//5. Print no of copies sold by RPA Course
		for(int i=0;i<courseCount;i++)
		{
			String courseTitle = js.getString("courses["+i+"].title");
			if(courseTitle.equalsIgnoreCase("RPA"))
			{
				int courseCopied = js.getInt("courses["+i+"].copies");
				System.out.println("Course Title: "+courseTitle+ " and Course Copied: "+courseCopied);
				break;
			}
		}

		//6. Verify if Sum of all Course prices matches with Purchase Amount
		int actualAmount =0;
		for(int i=0;i<courseCount;i++)
		{
			int coursePrice = js.getInt("courses["+i+"].price");
			int courseCopied = js.getInt("courses["+i+"].copies");
			actualAmount = actualAmount + (coursePrice*courseCopied);
		}
		System.out.println("Actual Amount: " +actualAmount);
		Assert.assertEquals(actualAmount, totalCourseAmount);

	}

}
