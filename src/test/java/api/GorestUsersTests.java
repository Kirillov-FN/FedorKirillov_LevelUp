package api;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.responseSpecification;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertTrue;

import api.response.BaseResponse;
import api.response.UserResponse;
import api.response.UsersResponse;
import java.util.Random;
import org.junit.jupiter.api.Test;

public class GorestUsersTests extends GorestBase {

    @Test
    public void getUsersTest() {
        BaseResponse response = given()
            .when()
            .get(USERS)
            .then()
            .assertThat()
            .spec(responseSpecification)
            .extract()
            .as(UsersResponse.class);

        assertAll("response",

            () -> assertTrue(response.getMeta().getPagination().getPage() == 1),
            () -> assertTrue(response.getMeta().getPagination().getTotal() > 0),
            () -> assertTrue(response.getMeta().getPagination().getPages() > 0)
        );
    }

    @Test
    public void getUserTest() {
        UserResponse response = given()
            .when()
            .get(USERS + "/" + new Random().nextInt(2380))
            .then()
            .assertThat()
            .spec(responseSpecification)
            .extract()
            .as(UserResponse.class);

        assertAll("response",
            () -> assertTrue(response.getCode() == 200),
            () -> assertTrue(response.getData().isNotNull()),
            () -> assertTrue(response.getMeta() == null)
        );
    }

    @Test
    public void postUserTest() {
        PeopleBody rq = new PeopleBody();
        UserResponse response = given()
            .body(rq)
            .when()
            .post(USERS)
            .then()
            .spec(responseSpecification)
            .extract()
            .as(UserResponse.class);
        assertAll(
            () -> assertTrue(response.getCode() == 201),
            () -> assertTrue(rq.equals(response.getData())));
    }

    @Test
    public void putUserTest() {
        var id = Preconditions.createUser();
        PeopleBody rq = new PeopleBody();
        rq.setId(id);
        UserResponse response = given()
            .body(rq)
            .when()
            .put(USERS + "/" + id)
            .then()
            .spec(responseSpecification)
            .extract()
            .as(UserResponse.class);
        assertAll(
            () -> assertTrue(response.getCode() == 200),
            () -> assertTrue(rq.equals(response.getData())));
    }

    @Test
    public void deleteUserTest() {
        var id = Preconditions.createUser();
        PeopleBody rq = new PeopleBody();
        UserResponse response = given()
            .when()
            .delete(USERS + "/" + id)
            .then()
            .spec(responseSpecification)
            .extract()
            .as(UserResponse.class);
        assertTrue(response.getCode() == 204);
    }
}
