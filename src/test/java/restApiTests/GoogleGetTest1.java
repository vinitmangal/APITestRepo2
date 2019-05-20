package restApiTests;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class GoogleGetTest1 {

	@Test
	public void getReqTest(){
		
		RestAssured.baseURI = "http://216.10.245.166/Library/GetBook.php";
		
		Response res = given().
			param("AuthorName", "Vinit Mangal").
		when().
			get().
		then().
			assertThat().
				statusCode(200).and().
				contentType(ContentType.JSON).and().body("isbn[0]", equalTo("aaaa")).
				extract().response();
		
	
		System.out.println("Body: "+res.getBody().asString());		
		JsonPath jsRes = new JsonPath(res.getBody().asString());		
		System.out.println(jsRes.get("isbn[0]"));
		
	}
}
