package gorest.TestCases;

import static org.hamcrest.Matchers.hasItem;


import org.hamcrest.core.Is;
import org.json.simple.JSONObject;
import org.testng.annotations.Test;


import gorest.Utils.LoadAPIData;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class GorestAPITests
{

	LoadAPIData data=new LoadAPIData();
	String baseUri=data.readData("baseUri");
	@Test
	public void POST_CreateNewUser_01() 
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
	public void POST_SameEmail_02()
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


	}
	@Test
	public void POST_WithMissingField_03() 
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

	}
	@Test
	public void POST_WIthWrongAuthenticationToken_04() throws InterruptedException 
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
	}
	@Test
	public void GET_USerDetailsById_05()
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
	public void GET_UsrDeatailByInvalidId_06() {
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
	public void GET_UserDetailsWithoutBearerToken_07() 
	{

		String apicall=data.readData("GET_Uri");
		String token=data.readData("authenticationToken");
		RestAssured.given().auth().oauth2(token)
		.when().
		get(apicall)
		.then().assertThat()
		.body("name", hasItem(data.readData("assertName1")))
		.body("email", Is.is(data.readData("assertEmail1")));


	}
	@Test
	public void PUT_UpadateUserDeatils_08() 
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
	public void PUT_UpdateUserDetailsWithInvalidId_09()
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
	public void PUT_UpdateUserDetailsWithoutBearerToken_10() 
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
	public void PATCH_UpadateUserDeatils_11() 
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
		.body("name", hasItem(data.readData("updatedName2")))
		.body("email", Is.is(data.readData("updtedEmai2")));
	}
	@Test
	public void PATCH_UpdateUserDetailsWithInvalidId_12()
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
	public void PATCH_UpdateUserDetailsWithoutBearerToken_13() 
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
	public void DELETE_UserDetails_14() 
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
	public void DELETE_UserDetailsWithoutBearertoken_15()
	{
		String apiCall=data.readData("PUT_Uri");
		String token=data.readData("authenticationToken");
		String authToken="Bearer "+token;
		Response response= RestAssured.given().header("API Key" , authToken).contentType("application/json")
				.when().
				delete(apiCall);
		response.getStatusCode();

	}
	@Test
	public void CreateUserPost_16()
	{
		String apiCall=data.readData("user_POST");
		JSONObject request= new JSONObject();
		request.put("user", data.readData("user"));
		request.put("title", data.readData("title"));
		request.put("body", data.readData("body"));
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
		response.then().assertThat()
		.body("title", Is.is(data.readData("title")))
		.body("body", Is.is(data.readData("body")));

	}

	@Test
	public void CreateUserPost_withoutBearerToken_17() 
	{
		String apiCall=data.readData("user_POST");
		JSONObject request= new JSONObject();
		request.put("user", data.readData("user"));
		request.put("title", data.readData("title"));
		request.put("body", data.readData("body"));
		String payLoad= JSONObject.toJSONString(request);
		String token=data.readData("authenticationToken");
		String authToken="API Key "+token;
		RequestSpecification reqSpecification=RestAssured.given()
				.header("Authorization" , authToken).contentType("application/json")
				.body(payLoad);
		Response response=reqSpecification.post(apiCall);
		int statusCode=response.getStatusCode();
		System.out.println(statusCode);
		String payLoadData=response.body().prettyPrint();
		response.then().assertThat()
		.body("message", Is.is(data.readData("assertAuthenticationFailed.message")));		
	}
	@Test
	public void CreateUserPost_WIthInvalidId_18()
	{
		String apiCall=data.readData("UserPost_InvalidUri");
		JSONObject request= new JSONObject();
		request.put("user", data.readData("user"));
		request.put("title", data.readData("title"));
		request.put("body", data.readData("body"));
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
		response.then().assertThat()
		.body("title", Is.is(data.readData("title")))
		.body("body", Is.is(data.readData("body")));

	}
	@Test
	public void CreateUserPost_withMissingFields_19() 
	{
		String apiCall=data.readData("user_POST");
		JSONObject request= new JSONObject();
		request.put("user", data.readData("user"));
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
	}
	@Test
	public void Create_POSTComment_20() 
	{
		String apiCall=data.readData("POST_Comment_Uri");
		JSONObject request= new JSONObject();
		request.put("title", data.readData("title"));
		request.put("name", data.readData("POST_Comment_Name"));
		request.put("email", data.readData("POST_Comment_Email"));
		request.put("body", data.readData("body"));
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
		response.then().assertThat()
		.body("email", Is.is(data.readData("POST_Comment_Email")))
		.body("body", Is.is(data.readData("body")));

	}
	@Test
	public void Create_POSTCommentwithInavlidId_21() 
	{
		String apiCall=data.readData("POST_Comment_invalidUri");
		JSONObject request= new JSONObject();
		request.put("title", data.readData("title"));
		request.put("name", data.readData("POST_Comment_Name"));
		request.put("email", data.readData("POST_Comment_Email"));
		request.put("body", data.readData("body"));
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
		response.then().assertThat()
		.body("message", hasItem(data.readData("POST_CommentInavlIdUser.meesage")));


	}
	@Test
	public void Create_POSTCommentwithoutBearerToken_22()
	{
		String apiCall=data.readData("POST_Comment_Uri");
		JSONObject request= new JSONObject();
		request.put("title", data.readData("title"));
		request.put("name", data.readData("POST_Comment_Name"));
		request.put("email", data.readData("POST_Comment_Email"));
		request.put("body", data.readData("body"));
		String payLoad= JSONObject.toJSONString(request);
		String token=data.readData("authenticationToken");
		String authToken="API Key "+token;
		RequestSpecification reqSpecification=RestAssured.given()
				.header("Authorization" , authToken).contentType("application/json")
				.body(payLoad);
		Response response=reqSpecification.post(apiCall);
		int statusCode=response.getStatusCode();
		System.out.println(statusCode);
		String payLoadData=response.body().prettyPrint();
		response.then().assertThat()
		.body("message", Is.is(data.readData("assertAuthenticationFailed.message")));	

	}
	@Test
	public void Create_POSTCommentWithMissingFiled_23() 
	{
		String apiCall=data.readData("POST_Comment_Uri");
		JSONObject request= new JSONObject();
		//request.put("title", data.readData("title"));
		request.put("name", data.readData("POST_Comment_Name"));
		request.put("email", data.readData("POST_Comment_Email"));
		request.put("body", data.readData("body"));
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
	}
	@Test
	public void CreateUser_todo_POST_24()

	{
		String apiCall=data.readData("POST_todo_Uri");
		JSONObject request= new JSONObject();
		request.put("title", data.readData("title"));
		request.put("due_on", data.readData("POST_todo_DueOn"));
		request.put("status", data.readData("POST_todo_Status"));
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
		response.then().assertThat()
		.body("title", Is.is(data.readData("title")))
		.body("due_on", Is.is(data.readData("POST_todo_DueOn")))
		.body("status", Is.is(data.readData("POST_todo_Status")));

	}
	@Test
	public void CreateUser_todo_POSTWithInavalidId_25()

	{
		String apiCall=data.readData("POST_todo_InavlidUri");
		JSONObject request= new JSONObject();
		request.put("title", data.readData("title"));
		request.put("due_on", data.readData("POST_todo_DueOn"));
		request.put("status", data.readData("POST_todo_Status"));
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
		response.then().assertThat()
		.body("message", hasItem(data.readData("POST_CommentInavlIdUser.meesage")));
	}
	@Test
	public void CreateUser_todo_POSTWithoutBearerToken_26()

	{
		String apiCall=data.readData("POST_todo_Uri");
		JSONObject request= new JSONObject();
		request.put("title", data.readData("title"));
		request.put("due_on", data.readData("POST_todo_DueOn"));
		request.put("status", data.readData("POST_todo_Status"));
		String payLoad= JSONObject.toJSONString(request);
		String token=data.readData("authenticationToken");
		String authToken="API Key "+token;
		RequestSpecification reqSpecification=RestAssured.given()
				.header("Authorization" , authToken).contentType("application/json")
				.body(payLoad);
		Response response=reqSpecification.post(apiCall);
		int statusCode=response.getStatusCode();
		System.out.println(statusCode);
		String payLoadData=response.body().prettyPrint();
		response.then().assertThat()
		.body("message", Is.is(data.readData("assertAuthenticationFailed.message")));	
	}
	@Test
	public void CreateUser_todo_POSTWithMissingField_27()

	{
		String apiCall=data.readData("POST_todo_Uri");
		JSONObject request= new JSONObject();
		request.put("due_on", data.readData("POST_todo_DueOn"));
		request.put("status", data.readData("POST_todo_Status"));
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

	}
	

}
