package api;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.responseSpecification;

import api.response.CommentResponse;
import api.response.PostResponse;
import api.response.UserResponse;

public class Preconditions extends  GorestBase {

    public static long createUser() {
        PeopleBody rq = new PeopleBody();
        UserResponse response = given()
            .body(rq)
            .when()
            .post(BASE_URL + "/users")
            .then()
            .spec(responseSpecification)
            .extract()
            .as(UserResponse.class);
        return response.getData().getId();
    }

    public static PostBody createPost() {
        var id = createUser();
        PostBody rq = new PostBody(id);
        PostResponse response = given()
            .body(rq)
            .when()
            .post(BASE_URL + "/posts")
            .then()
            .spec(responseSpecification)
            .extract()
            .as(PostResponse.class);
        return response.getData();
    }

    public static CommentsBody createComment() {
        PostBody post = createPost();
        CommentsBody rq = new CommentsBody(post.getId());
        CommentResponse comment = given()
            .body(rq)
            .when()
            .post(BASE_URL + "/comments")
            .then()
            .spec(responseSpecification)
            .extract()
            .as(CommentResponse.class);
        return comment.getData();
    }
}
