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
import org.testng.Assert;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class CreateanInstruction {
    CreateanOrder createanOrder=new CreateanOrder();
    CreateHMAC createHMAC=new CreateHMAC();
    Response resGetBody;
    String path;
    private Response response;
    String accessToken;
    String accountId = "782f1b71-7ca4-4465-917f-68d58ffbec8b";
    String tenantId = "6423ae63-59b6-4986-a949-c910ac622471";
    String SchemeId = "654EB81FF7F07F7CF5A1EE3FF6972E90";

    public CreateanInstruction() throws NoSuchAlgorithmException {
    }

    @Before
    public void before(Scenario scenario) {
    }

//    @When("send the post request to end point {string} with instructionRef {string} and customerRef {string}")
//    public void sendThePostRequestToEndPointWithInstructionRefAndCustomerRef(String arg0, String arg1, String arg2) {
//
//        String requestBodyInstructions="\"{\\\"orderRef\\\":\\\"Htarcan\\\",\\\"instructions\\\":[{\\\"instructionRef\\\":\\\"HHtarcan1\\\",\\\"customerRef\\\":\\\"HHtarcanCustom1\\\",\\\"direction\\\":\\\"DEBIT\\\",\\\"financialInstrumentId\\\":\\\"123456\\\",\\\"amount\\\":\\\"200.05\\\",\\\"currency\\\":\\\"EUR\\\",\\\"country\\\":\\\"IE\\\",\\\"settledByDate\\\":\\\"2022-10-02\\\",\\\"schemeId\\\":\\\"654EB81FF7F07F7CF5A1EE3FF6972E90\\\",\\\"metadata\\\":{\\\"key1\\\":\\\"abc\\\",\\\"key2\\\":\\\"def\\\",\\\"key3\\\":\\\"ghi\\\"}},{\\\"instructionRef\\\":\\\"HHtarcan2\\\",\\\"customerRef\\\":\\\"HHtarcanCustom2\\\",\\\"direction\\\":\\\"DEBIT\\\",\\\"financialInstrumentId\\\":\\\"123456\\\",\\\"amount\\\":\\\"201.05\\\",\\\"currency\\\":\\\"EUR\\\",\\\"country\\\":\\\"IE\\\",\\\"settledByDate\\\":\\\"2022-10-03\\\",\\\"schemeId\\\":\\\"654EB81FF7F07F7CF5A1EE3FF6972E90\\\",\\\"metadata\\\":{\\\"key1ins1\\\":\\\"abc\\\",\\\"key2ins1\\\":\\\"def\\\",\\\"key3ins1\\\":\\\"ghi\\\"}},{\\\"instructionRef\\\":\\\"HHtarcan3\\\",\\\"customerRef\\\":\\\"HHtarcanCustom3\\\",\\\"direction\\\":\\\"DEBIT\\\",\\\"financialInstrumentId\\\":\\\"123456\\\",\\\"amount\\\":\\\"202.05\\\",\\\"currency\\\":\\\"EUR\\\",\\\"country\\\":\\\"IE\\\",\\\"settledByDate\\\":\\\"2022-10-05\\\",\\\"schemeId\\\":\\\"654EB81FF7F07F7CF5A1EE3FF6972E90\\\",\\\"metadata\\\":{\\\"key1ins2\\\":\\\"abc\\\",\\\"key2ins2\\\":\\\"def\\\",\\\"key3ins2\\\":\\\"ghi\\\"}}],\\\"metadata\\\":{\\\"key1meta\\\":\\\"jkl\\\",\\\"key2meta\\\":\\\"mno\\\",\\\"key3meta\\\":\\\"prs\\\"},\\\"customerDefaults\\\":{\\\"customerRef1\\\":{\\\"financialInstrumentId\\\":\\\"123456\\\",\\\"schemeId\\\":\\\"654EB81FF7F07F7CF5A1EE3FF6972E90\\\",\\\"metadata\\\":{\\\"key1ref1\\\":\\\"a123\\\",\\\"key2ref1\\\":\\\"b456\\\",\\\"key3ref1\\\":\\\"c789\\\"}},\\\"customerRef2\\\":{\\\"financialInstrumentId\\\":\\\"123456\\\",\\\"schemeId\\\":\\\"654EB81FF7F07F7CF5A1EE3FF6972E90\\\",\\\"metadata\\\":{\\\"key1ref2\\\":\\\"a123\\\",\\\"key2ref2\\\":\\\"b456\\\",\\\"key3ref2\\\":\\\"c789\\\"}},\\\"customerRef3\\\":{\\\"financialInstrumentId\\\":\\\"123456\\\",\\\"schemeId\\\":\\\"654EB81FF7F07F7CF5A1EE3FF6972E90\\\",\\\"metadata\\\":{\\\"key1ref3\\\":\\\"a123\\\",\\\"key2ref3\\\":\\\"b456\\\",\\\"key3ref3\\\":\\\"c789\\\"}}}}\"";
//
//        given().log().all(). contentType(ContentType.JSON).
//                header("Authorization", "bearer " + accessToken).
//                header("x-account-id", ConfigurationReader.get("AccountId")).
//                header("x-tenant-id", ConfigurationReader.get("TenantId")).body(requestBodyInstructions).
//                when().post(" https://sandbox-api.imbursepayments.com/v1/order-management").then().statusCode(202).extract().response();

//        System.out.println(response.statusCode());
//        System.out.println(response.asString());
//        Assert.assertEquals(response.statusCode(),201);





//    @When("send the post request to end point {string} with instructionRef {string} and customerRef {string}")
//    public void sendThePostRequestToEndPointWithInstructionRefAndCustomerRef(String arg0, String arg1, String arg2) {
//
//        String requestBodyInstructions="\"{\\\"instructionRef\\\":\\\"HHtarcan\\\",\\\"customerRef\\\":\\\"HHtarcan1\\\",\\\"direction\\\":\\\"DEBIT\\\",\\\"financialInstrumentId\\\":\\\"HHtarcanFinancialInst\\\",\\\"amount\\\":\\\"200.05\\\",\\\"currency\\\":\\\"EUR\\\",\\\"country\\\":\\\"IE\\\",\\\"settledByDate\\\":\\\"2021-08-05\\\",\\\"schemeId\\\":\\\"654EB81FF7F07F7CF5A1EE3FF6972E90\\\",\\\"metadata\\\":{\\\"key1instmeta\\\":\\\"stringmeta1\\\",\\\"key2instmeta\\\":\\\"stringmeta2\\\",\\\"key3instmeta\\\":\\\"stringmeta3\\\"}}\"";
//        path="https://sandbox-api.imbursepayments.com/v1/order-management";
//        Response instResBody= given().log().all(). contentType(ContentType.JSON).
//                header("Authorization", "bearer " + accessToken).
//                header("x-account-id", ConfigurationReader.get("AccountId")).
//                header("x-tenant-id", ConfigurationReader.get("TenantId")).body(requestBodyInstructions).
//                when().post(path).then().extract().response();
//
//        System.out.println(instResBody.statusCode());
//        System.out.println(instResBody.asString());
//        Assert.assertEquals(instResBody.statusCode(),201);
//    }
}




