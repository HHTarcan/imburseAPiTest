package com.imburse.step_definitions;

import com.imburse.utilities.ConfigurationReader;
import com.imburse.utilities.CreateHMAC;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.testng.Assert;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

import static io.restassured.RestAssured.given;

public class CreateanOrder {
    Response resGetBody;
    String path;
    private Response response;
    String accessToken;
    String tenantId = "6423ae63-59b6-4986-a949-c910ac622471";
    String SchemeId = "654EB81FF7F07F7CF5A1EE3FF6972E90";
    String requestBodyOr = "\"{\\\"orderRef\\\":\\\"Htarcan\\\",\\\"metadata\\\":{\\\"key1meta\\\":\\\"jkl\\\",\\\"key2meta\\\":\\\"mno\\\",\\\"key3meta\\\":\\\"prs\\\"},\\\"customerDefaults\\\":{\\\"customerRef1\\\":{\\\"financialInstrumentId\\\":\\\"123456\\\",\\\"schemeId\\\":\\\"654EB81FF7F07F7CF5A1EE3FF6972E90\\\",\\\"metadata\\\":{\\\"key1ref1\\\":\\\"a123\\\",\\\"key2ref1\\\":\\\"b456\\\",\\\"key3ref1\\\":\\\"c789\\\"}},\\\"customerRef2\\\":{\\\"financialInstrumentId\\\":\\\"123456\\\",\\\"schemeId\\\":\\\"654EB81FF7F07F7CF5A1EE3FF6972E90\\\",\\\"metadata\\\":{\\\"key1ref2\\\":\\\"a123\\\",\\\"key2ref2\\\":\\\"b456\\\",\\\"key3ref2\\\":\\\"c789\\\"}},\\\"customerRef3\\\":{\\\"financialInstrumentId\\\":\\\"123456\\\",\\\"schemeId\\\":\\\"654EB81FF7F07F7CF5A1EE3FF6972E90\\\",\\\"metadata\\\":{\\\"key1ref3\\\":\\\"a123\\\",\\\"key2ref3\\\":\\\"b456\\\",\\\"key3ref3\\\":\\\"c789\\\"}}}}\"";
    String requestBodyInstructions = "\"{\\\"orderRef\\\":\\\"Htarcan\\\",\\\"instructions\\\":[{\\\"instructionRef\\\":\\\"HHtarcanA\\\",\\\"customerRef\\\":\\\"HHtarcanCustomA\\\",\\\"direction\\\":\\\"DEBIT\\\",\\\"financialInstrumentId\\\":\\\"123456\\\",\\\"amount\\\":\\\"200.05\\\",\\\"currency\\\":\\\"EUR\\\",\\\"country\\\":\\\"IE\\\",\\\"settledByDate\\\":\\\"2022-10-02\\\",\\\"schemeId\\\":\\\"654EB81FF7F07F7CF5A1EE3FF6972E90\\\",\\\"metadata\\\":{\\\"key1\\\":\\\"abc\\\",\\\"key2\\\":\\\"def\\\",\\\"key3\\\":\\\"ghi\\\"}},{\\\"instructionRef\\\":\\\"HHtarcanB\\\",\\\"customerRef\\\":\\\"HHtarcanCustomB\\\",\\\"direction\\\":\\\"DEBIT\\\",\\\"financialInstrumentId\\\":\\\"123456\\\",\\\"amount\\\":\\\"201.05\\\",\\\"currency\\\":\\\"EUR\\\",\\\"country\\\":\\\"IE\\\",\\\"settledByDate\\\":\\\"2022-10-03\\\",\\\"schemeId\\\":\\\"654EB81FF7F07F7CF5A1EE3FF6972E90\\\",\\\"metadata\\\":{\\\"key1ins1\\\":\\\"abc\\\",\\\"key2ins1\\\":\\\"def\\\",\\\"key3ins1\\\":\\\"ghi\\\"}},{\\\"instructionRef\\\":\\\"HHtarcanC\\\",\\\"customerRef\\\":\\\"HHtarcanCustomC\\\",\\\"direction\\\":\\\"DEBIT\\\",\\\"financialInstrumentId\\\":\\\"123456\\\",\\\"amount\\\":\\\"202.05\\\",\\\"currency\\\":\\\"EUR\\\",\\\"country\\\":\\\"IE\\\",\\\"settledByDate\\\":\\\"2022-10-05\\\",\\\"schemeId\\\":\\\"654EB81FF7F07F7CF5A1EE3FF6972E90\\\",\\\"metadata\\\":{\\\"key1ins2\\\":\\\"abc\\\",\\\"key2ins2\\\":\\\"def\\\",\\\"key3ins2\\\":\\\"ghi\\\"}}],\\\"metadata\\\":{\\\"key1meta\\\":\\\"jkl\\\",\\\"key2meta\\\":\\\"mno\\\",\\\"key3meta\\\":\\\"prs\\\"},\\\"customerDefaults\\\":{\\\"customerRef1\\\":{\\\"financialInstrumentId\\\":\\\"123456\\\",\\\"schemeId\\\":\\\"654EB81FF7F07F7CF5A1EE3FF6972E90\\\",\\\"metadata\\\":{\\\"key1ref1\\\":\\\"a123\\\",\\\"key2ref1\\\":\\\"b456\\\",\\\"key3ref1\\\":\\\"c789\\\"}},\\\"customerRef2\\\":{\\\"financialInstrumentId\\\":\\\"123456\\\",\\\"schemeId\\\":\\\"654EB81FF7F07F7CF5A1EE3FF6972E90\\\",\\\"metadata\\\":{\\\"key1ref2\\\":\\\"a123\\\",\\\"key2ref2\\\":\\\"b456\\\",\\\"key3ref2\\\":\\\"c789\\\"}},\\\"customerRef3\\\":{\\\"financialInstrumentId\\\":\\\"123456\\\",\\\"schemeId\\\":\\\"654EB81FF7F07F7CF5A1EE3FF6972E90\\\",\\\"metadata\\\":{\\\"key1ref3\\\":\\\"a123\\\",\\\"key2ref3\\\":\\\"b456\\\",\\\"key3ref3\\\":\\\"c789\\\"}}}}\"";
    private Response respGetOrder;
    private Response respRequestBody2;
    private Response respMetadata;
    private Response respMetadataV;

    @Before
    public void before(Scenario scenario) {
    }

    @Given("authorized with access token from the endpoint {string}")
    public void authorizedWithAccessTokenFromTheEndpoint(String p) throws NoSuchAlgorithmException, UnsupportedEncodingException {

        CreateHMAC createHMAC = new CreateHMAC();
        RestAssured.baseURI = ConfigurationReader.get("baseURI");
        path = p;
        String token = createHMAC.createToken("");
        response = given().header("Authorization", token).contentType(ContentType.JSON).
                post("/identity/hmac")
                .then().extract().response();
        accessToken = response.jsonPath().get("accessToken").toString();
        System.out.println("Access: " + accessToken);
    }

    @When("send the post request to end point {string} with orderRef {string}")
    public void sendThePostRequestToEndPointWithOrderRef(String orderRef, String arg1) {
        given().log().all().accept("application/json").
                header("Authorization", "bearer " + accessToken).
                headers("x-account-id", ConfigurationReader.get("AccountId"), "x-tenant-id", ConfigurationReader.get("TenantId")).
                body(requestBodyOr).
                    when().log().all().
                        post("path").then().
                          extract().response();
        System.out.println(response.getStatusLine());
    }

    @And("send the get order request to end point")
    public void sendTheGetOrderRequestToEndPoint() {

        response=   given().log().all().accept(ContentType.JSON).
        header("Authorization", "bearer " + accessToken).
        headers("x-account-id", ConfigurationReader.get("AccountId"), "x-tenant-id", ConfigurationReader.get("TenantId"),"orderRef","Htarcan").

            when().
                get(ConfigurationReader.get("getOrderManagement"));
        System.out.println(response.statusCode());
        System.out.println(response.jsonPath().getString("errors.errorCode"));
    }


    @Then("status code should be {int}")
    public void statusCodeShouldBe(int statusCode) {
        if(response.getStatusCode()!=200 && response.getStatusCode()!=201 && response.getStatusCode()!=202){
            System.out.println(response.jsonPath().getString("errors.errorCode"));
            System.out.println(response.getStatusLine());
        }
        System.out.println(response.jsonPath().getString("errors.errorCode"));
        Assertions.assertEquals(statusCode, response.getStatusCode());
    }

    @Then("verify that schemeId is {string}")
    public void verifyThatSchemeIdIs(String schemeId) {
        Assert.assertEquals(respGetOrder.jsonPath().get("customerDefaults.customerRef1.scheme.schemeId"), "654EB81FF7F07F7CF5A1EE3FF6972E90");
    }

    @When("send the post request to end point with send the orderRef as {string}")
    public void sendThePostRequestToEndPointWithSendTheOrderRefAs(String p) {

        String requestBodyReplace = requestBodyOr.replace("Htarcan", "    ");

        given().log().all().accept("application/json").contentType("application/json").
                header("Authorization", "bearer " + accessToken).
                headers("x-account-id", ConfigurationReader.get("AccountId"), "x-tenant-id", ConfigurationReader.get("TenantId"))
                .body(requestBodyReplace).
                when().log().all().
                post("path").then().
                extract().response();
        System.out.println(response.statusCode());
        System.out.println(response.getStatusLine());
        System.out.println(response.jsonPath().getString("errors.errorCode"));

    }

    @Then("send the order reference as b {string}")
    public void sendTheOrderReferenceAsB(String orderRef) {

        String requestBodyReplace2 = requestBodyOr.replace("Htarcan", "?asdasd/*/* 1231231231");

        given().log().all().accept("application/json").contentType("application/json").
                header("Authorization", "bearer " + accessToken).
                headers("x-account-id", ConfigurationReader.get("AccountId"), "x-tenant-id", ConfigurationReader.get("TenantId")).
                body(requestBodyReplace2).
                when().log().all().
                post("path").then().
                extract().response();
        System.out.println(response.statusCode());
        System.out.println(response.jsonPath().getString("errors.errorCode"));
        System.out.println(response.getStatusLine());
    }

    @When("send the order reference as a {string}")
    public void sendTheOrderReferenceAsA(String orderRef) {

        String requestBodyReplace3 = requestBodyOr.replace("Htarcan", "_asdasd---_.asdasdasrtytryrtrtytriuhihidhgdsfghshkjdsfhgks");

        given().log().all().accept("application/json").contentType("application/json").
                header("Authorization", "bearer " + accessToken).
                headers("x-account-id", ConfigurationReader.get("AccountId"), "x-tenant-id", ConfigurationReader.get("TenantId")).
                    body(requestBodyReplace3).
                        when().log().all().
                         post("path").then().
                             extract().response();
        System.out.println(response.statusCode());
        System.out.println(response.jsonPath().getString("errors.errorCode"));
        System.out.println(requestBodyReplace3);

    }


    @When("create another order with same orderRef {string}")
    public void createAnotherOrderWithSameOrderRef(String arg0) {

        String requestBodyOr2 = requestBodyOr.replace("Htarcan", "Htarcan");

        given().log().all().accept("application/json").contentType("application/json").
                header("Authorization", "bearer " + accessToken).
                headers("x-account-id", ConfigurationReader.get("AccountId"), "x-tenant-id", ConfigurationReader.get("TenantId")).
                        body(requestBodyOr2).
                            when().log().all().
                                post(path).then().
                                    extract().response();
        System.out.println(response.statusCode());
        System.out.println(response.jsonPath().getString("errors.errorCode"));

    }
    @Then("response status should be {int}")

    @When("first metadata key {string} replace with value {string}")
    public void firstMetadataKeyReplaceWithValue(String arg0, String arg1) {

        String requestodyMetadata ="\"{\\\"sdfsfsfioerfiejfoijeiejrfjerofjeoifjeojfeorfjerionjdvmnERTERTERTSF\\\":\\\"jkl\\\",\\\"key2meta\\\":\\\"mno\\\",\\\"key3meta\\\":\\\"prs\\\"}\"";

        response=given().log().all().accept("application/json").contentType("application/json").
                header("Authorization", "bearer " + accessToken).
                 headers("x-account-id", ConfigurationReader.get("AccountId"), "x-tenant-id", ConfigurationReader.get("TenantId"), "orderRef", "Htarcan").
                          body(requestodyMetadata).
                             when().log().all().
                                    put(ConfigurationReader.get("putMetadata")).then().
                                              extract().response();
        System.out.println(response.body().asString());
        System.out.println(response.statusCode());
        System.out.println(response.jsonPath().getString("errors.errorCode"));

    }

    @When("first metadata value {string} replace with value {string}")
    public void firstMetadataValueReplaceWithValue(String arg0, String arg1) {

        String requestodyMetadata = " \"{\\\"key1meta\\\":\\\"\\\"sdfsfsfioerfiejfoijeiejrfjerofjeoifjeojfeorfjerionjdvmnERTERTERTSF\\\\\\\"\\\",\\\"key2meta\\\":\\\"mno\\\",\\\"key3meta\\\":\\\"prs\\\"}\"";

        response = given().log().all().accept("application/json").contentType("application/json").
                header("Authorization", "bearer " + accessToken).
                headers("x-account-id", ConfigurationReader.get("AccountId"), "x-tenant-id", ConfigurationReader.get("TenantId")).
                     body(requestodyMetadata).
                         when().log().all().
                           put(ConfigurationReader.get("putMetadata")).then().
                extract().response();

        System.out.println(response.statusCode());
        System.out.println(response.jsonPath().getString("errors.errorCode"));

    }

    @And("first metadata key {string} and second metadata key replace with value {string}")
    public void firstMetadataKeyAndSecondMetadataKeyReplaceWithValue(String arg0, String key) {

        String requestodyMetadata = "\"{\\\"key1meta\\\":\\\"jkl\\\",\\\"key1meta\\\":\\\"mno\\\",\\\"key3meta\\\":\\\"prs\\\"}\"";

        Response response = given().log().all().accept("application/json").contentType("application/json").
                header("Authorization", "bearer " + accessToken).
                headers("x-account-id", ConfigurationReader.get("AccountId"), "x-tenant-id", ConfigurationReader.get("TenantId")).
                            body(requestodyMetadata).
                              when().log().all().
                                  put(ConfigurationReader.get("putMetadata")).
                                    then().extract().response();

        System.out.println(response.statusCode());
        System.out.println(response.jsonPath().getString("errors.errorCode"));

    }

    @And("first metadata value {string} replace with value null")
    public void firstMetadataValueReplaceWithValueNull(String value) {

        String requestodyMetadataNull = "\"{\\\"key1meta\\\":null,\\\"key1meta\\\":\\\"mno\\\",\\\"key3meta\\\":\\\"prs\\\"}\"";
        Response response = given().log().all().accept("application/json").contentType("application/json").
                header("Authorization", "bearer " + accessToken).
                headers("x-account-id", ConfigurationReader.get("AccountId"), "x-tenant-id", ConfigurationReader.get("TenantId")).
                body(requestodyMetadataNull).
                           when().log().all().
                               put(ConfigurationReader.get("putMetadata")).then().extract().response();
        System.out.println(response.statusCode());
        System.out.println(response.jsonPath().getString("errors.errorCode"));

    }
    // instructions
    //functions
    @When("send the post request to end point {string} with instructionRef {string} and customerRef {string}")
    public void sendThePostRequestToEndPointWithInstructionRefAndCustomerRef(String arg0, String arg1, String arg2) {

        given().log().all().contentType("application/json").accept(ContentType.JSON).
                header("Authorization", "bearer " + accessToken).
                headers("x-account-id", ConfigurationReader.get("AccountId"), "x-tenant-id", ConfigurationReader.get("TenantId")).
                        body(requestBodyInstructions).
                            when().post("path").then().extract().response();

        System.out.println(response.statusCode());
        System.out.println(response.jsonPath().getString("errors.errorCode"));

    }

    @And("send the get instruction request to end point")
    public void sendTheGetInstructionRequestToEndPoint() {

         response = given().log().all().contentType(ContentType.JSON).
                header("Authorization", "bearer " + accessToken).
                header("orderRef", "Htarcan").
                header("instructionRef", "HHtarcanA").
                header("x-account-id", ConfigurationReader.get("AccountId")).
                header("x-tenant-id", ConfigurationReader.get("TenantId")).
                 when().get(ConfigurationReader.get("getInstruction")).then().extract().response();
        System.out.println(response.statusCode());
        System.out.println(response.getStatusLine());

    }

    @When("send the post request empty instructionRef")
    public void sendThePostRequestEmptyInstructionRef() {

        String requestBodyReplaceInst = requestBodyInstructions.replace("HHtarcanA", "    ");

        response = given().log().all().accept("application/json").contentType("application/json").
                header("Authorization", "bearer " + accessToken).
                header("x-account-id", ConfigurationReader.get("AccountId")).
                header("x-tenant-id", ConfigurationReader.get("TenantId")).body(requestBodyReplaceInst).
                when().log().all().
                post(path).then().
                extract().response();
        System.out.println(response.statusCode());
        System.out.println(response.jsonPath().getString("errors.errorCode"));
    }

    @When("send the post request with invalid chars instructionRef {string}")
    public void sendThePostRequestWithInvalidCharsInstructionRef(String arg0) {

        String requestBodyReplaceInst = requestBodyInstructions.replace("HHtarcanA", "?asdasd/*/* 1231231231");

        response = given().log().all().accept("application/json").contentType("application/json").
                header("Authorization", "bearer " + accessToken).
                header("x-account-id", ConfigurationReader.get("AccountId")).
                header("x-tenant-id", ConfigurationReader.get("TenantId")).body(requestBodyReplaceInst).
                when().log().all().
                post(path).then().
                extract().response();
        System.out.println(response.statusCode());
        System.out.println(response.jsonPath().getString("errors.errorCode"));
    }

    @When("send the post request with too long instructionRef {string}")
    public void sendThePostRequestWithTooLongInstructionRef(String arg0) {

        String requestBodyReplaceInst = requestBodyInstructions.replace("HHtarcanA", "_asdasd---_.asdasdasrtytryrtrtytriuhihidhgdsfghshkjdsfhgks");

        response = given().log().all().accept("application/json").contentType("application/json").
                header("Authorization", "bearer " + accessToken).
                header("x-account-id", ConfigurationReader.get("AccountId")).
                header("x-tenant-id", ConfigurationReader.get("TenantId")).body(requestBodyReplaceInst).
                when().log().all().
                post(path).then().
                extract().response();
        System.out.println(response.statusCode());
    }

    // customer referance negative tests

    @When("send the post request to create instruction end point {string} with instructionRef {string} and customerRef {string}")
    public void sendThePostRequestToCreateInstructionEndPointWithInstructionRefAndCustomerRef(String arg0, String arg1, String arg2) {

        given().log().all().contentType(ContentType.JSON).
                header("Authorization", "bearer " + accessToken).
                header("orderRef", "Htarcan").
                header("x-account-id", ConfigurationReader.get("AccountId")).
                header("x-tenant-id", ConfigurationReader.get("TenantId")).body(requestBodyInstructions).
                when().post("https://sandbox-api.imbursepayments.com/v1/order-management/Htarcan/instruction").then().extract().response();
        System.out.println(response.statusCode());
        System.out.println(response.jsonPath().getString("orderRef"));


    }

    @And("send the post request to end point {string} with customerRef {string} and instructionRef {string}")
    public void sendThePostRequestToEndPointWithCustomerRefAndInstructionRef(String arg0, String arg1, String arg2) {
        given().log().all().contentType(ContentType.JSON).
                header("Authorization", "bearer " + accessToken).
                header("orderRef", "Htarcan").
                header("x-account-id", ConfigurationReader.get("AccountId")).
                header("x-tenant-id", ConfigurationReader.get("TenantId")).
                when().get("https://sandbox-api.imbursepayments.com/v1/order-management/Htarcan").then().extract().response();
        System.out.println(response.statusCode());
        System.out.println(response.asString());
    }

    @Then("verify that customerRef is matched")
    public void verifyThatCustomerRefIsMatched() {
    }
// negative

    @And("send the post request with empty customerRef")
        public void sendThePostRequestWithEmptyCustomerRef() {

        String requestBodInstruction1 = requestBodyInstructions.replace("HHtarcanCustomA","       ");
        response=given().log().all().accept("application/json").contentType("application/json").
                header("Authorization", "bearer " + accessToken).
                header("orderRef", "Htarcan").
                header("x-account-id", ConfigurationReader.get("AccountId")).
                header("x-tenant-id", ConfigurationReader.get("TenantId")).
                        body(requestBodInstruction1).
                when().log().all().
                post("https://sandbox-api.imbursepayments.com/v1/order-management/Htarcan/instruction").then().extract().response();

        System.out.println(response.getStatusLine());
        System.out.println(response.statusCode());

    }

    @And("send the post request with invalid chars customerRef {string}")
    public void sendThePostRequestWithInvalidCharsCustomerRef(String arg0) {

        String requestBodInstruction2 = requestBodyOr.replace("HHtarcanCustomA", "?asdasd/*/* 1231231231");

        response=given().log().all().accept("application/json").contentType("application/json").
                header("Authorization", "bearer " + accessToken).
                header("orderRef", "Htarcan").
                header("x-account-id", ConfigurationReader.get("AccountId")).
                header("x-tenant-id", ConfigurationReader.get("TenantId")).
                body(requestBodInstruction2).
                when().log().all().
                post("https://sandbox-api.imbursepayments.com/v1/order-management/Htarcan/instruction").then().extract().response();

        System.out.println(response.getStatusLine());
        System.out.println(response.statusCode());

    }

    @And("send the post request with too long customerRef {string}")
    public void sendThePostRequestWithTooLongCustomerRef(String arg0) {

        String requestBodInstruction3 = requestBodyOr.replace("HHtarcanCustomA", "_asdasd---_.asdasdasrtytryrtrtytriuhihidhgdsfghshkjdsfhgks");

        response=given().log().all().accept("application/json").contentType("application/json").
                header("Authorization", "bearer " + accessToken).
                header("orderRef", "Htarcan").
                header("x-account-id", ConfigurationReader.get("AccountId")).
                header("x-tenant-id", ConfigurationReader.get("TenantId")).
                body(requestBodInstruction3).
                when().log().all().
                post("https://sandbox-api.imbursepayments.com/v1/order-management/Htarcan/instruction").then().extract().response();

        System.out.println(response.getStatusLine());
        System.out.println(response.statusCode());

    }


    @When("succesfully create an order without instructions")
    public void succesfullyCreateAnOrderWithoutInstructions() {

        given().log().all().accept("application/json").contentType("application/json").
                header("Authorization", "bearer " + accessToken).
                header("x-account-id", ConfigurationReader.get("AccountId")).
                header("x-tenant-id", ConfigurationReader.get("TenantId")).body(requestBodyOr).
                when().log().all().
                post("path").then().
                extract().response();
    }
    @And("add the instruction to this order with  empty amount of money {string}")
    public void addTheInstructionToThisOrderWithEmptyAmountOfMoney(String arg0) {

        String instBody="\"{\\\"instructionRef\\\":\\\"HHtarcanA\\\",\\\"customerRef\\\":\\\"HHtarcanCustomA\\\",\\\"direction\\\":\\\"DEBIT\\\",\\\"financialInstrumentId\\\":\\\"123456\\\",\\\"amount\\\":\\\"\\\",\\\"currency\\\":\\\"EUR\\\",\\\"country\\\":\\\"IE\\\",\\\"settledByDate\\\":\\\"2022-10-02\\\",\\\"schemeId\\\":\\\"654EB81FF7F07F7CF5A1EE3FF6972E90\\\",\\\"metadata\\\":{\\\"key1\\\":\\\"abc\\\",\\\"key2\\\":\\\"def\\\",\\\"key3\\\":\\\"ghi\\\"}\"";
       response= given().log().all().accept("application/json").contentType("application/json").
                header("Authorization", "bearer " + accessToken).
                header("oderRef","Htarcan").
                header("x-account-id", ConfigurationReader.get("AccountId")).
                header("x-tenant-id", ConfigurationReader.get("TenantId")).body(instBody).
                when().log().all().
                post("https://sandbox-api.imbursepayments.com/v1/order-management/Htarcan/instruction").then().
                extract().response();
        System.out.println(response.getStatusLine());
        System.out.println(response.statusCode());

    }

    @And("add the instruction to this order with more than {int} digit amount of money {string}")
    public void addTheInstructionToThisOrderWithMoreThanDigitAmountOfMoney(int arg0, String arg1) {

        String instBodywiththree="\"{\\\"instructionRef\\\":\\\"HHtarcanA\\\",\\\"customerRef\\\":\\\"HHtarcanCustomA\\\",\\\"direction\\\":\\\"DEBIT\\\",\\\"financialInstrumentId\\\":\\\"123456\\\",\\\"amount\\\":\\\"210.012\\\",\\\"currency\\\":\\\"EUR\\\",\\\"country\\\":\\\"IE\\\",\\\"settledByDate\\\":\\\"2022-10-02\\\",\\\"schemeId\\\":\\\"654EB81FF7F07F7CF5A1EE3FF6972E90\\\",\\\"metadata\\\":{\\\"key1\\\":\\\"abc\\\",\\\"key2\\\":\\\"def\\\",\\\"key3\\\":\\\"ghi\\\"}\"";

        given().log().all().accept("application/json").contentType("application/json").
                header("Authorization", "bearer " + accessToken).
                header("oderRef","Htarcan").
                headers("x-account-id", ConfigurationReader.get("AccountId"),"x-tenant-id", ConfigurationReader.get("TenantId")).
                    body(instBodywiththree).
                       when().log().all().
                           post("https://sandbox-api.imbursepayments.com/v1/order-management/Htarcan/instruction");
         }


    @And("add the instruction to this order with zero amount of money {string}")
    public void addTheInstructionToThisOrderWithZeroAmountOfMoney(String arg0) {

        String instBodywithfour="\"{\\\"instructionRef\\\":\\\"HHtarcanA\\\",\\\"customerRef\\\":\\\"HHtarcanCustomA\\\",\\\"direction\\\":\\\"DEBIT\\\",\\\"financialInstrumentId\\\":\\\"123456\\\",\\\"amount\\\":\\\"0.00\\\",\\\"currency\\\":\\\"EUR\\\",\\\"country\\\":\\\"IE\\\",\\\"settledByDate\\\":\\\"2022-10-02\\\",\\\"schemeId\\\":\\\"654EB81FF7F07F7CF5A1EE3FF6972E90\\\",\\\"metadata\\\":{\\\"key1\\\":\\\"abc\\\",\\\"key2\\\":\\\"def\\\",\\\"key3\\\":\\\"ghi\\\"}\"";

        response=given().log().all().accept("application/json").contentType("application/json").
                header("Authorization", "bearer " + accessToken).
                header("oderRef","Htarcan").
                header("x-account-id", ConfigurationReader.get("AccountId")).
                header("x-tenant-id", ConfigurationReader.get("TenantId")).body(instBodywithfour).
                when().log().all().
                post("https://sandbox-api.imbursepayments.com/v1/order-management/Htarcan/instruction");
        System.out.println(response.statusCode());
        System.out.println(response.getStatusLine());
    }

    @And("add the instruction to this order with currency number {string}")
    public void addTheInstructionToThisOrderWithCurrencyNumber(String arg0) {
        String instBody="\"{\\\"instructionRef\\\":\\\"HHtarcanA\\\",\\\"customerRef\\\":\\\"HHtarcanCustomA\\\",\\\"direction\\\":\\\"DEBIT\\\",\\\"financialInstrumentId\\\":\\\"123456\\\",\\\"amount\\\":\\\"210.00\\\",\\\"currency\\\":\\\"978\\\",\\\"country\\\":\\\"IE\\\",\\\"settledByDate\\\":\\\"2022-10-02\\\",\\\"schemeId\\\":\\\"654EB81FF7F07F7CF5A1EE3FF6972E90\\\",\\\"metadata\\\":{\\\"key1\\\":\\\"abc\\\",\\\"key2\\\":\\\"def\\\",\\\"key3\\\":\\\"ghi\\\"}\"";

        response=given().log().all().accept("application/json").contentType("application/json").
                header("Authorization", "bearer " + accessToken).
                header("oderRef","Htarcan").
                headers("x-account-id", ConfigurationReader.get("AccountId"),"x-tenant-id", ConfigurationReader.get("TenantId")).
                body(instBody).
                when().log().all().
                post("https://sandbox-api.imbursepayments.com/v1/order-management/Htarcan/instruction");
        System.out.println(response.statusCode());
        System.out.println(response.getStatusLine());
    }

    @And("add the instruction to this order with currency {string}")
    public void addTheInstructionToThisOrderWithCurrency(String arg0) {

        String instBody="\"{\\\"instructionRef\\\":\\\"HHtarcanA\\\",\\\"customerRef\\\":\\\"HHtarcanCustomA\\\",\\\"direction\\\":\\\"DEBIT\\\",\\\"financialInstrumentId\\\":\\\"123456\\\",\\\"amount\\\":\\\"210.00\\\",\\\"currency\\\":\\\"EURO\\\",\\\"country\\\":\\\"IE\\\",\\\"settledByDate\\\":\\\"2022-10-02\\\",\\\"schemeId\\\":\\\"654EB81FF7F07F7CF5A1EE3FF6972E90\\\",\\\"metadata\\\":{\\\"key1\\\":\\\"abc\\\",\\\"key2\\\":\\\"def\\\",\\\"key3\\\":\\\"ghi\\\"}\"";
       Response responseCurrBody= given().log().all().accept("application/json").contentType("application/json").
                header("Authorization", "bearer " + accessToken).
                header("oderRef","Htarcan").
               headers("x-account-id", ConfigurationReader.get("AccountId"),"x-tenant-id", ConfigurationReader.get("TenantId")).
               body(instBody).
                when().log().all().
                post("/order-management/Htarcan/instruction").then().
                extract().response();

        System.out.println(responseCurrBody.asString());
        System.out.println(responseCurrBody.statusCode());
        System.out.println(responseCurrBody.jsonPath().getString("message"));
        //Assert.assertEquals(responseCurrBody.statusCode(),400);
    }

    @And("add the instruction to this order with country name {string}")
    public void addTheInstructionToThisOrderWithCountryName(String country) {

        String instBodyCountry=" \"{\\\"instructionRef\\\":\\\"HHtarcanA\\\",\\\"customerRef\\\":\\\"HHtarcanCustomA\\\",\\\"direction\\\":\\\"DEBIT\\\",\\\"financialInstrumentId\\\":\\\"123456\\\",\\\"amount\\\":\\\"210.00\\\",\\\"currency\\\":\\\"EUR\\\",\\\"country\\\":\\\"IEO\\\",\\\"settledByDate\\\":\\\"2022-10-02\\\",\\\"schemeId\\\":\\\"654EB81FF7F07F7CF5A1EE3FF6972E90\\\",\\\"metadata\\\":{\\\"key1\\\":\\\"abc\\\",\\\"key2\\\":\\\"def\\\",\\\"key3\\\":\\\"ghi\\\"}\"";

                Response responseCountryBody= given().log().all().accept("application/json").contentType("application/json").
                header("Authorization", "bearer " + accessToken).
                header("oderRef","Htarcan").
                        headers("x-account-id", ConfigurationReader.get("AccountId"),"x-tenant-id", ConfigurationReader.get("TenantId")).
                        body(instBodyCountry).
                when().log().all().
                post("/order-management/Htarcan/instruction").then().
                extract().response();

        System.out.println(responseCountryBody.statusCode());
        System.out.println(responseCountryBody.jsonPath().getString("errors.errorCode"));

    }


    @And("add the instruction to this order with direction {string}")
    public void addTheInstructionToThisOrderWithDirection(String arg0) {

        String instBodyDirection="\"{\\\"instructionRef\\\":\\\"HHtarcanA\\\",\\\"customerRef\\\":\\\"HHtarcanCustomA\\\",\\\"direction\\\":\\\"CREDIT\\\",\\\"financialInstrumentId\\\":\\\"123456\\\",\\\"amount\\\":\\\"210.00\\\",\\\"currency\\\":\\\"EUR\\\",\\\"country\\\":\\\"IE\\\",\\\"settledByDate\\\":\\\"2022-10-02\\\",\\\"schemeId\\\":\\\"654EB81FF7F07F7CF5A1EE3FF6972E90\\\",\\\"metadata\\\":{\\\"key1\\\":\\\"abc\\\",\\\"key2\\\":\\\"def\\\",\\\"key3\\\":\\\"ghi\\\"}\"";

       Response response= given().log().all().accept("application/json").contentType("application/json").
                header("Authorization", "bearer " + accessToken).
                header("oderRef","Htarcan").
                headers("x-account-id", ConfigurationReader.get("AccountId"),"x-tenant-id", ConfigurationReader.get("TenantId")).
                        body(instBodyDirection).
                          when().log().all().
                             post("/order-management/Htarcan/instruction").then().
                               extract().response();

        System.out.println(response.statusCode());
        System.out.println(response.getStatusLine());
        System.out.println(response.jsonPath().getString("errors.errorCode"));
    }


    @And("error Code will be {string}")
    public void errorCodeWillBe(String expectedErrorCode) {
        String actualErrorCode= response.jsonPath().getString("errors.errorCode");
        Assert.assertEquals(expectedErrorCode, actualErrorCode);
    }

}

























