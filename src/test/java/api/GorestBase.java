package api;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.http.Header;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import properties.ApiProperties;

@TestInstance(Lifecycle.PER_CLASS)
public abstract class GorestBase {
    protected static final String BASE_URL = ApiProperties.getProperty("gorest.baseUrl");
    protected static final String USERS = BASE_URL + "/users";
    protected static final String POSTS = BASE_URL + "/posts";
    protected static final String COMMENTS = BASE_URL + "/comments";


    @BeforeAll
    public static void init() {
        RestAssured.baseURI = BASE_URL;
        RestAssured.requestSpecification = new RequestSpecBuilder()
            .log(LogDetail.HEADERS)
            .log(LogDetail.BODY)
            .setContentType(ContentType.JSON)
            .build()
            .header(new Header("Authorization", "Bearer " + ApiProperties.getProperty("gorest.token")));
        RestAssured.responseSpecification = new ResponseSpecBuilder()
            .log(LogDetail.BODY)
            .expectContentType(ContentType.JSON)
            .expectStatusCode(200)
            .build();
    }

    @AfterAll
    public static void close() {
        RestAssured.reset();
    }
}
