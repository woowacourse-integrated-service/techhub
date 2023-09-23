package com.integrated.techhub.auth.presentation;

import com.integrated.techhub.common.acceptance.AcceptanceTest;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import static com.integrated.techhub.member.fixture.MemberFixture.무민_회원가입_요청;
import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;

class AuthControllerTest extends AcceptanceTest {

    @Test
    void signUp() {
        //given
        var body = 무민_회원가입_요청;

        //when
        var 응답 = given()
                .body(body)
                .contentType(JSON)
                .when()
                .post("/auth/sign-up")
                .then()
                .extract();

        //then
        응답.response().then().statusCode(HttpStatus.CREATED.value());
    }

}
