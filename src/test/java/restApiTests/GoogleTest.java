package restApiTests;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;;

public class GoogleTest {
	
	
	
	@Test
	public void postReqTest(){
	
		RestAssured.baseURI = "http://216.10.245.166/Library/Addbook.php";
		String reqBody="{"+
				"	\"name\":\"Learn Automation\","+
				"	\"isbn\":\"zssvva\","+
				"	\"aisle\":\"7224\","+
				"	\"author\":\"MY Author1\"	"+
				"}";
		
		Response res= given().
			header("Content-Type","application/json").
			body(reqBody).
		when().
			post("Library/Addbook.php").
		then().
			assertThat().
				//statusCode(200).and().
				//contentType(ContentType.JSON).and().
				//body("Msg", equalTo("successfully added")).
		extract().response();
		
		//System.out.println(res.getBody().asString());
		JsonPath jsres = new JsonPath(res.getBody().asString());
		
		String bookId="";
		if(res.getStatusCode() == 200){
			System.out.println(jsres.get("Msg"));
			System.out.println("Book ID:" +jsres.get("ID"));
			bookId = jsres.get("ID");
		}else{
			System.out.println(jsres.get("msg"));
		}
		
		if(bookId != ""){
			
			System.out.println("**********Deletion**********");
			String bodyforDel = "{"+
					"	\"ID\" : \""+bookId+"\""+
					"}"; 
					
			System.out.println("Request Body: "+bodyforDel);
			Response resDel = given().
				header("Content-Type","application/json").and().
				body(bodyforDel).
			when().
				post("Library/DeleteBook.php").
			then().assertThat().
				statusCode(200).and().
				body("msg", equalTo("book is successfully deleted")).
			extract().response();
			
			/*System.out.println("Response Body: "+resDel.getBody().toString());
			JsonPath jsResDel = new JsonPath(resDel.getBody().asString());
			
			System.out.println("Deletion status code:"+resDel.getStatusCode());
			System.out.println("Message:"+jsResDel.get("msg"));*/
		}
	}
	
}
