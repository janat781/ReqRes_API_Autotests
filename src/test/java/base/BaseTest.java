package base;

import config.Config;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import org.junit.jupiter.api.BeforeEach;
import io.restassured.specification.ResponseSpecification;
import io.restassured.http.ContentType;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;

public class BaseTest {

    protected RequestSpecification requestSpecification;
    protected ResponseSpecification responseSpecification;

    @BeforeEach
    public void setUp() {

        RestAssured.baseURI = Config.getBaseUrl();

        requestSpecification =
                new RequestSpecBuilder()
                        .addHeader("x-api-key", Config.getApiKey())
                        .setContentType(ContentType.JSON)
                        .setAccept(ContentType.JSON)
                        .log(LogDetail.ALL)
                        .build();

        RestAssured.filters(new AllureRestAssured());

        responseSpecification =
                new ResponseSpecBuilder()
                        .log(LogDetail.ALL)
                        .build();
    }
}