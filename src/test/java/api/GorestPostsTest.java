package api;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.responseSpecification;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertTrue;

import api.response.PostResponse;
import api.response.PostsResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

public class GorestPostsTest extends GorestBase {

    @Test
    public void getListAllPostsTest() {
        PostsResponse response = given()
            .when()
            .get(POSTS)
            .then()
            .assertThat()
            .spec(responseSpecification)
            .extract()
            .as(PostsResponse.class);

        assertAll("response",
            () -> assertTrue(response.getCode() == 200),
            () -> assertTrue(response.getMeta().getPagination().getPage() == 1),
            () -> assertTrue(response.getMeta().getPagination().getTotal() > 0),
            () -> assertTrue(response.getMeta().getPagination().getPages() > 0),
            () -> assertTrue(response.getData().isEmpty() == false));
    }

    @Test
    public void getPostTest() {
        {
            var id = Preconditions.createPost().getId();
            PostResponse response = given()
                .when()
                .get(POSTS + "/" + id)
                .then()
                .assertThat()
                .spec(responseSpecification)
                .extract()
                .as(PostResponse.class);

            assertAll("response",
                () -> assertTrue(response.getCode() == 200),
                () -> assertTrue(response.getMeta() == null),
                () -> assertTrue(response.getData().isNotNull())
            );
        }
    }

    @Test
    public void postPostTest() {
        var userId = Preconditions.createUser();
        PostBody postBody = new PostBody(userId);
        PostResponse response = given()
            .body(postBody)
            .when()
            .post(POSTS)
            .then()
            .spec(responseSpecification)
            .extract()
            .as(PostResponse.class);
        assertAll("response",
            () -> assertTrue(response.getCode() == 201, "rsCode"),
            () -> assertTrue(response.getData().equals(postBody), "rsData"));
    }

    @Test
    public void putPostTest() {
        PostBody oldBody = Preconditions.createPost();
        PostBody newPosBody = new PostBody(oldBody.getUserId());
        newPosBody.setId(oldBody.getId());
        PostResponse response = given()
            .body(newPosBody)
            .when()
            .post(POSTS)
            .then()
            .spec(responseSpecification)
            .extract()
            .as(PostResponse.class);
        assertAll("response",
            () -> assertTrue(response.getCode() == 201, "rsCode"),
            () -> assertTrue(response.getData().equals(newPosBody), "rsData"));
    }

    @Test
    public void deletePostTest() {
        PostBody post = Preconditions.createPost();
        PostResponse response = given()
            .when()
            .delete(POSTS + "/" + post.getId())
            .then()
            .spec(responseSpecification)
            .extract()
            .as(PostResponse.class);
        assertAll("response",
            () -> assertTrue(response.getCode() == 204, "rsCode"),
            () -> assertTrue(response.getData() == null)
        );
    }
}
