package gorest.TestCases;

import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasItem;

import org.hamcrest.Matchers;
import org.hamcrest.core.Is;
import org.json.simple.JSONObject;
import org.testng.annotations.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

import gorest.Utils.LoadAPIData;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;

public class GorestAPITests
{

	LoadAPIData data=new LoadAPIData();
	String baseUri=data.readData("baseUri");
	@Test
	public void POST_CreateNewUser() 
	{
		String apiCall=data.readData("POST_Uri");
		JSONObject request= new JSONObject();
		request.put("name", data.readData("name1"));
		request.put("gender", data.readData("gender1"));
		request.put("status", data.readData("status"));
		request.put("email", data.readData("email"));
		String payLoad= JSONObject.toJSONString(request);
		String token=data.readData("authenticationToken");
		String authToken="Bearer "+token;
		RequestSpecification reqSpecification=RestAssured.given()
				.header("Authorization" , authToken).contentType("application/json")
				.body(payLoad);
		Response response=reqSpecification.post(apiCall);
		int statusCode=response.getStatusCode();

		System.out.println(statusCode);
		String payLoadData=response.body().prettyPrint();
		System.out.println(payLoadData);





	}
	@Test
	public void POST_SameEmail()
	{
		String apiCall=data.readData("POST_Uri");
		JSONObject request= new JSONObject();
		request.put("name", data.readData("name1"));
		request.put("gender", data.readData("gender1"));
		request.put("status", data.readData("status"));
		request.put("email", data.readData("email"));
		String payLoad= JSONObject.toJSONString(request);
		String token=data.readData("authenticationToken");
		String authToken="Bearer "+token;
		RequestSpecification reqSpecification=RestAssured.given()
				.header("Authorization" , authToken).contentType("application/json")
				.body(payLoad);
		Response response=reqSpecification.post(apiCall);
		int statusCode=response.getStatusCode();

		System.out.println(statusCode);
		String payLoadData=response.body().prettyPrint();
		response.then().assertThat().body("message", hasItem(data.readData("assertEmailTaken.message")));
		//System.out.println(payLoadData);


	}
	@Test
	public void POST_WithMissingField() 
	{
		String apiCall=data.readData("POST_Uri");
		JSONObject request= new JSONObject();
		request.put("name", data.readData("name1"));
		request.put("gender", data.readData("gender1"));
		request.put("status", data.readData("status"));
		//request.put("email", data.readData("email"));
		String payLoad= JSONObject.toJSONString(request);
		String token=data.readData("authenticationToken");
		String authToken="Bearer "+token;
		RequestSpecification reqSpecification=RestAssured.given()
				.header("Authorization" , authToken).contentType("application/json")
				.body(payLoad);
		Response response=reqSpecification.post(apiCall);
		int statusCode=response.getStatusCode();
		System.out.println(statusCode);
		String payLoadData=response.body().prettyPrint();
		response.then().assertThat().body("message", hasItem(data.readData("assertBlankfield.message")));
		//System.out.println(payLoadData);

	}
	@Test
	public void POST_WIthWrongAuthenticationToken() throws InterruptedException 
	{
		String apiCall=data.readData("POST_Uri");
		JSONObject request= new JSONObject();
		request.put("name", data.readData("name1"));
		request.put("gender", data.readData("gender1"));
		request.put("status", data.readData("status"));
		//request.put("email", data.readData("email"));
		String payLoad= JSONObject.toJSONString(request);
		String token=data.readData("wrongToken");
		String authToken="Bearer "+token;
		RequestSpecification reqSpecification=RestAssured.given()
				.header("Authorization" , authToken).contentType("application/json")
				.body(payLoad);
		Response response=reqSpecification.post(apiCall);
		int statusCode=response.getStatusCode();	
		System.out.println(statusCode);
		String payLoadData=response.body().prettyPrint();
		response.then().assertThat().body("message", Is.is(data.readData("assertAuthenticationFailed.message")));
		//System.out.println(payLoadData);
	}
	@Test
	public void GET_USerDetailsById() 
	{
		String apicall=data.readData("GET_Uri");
		String token=data.readData("authenticationToken");
		String authToken="Bearer "+token;
		RequestSpecification reqSpecification=RestAssured.given()
				.header("Authorization" , authToken).contentType("application/json");
		Response response=reqSpecification.get(apicall);
		int statusCode=response.getStatusCode();	
		System.out.println(statusCode);
		String payLoadData=response.body().prettyPrint();
		response.then().assertThat()
		.body("name", Is.is(data.readData("assertName1")))
		.body("email", Is.is(data.readData("assertEmail1")));


	}
	@Test
	public void GET_UsrDeatailByInvalidId() {
	String apicall=data.readData("GET_InavlidUri");
	String token=data.readData("authenticationToken");
	String authToken="Bearer "+token;
	RequestSpecification reqSpecification=RestAssured.given()
			.header("Authorization" , authToken).contentType("application/json");
	Response response=reqSpecification.get(apicall);
	int statusCode=response.getStatusCode();	
	System.out.println(statusCode);
	String payLoadData=response.body().prettyPrint();
	response.then().assertThat()
	.body("message", Is.is(data.readData("assertResource.message")));
	}
	@Test
	public void GET_UserDetailsWithoutBearerToken() 
	{
		
		String apicall=data.readData("GET_Uri");
		String token=data.readData("authenticationToken");
		 RestAssured.given().auth().oauth2(token)
		.when().
		get(apicall)
		.then().assertThat()
		.body("name", Is.is(data.readData("assertName1")))
		.body("email", Is.is(data.readData("assertEmail1")));
		
		
	}
	@Test
	public void PUT_UpadateUserDeatils() 
	{
		String apicall=data.readData("PUT_Uri");
		JSONObject request= new JSONObject();
		request.put("name", data.readData("updatedName"));
		request.put("gender", data.readData("gender1"));
		request.put("status", data.readData("status"));
		request.put("email", data.readData("updtaedEmail"));
		String payLoad= JSONObject.toJSONString(request);
		String token=data.readData("authenticationToken");
		String authToken="Bearer "+token;
		RequestSpecification reqSpecification=RestAssured.given()
				.header("Authorization" , authToken).contentType("application/json")
				.body(payLoad);
		Response response=reqSpecification.put(apicall);
		int statusCode=response.getStatusCode();

		System.out.println(statusCode);
		String payLoadData=response.body().prettyPrint();
		response.then().assertThat()
		.body("name", Is.is(data.readData("updatedName")))
		.body("email", Is.is(data.readData("updtaedEmail")));
	}
	@Test
	public void PUT_UpdateUserDetailsWithInvalidId()
	{
		String apicall=data.readData("GET_InavlidUri");
		JSONObject request= new JSONObject();
		request.put("name", data.readData("updatedName"));
		request.put("gender", data.readData("gender1"));
		request.put("status", data.readData("status"));
		request.put("email", data.readData("updtaedEmail"));
		String payLoad= JSONObject.toJSONString(request);
		String token=data.readData("authenticationToken");
		String authToken="Bearer "+token;
		RequestSpecification reqSpecification=RestAssured.given()
				.header("Authorization" , authToken).contentType("application/json")
				.body(payLoad);
		Response response=reqSpecification.put(apicall);
		int statusCode=response.getStatusCode();

		System.out.println(statusCode);
		String payLoadData=response.body().prettyPrint();
		response.then().assertThat()
		.body("message", Is.is(data.readData("assertResource.message")));
	}
	@Test
	public void PUT_UpdateUserDetailsWithoutBearerToken() 
	{
		String apiCall=data.readData("GET_Uri");
		JSONObject request= new JSONObject();
		request.put("name", data.readData("updatedName"));
		request.put("gender", data.readData("gender1"));
		request.put("status", data.readData("status"));
		request.put("email", data.readData("updtaedEmail"));
		String payLoad= JSONObject.toJSONString(request);
		String token=data.readData("wrongToken");
		String authToken="API Key "+token;
		RequestSpecification reqSpecification=RestAssured.given()
				.header("Authorization" , authToken).contentType("application/json")
				.body(payLoad);
		Response response=reqSpecification.put(apiCall);
		int statusCode=response.getStatusCode();
		System.out.println(statusCode);
		String payLoadData=response.body().prettyPrint();

	}
	@Test
	public void PATCH_UpadateUserDeatils() 
	{
		String apicall=data.readData("PUT_Uri");
		JSONObject request= new JSONObject();
		request.put("name", data.readData("updatedName2"));
		request.put("gender", data.readData("gender1"));
		request.put("status", data.readData("status"));
		request.put("email", data.readData("updtedEmai2"));
		String payLoad= JSONObject.toJSONString(request);
		String token=data.readData("authenticationToken");
		String authToken="Bearer "+token;
		RequestSpecification reqSpecification=RestAssured.given()
				.header("Authorization" , authToken).contentType("application/json")
				.body(payLoad);
		Response response=reqSpecification.patch(apicall);
		int statusCode=response.getStatusCode();

		System.out.println(statusCode);
		String payLoadData=response.body().prettyPrint();
		response.then().assertThat()
		.body("name", Is.is(data.readData("updatedName2")))
		.body("email", Is.is(data.readData("updtedEmai2")));
	}
	@Test
	public void PATCH_UpdateUserDetailsWithInvalidId()
	{
		String apicall=data.readData("GET_InavlidUri");
		JSONObject request= new JSONObject();
		request.put("name", data.readData("updatedName2"));
		request.put("gender", data.readData("gender1"));
		request.put("status", data.readData("status"));
		request.put("email", data.readData("updtedEmai2"));
		String payLoad= JSONObject.toJSONString(request);
		String token=data.readData("authenticationToken");
		String authToken="Bearer "+token;
		RequestSpecification reqSpecification=RestAssured.given()
				.header("Authorization" , authToken).contentType("application/json")
				.body(payLoad);
		Response response=reqSpecification.patch(apicall);
		int statusCode=response.getStatusCode();

		System.out.println(statusCode);
		String payLoadData=response.body().prettyPrint();
		response.then().assertThat()
		.body("message", Is.is(data.readData("assertResource.message")));
	}
	@Test
	public void PATCH_UpdateUserDetailsWithoutBearerToken() 
	{
		String apiCall=data.readData("PUT_Uri");
		JSONObject request= new JSONObject();
		request.put("name", data.readData("updatedName2"));
		request.put("gender", data.readData("gender1"));
		request.put("status", data.readData("status"));
		request.put("email", data.readData("updtedEmai2"));
		String payLoad= JSONObject.toJSONString(request);
		String token=data.readData("wrongToken");
		String authToken="API Key "+token;
		RequestSpecification reqSpecification=RestAssured.given()
				.header("Authorization" , authToken).contentType("application/json")
				.body(payLoad);
		Response response=reqSpecification.patch(apiCall);
		int statusCode=response.getStatusCode();
		System.out.println(statusCode);
		String payLoadData=response.body().prettyPrint();
	}
	@Test
	public void DELETE_UserDetails() 
	{
		String apiCall=data.readData("PUT_Uri");
		String token=data.readData("authenticationToken");
		String authToken="Bearer "+token;
		Response response= RestAssured.given().header("Authorization" , authToken).contentType("application/json")
		.when().
		delete(apiCall);
		response.getStatusCode();
		
		
		
	}
	@Test
	public void DELETE_UserDetailsWithoutBearertoken()
	{
		String apiCall=data.readData("PUT_Uri");
		String token=data.readData("authenticationToken");
		String authToken="Bearer "+token;
		Response response= RestAssured.given().header("API Key" , authToken).contentType("application/json")
		.when().
		delete(apiCall);
		response.getStatusCode();
		
	}
	

}
