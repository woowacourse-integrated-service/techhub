package com.integrated.techhub.mail.presentation;

import com.integrated.techhub.mail.dto.MailValidateRequest;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;

public class MailControllerSteps {

    public static ExtractableResponse<Response> 인증코드_유효한지_요청(MailValidateRequest body) {
        return given()
                .body(body)
                .contentType(JSON)
                .when()
                .post("/mail/validation")
                .then()
                .extract();
    }

}
