package SerializationMaps;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.specification.RequestSpecification;
import static io.restassured.RestAssured.*;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import POJO.CreateOrder;
import POJO.LoginRequest;
import POJO.LoginResponse;
import POJO.Orders;
import POJO.ViewOrder;

public class E2EEcommerceAPITest {

	public static void main(String[] args) {

		//Login
		RequestSpecification req = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com").setContentType(ContentType.JSON).build();
		
		LoginRequest loginReq = new LoginRequest();
		loginReq.setUserEmail("iamking@gmail.com");
		loginReq.setUserPassword("Iamking@000");
		
		RequestSpecification loginBody =  given().log().all().relaxedHTTPSValidation().spec(req).body(loginReq);
		
		LoginResponse response = loginBody.when().post("/api/ecom/auth/login")
		.then().log().all().extract().response().as(LoginResponse.class);
		
		String token = response.getToken();
		String userId = response.getUserId();
		System.out.println("Token: "+token);
		System.out.println("User ID: "+userId);
		
		
		//Add Product
		RequestSpecification addProductBaseReq = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
				.addHeader("Authorization", token).build();
		
		RequestSpecification addProductBody = given().log().all().relaxedHTTPSValidation().spec(addProductBaseReq)
		.param("productName", "Green Plant").param("productCategory", "Laptop").param("productAddedBy", userId)
		.param("productSubCategory", "House Plant").param("productPrice", "10500")
		.param("productDescription", "Green Evnironment").param("productFor", "Environment")
		.multiPart("productImage",new File("C:\\Users\\Avinash\\Pictures\\Screenshots\\tree.png"));
		
		String addProductResponse = addProductBody.when().post("/api/ecom/product/add-product")
		.then().log().all().extract().response().asString();
		
		JsonPath js = new JsonPath(addProductResponse);
		String productId = js.getString("productId");
		System.out.println("Product Id: "+productId);
		
		
		//Create Order
		RequestSpecification createOrderBaseReq = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
				.addHeader("Authorization", token).setContentType(ContentType.JSON).build();
		
		Orders order = new Orders();
		order.setCountry("India");
		order.setProductOrderedId(productId);
		
		List<Orders> myOrderList = new ArrayList<Orders>();
		myOrderList.add(order);
		
		CreateOrder createOrder = new CreateOrder();
		createOrder.setOrders(myOrderList);
		
		RequestSpecification createOrderBody = given().log().all().relaxedHTTPSValidation().spec(createOrderBaseReq)
		.body(createOrder);
				
		String createOrderResponse = createOrderBody.when().post("/api/ecom/order/create-order")
		.then().log().all().extract().response().asString();
		
		JsonPath js1 = new JsonPath(createOrderResponse);
		String message = js1.getString("message");
		String orderId  =js1.getString("orders[0]");
		System.out.println("order Id: "+orderId);
		System.out.println("Message: "+message);
		
		
		//View Order
		
		 RequestSpecification viewOrderBaseReq = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
				.addHeader("Authorization", token).addQueryParam("id",orderId ).build();
		
		RequestSpecification viewOrderBody = given().log().all().relaxedHTTPSValidation().spec(viewOrderBaseReq);
		
		ViewOrder vo = viewOrderBody.when().get("/api/ecom/order/get-orders-details")
		.then().log().all().extract().response().as(ViewOrder.class);
		
	
		System.out.println(vo.getData().get_id());
		System.out.println(vo.getData().getOrderById());
		System.out.println(vo.getData().getOrderBy());
		System.out.println(vo.getData().getProductOrderedId());
		System.out.println(vo.getData().getProductName());
		System.out.println(vo.getData().getCountry());
		System.out.println(vo.getData().getProductDescription());
		System.out.println(vo.getData().getProductImage());
		System.out.println(vo.getData().getOrderPrice());
		System.out.println(vo.getData().get__v());
		System.out.println(vo.getMessage());
		
		
		//Delete Product 
		
		RequestSpecification deleteProductBaseReq = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
				.addHeader("Authorization", token).setContentType(ContentType.JSON).build();
		
		RequestSpecification deleteProductBody = given().log().all().relaxedHTTPSValidation().spec(deleteProductBaseReq).pathParam("prodId", productId);
		
		String deleteResponse= deleteProductBody.when().delete("/api/ecom/product/delete-product/{prodId}")
		.then().log().all().extract().response().asString();
	
		JsonPath js2 = new JsonPath(deleteResponse);
		String msg = js2.getString("message");
		System.out.println("Deleted Message: "+msg);
	}

}
