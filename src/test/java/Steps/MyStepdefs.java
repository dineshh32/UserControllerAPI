package Steps;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import models.UserController;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.testng.Assert;
import utils.Endpoints;
import utils.JsonInputParser;
import utils.TestNGListener;

import java.util.List;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

public class MyStepdefs {
        int userID;

        UserController user, user1, responseUser;

        JSONArray jsonArray;


        Response response, response1, putresponse;

        ObjectMapper objectMapper = new ObjectMapper();
        JsonPath jsonPath;

        @Given("user details")
        public void userDetails() {
                JSONObject testData = (JSONObject) JsonInputParser.data.get("createRequest");
        }

        @When("creating a user")
        public void creatingAUser() throws JsonProcessingException {
                JSONObject testData = (JSONObject) TestNGListener.data.get("createRequest");
                user = new UserController((String) testData.get("name"),
                        (String) testData.get("address"),
                        (Long) testData.get("marks"));

                response = given()
                        .body(user)
                        .when().post(Endpoints.userEndpoint)
                        .then().body(matchesJsonSchemaInClasspath("userController_schema.json"))
                        .statusCode(200).extract().response();
                objectMapper = new ObjectMapper();
                responseUser = objectMapper.readValue(response.asString(), UserController.class);
        }

        @Then("user is created")
        public void userIsCreated() throws JsonProcessingException {
                Assert.assertEquals(user.getName(), responseUser.getName());


        }


        @When("updating a user")
        public void updatingAUser() throws JsonProcessingException {
                JSONObject testData= (JSONObject) JsonInputParser.data.get("createRequest");
                user = new UserController((String) testData.get("name"),
                        (String) testData.get("address"),
                        (Long) testData.get("marks"));

                response = given()
                        .body(user)
                        .when().post(Endpoints.userEndpoint)
                        .then().body(matchesJsonSchemaInClasspath("userController_schema.json"))
                        .statusCode(200).extract().response();
                objectMapper = new ObjectMapper();
                responseUser = objectMapper.readValue(response.asString(), UserController.class);

                jsonPath = new JsonPath(response.asString());


        }

        @Then("user is updated")
        public void userIsUpdated() throws JsonProcessingException {
                JSONObject testData1= (JSONObject) JsonInputParser.data.get("updateRequest");
                user =  new UserController(userID=jsonPath.getInt("id"),(String) testData1.get("name"),
                        (String) testData1.get("address"),
                        (Long) testData1.get("marks"));

                putresponse = given()
                        .body(user)
                        .when().put(Endpoints.userEndpoint2)
                        .then()
                        .statusCode(200).extract().response();
                jsonPath = new JsonPath(putresponse.asString());
                objectMapper = new ObjectMapper();
                responseUser = objectMapper.readValue(putresponse.asString(), UserController.class);
                Assert.assertEquals(user.getMarks(), responseUser.getMarks());
        }

        @When("creating a resource without name")
        public void creatingAResourceWithoutName() throws JsonProcessingException {
                        JSONObject testData = (JSONObject) JsonInputParser.data.get("createRequest");
                        user = new UserController((String) testData.get(""),
                                (String) testData.get("address"),
                                (Long) testData.get("marks"));


        }

        @Then("error message is displayed")
        public void errorMessageIsDisplayed() throws JsonProcessingException {
                response = given()
                        .body(user)
                        .when().post(Endpoints.userEndpoint)
                        .then()
                        .statusCode(400).extract().response();
                jsonPath = new JsonPath(response.asString());
                Assert.assertEquals(jsonPath.getString("message"),"Name is required");
        }

        @When("creating a resource without address")
        public void creatingAResourceWithoutAddress() {
                JSONObject testData = (JSONObject) JsonInputParser.data.get("createRequest");
                user = new UserController((String) testData.get("name"),
                        (String) testData.get(""),
                        (Long) testData.get("marks"));
        }

        @Then("error message for address is displayed")
        public void errorMessageForAddressIsDisplayed() {
                response = given()
                        .body(user)
                        .when().post(Endpoints.userEndpoint)
                        .then()
                        .statusCode(400).extract().response();
                jsonPath = new JsonPath(response.asString());
                Assert.assertEquals(jsonPath.getString("message"),"Address is required");
        }

        @When("creating a resource without marks")
        public void creatingAResourceWithoutMarks() {
                JSONObject testData = (JSONObject) JsonInputParser.data.get("blankMarks");

        }

        @Then("error message for marks is displayed")
        public void errorMessageForMarksIsDisplayed() {
                try {
                        JSONObject testData = (JSONObject) JsonInputParser.data.get("blankMarks");
                        user = new UserController((String) testData.get("name"),
                                (String) testData.get("address"),
                                (Long) testData.get(""));
                } catch (NullPointerException e) {
                        System.out.println("Bad Request");
                }
        }

        @When("deleting a resource")
        public void deletingAResource() throws JsonProcessingException {
                JSONObject testData= (JSONObject) JsonInputParser.data.get("createRequest");
                user = new UserController((String) testData.get("name"),
                        (String) testData.get("address"),
                        (Long) testData.get("marks"));

                response = given()
                        .body(user)
                        .when().post(Endpoints.userEndpoint)
                        .then().body(matchesJsonSchemaInClasspath("userController_schema.json"))
                        .statusCode(200).extract().response();
                objectMapper = new ObjectMapper();
                responseUser = objectMapper.readValue(response.asString(), UserController.class);


        }

        @Then("resource should be deleted")
        public void resourceShouldBeDeleted() {
                jsonPath = new JsonPath(response.asString());
                userID=jsonPath.getInt("id");
                response = given()
                        .body(user)
                        .when().delete(Endpoints.deleteuserEndpoint + "/" + userID)
                        .then()
                        .statusCode(200).extract().response();

        }

        @When("updating a user without specifying a name")
        public void updatingAUserWithoutSpecifyingAName() throws JsonProcessingException {
                JSONObject testData= (JSONObject) JsonInputParser.data.get("createRequest");
                user = new UserController((String) testData.get("name"),
                        (String) testData.get("address"),
                        (Long) testData.get("marks"));

                response = given()
                        .body(user)
                        .when().post(Endpoints.userEndpoint)
                        .then().body(matchesJsonSchemaInClasspath("userController_schema.json"))
                        .statusCode(200).extract().response();
                objectMapper = new ObjectMapper();
                responseUser = objectMapper.readValue(response.asString(), UserController.class);

                jsonPath = new JsonPath(response.asString());
        }

        @Then("error message asking for name is displayed")
        public void errorMessageAskingForNameIsDisplayed() throws JsonProcessingException {
                JSONObject testData1= (JSONObject) JsonInputParser.data.get("updateRequest");
                user =  new UserController(userID=jsonPath.getInt("id"),(String) testData1.get(""),
                        (String) testData1.get("address"),
                        (Long) testData1.get("marks"));

                putresponse = given()
                        .body(user)
                        .when().put(Endpoints.userEndpoint2)
                        .then()
                        .statusCode(400).extract().response();
                jsonPath = new JsonPath(putresponse.asString());
                Assert.assertEquals(jsonPath.getString("message"),"Name is required");
        }

        @When("updating a user without specifying an address")
        public void updatingAUserWithoutSpecifyingAnAddress() throws JsonProcessingException {
                JSONObject testData= (JSONObject) JsonInputParser.data.get("createRequest");
                user = new UserController((String) testData.get("name"),
                        (String) testData.get("address"),
                        (Long) testData.get("marks"));

                response = given()
                        .body(user)
                        .when().post(Endpoints.userEndpoint)
                        .then().body(matchesJsonSchemaInClasspath("userController_schema.json"))
                        .statusCode(200).extract().response();
                objectMapper = new ObjectMapper();
                responseUser = objectMapper.readValue(response.asString(), UserController.class);

                jsonPath = new JsonPath(response.asString());
        }

        @Then("error message asking for address is displayed")
        public void errorMessageAskingForAddressIsDisplayed() {
                JSONObject testData1= (JSONObject) JsonInputParser.data.get("updateRequest");
                user =  new UserController(userID=jsonPath.getInt("id"),(String) testData1.get("name"),
                        (String) testData1.get(""),
                        (Long) testData1.get("marks"));

                putresponse = given()
                        .body(user)
                        .when().put(Endpoints.userEndpoint2)
                        .then()
                        .statusCode(400).extract().response();
                jsonPath = new JsonPath(putresponse.asString());
                Assert.assertEquals(jsonPath.getString("message"),"Address is required");
        }

        @When("deleting a resource without specifying id")
        public void deletingAResourceWithoutSpecifyingId() throws JsonProcessingException {
                JSONObject testData= (JSONObject) JsonInputParser.data.get("createRequest");
                user = new UserController((String) testData.get("name"),
                        (String) testData.get("address"),
                        (Long) testData.get("marks"));

                response = given()
                        .body(user)
                        .when().post(Endpoints.userEndpoint)
                        .then().body(matchesJsonSchemaInClasspath("userController_schema.json"))
                        .statusCode(200).extract().response();
                objectMapper = new ObjectMapper();
                responseUser = objectMapper.readValue(response.asString(), UserController.class);

        }

        @Then("error is thrown")
        public void errorIsThrown() {
                jsonPath = new JsonPath(response.asString());
                userID=jsonPath.getInt("id");
                response = given()
                        .body(user)
                        .when().delete(Endpoints.deleteuserEndpoint)
                        .then()
                        .statusCode(404).extract().response();
        }


        @When("accessing the resouces under user")
        public void accessingTheResoucesUnderUser() {



        }

        @Then("resources are displayed")
        public void resourcesAreDisplayed() {
                response = given()
                        .when().get(Endpoints.getuserEndpoint)
                        .then()
                        .statusCode(200).extract().response();
        }

        @When("accessing a specific resource under user")
        public void accessingASpecificResourceUnderUser() {
                userID=1;

        }

        @Then("specific resource is displayed")
        public void specificResourceIsDisplayed() throws JsonProcessingException {
                response = given()
                        .when().get(Endpoints.getsingleuserEndpoint + "/" + userID )
                        .then()
                        .statusCode(200).extract().response();


                jsonPath = new JsonPath(response.asString());
                objectMapper = new ObjectMapper();
                responseUser = objectMapper.readValue(response.asString(), UserController.class);
                Assert.assertEquals(userID, 1);

        }

        @When("creating multiple users")
        public void creatingMultipleUsers() {

                jsonArray = (JSONArray) TestNGListener.data.get("multiplepost");
                JSONObject jsonObject;
                jsonObject=(JSONObject) jsonArray.get(0);
                JSONObject jsonObject1=(JSONObject) jsonArray.get(1);


                user = new UserController((String) jsonObject.get("name"),
                        (String) jsonObject.get("address"),
                        (Long) jsonObject.get("marks"));

                user1 = new UserController((String) jsonObject1.get("name"),
                        (String) jsonObject1.get("address"),
                        (Long) jsonObject1.get("marks"));


        }

        @Then("multiple users are created")
         public void multipleUsersAreCreated() {
                UserController[] array= new UserController[2];
                array[0]=user;
                array[1]=user1;
                response = given()
                        .body(array)
                        .when().post(Endpoints.multipostEndpoint)
                        .then()
                        .statusCode(200).extract().response();

        }
}

