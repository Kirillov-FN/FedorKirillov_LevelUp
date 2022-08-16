package api;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.responseSpecification;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertTrue;

import api.response.CommentResponse;
import api.response.CommentsResponse;
import org.junit.jupiter.api.Test;

public class GorestCommentsTests extends GorestBase {

    @Test
    public void getCommentsTest() {
        CommentsResponse response = given()
            .when()
            .get(COMMENTS)
            .then()
            .spec(responseSpecification)
            .extract()
            .as(CommentsResponse.class);
        assertAll("response",
            () -> assertTrue(response.getCode() == 200),
            () -> assertTrue(response.getData().size() > 0)
        );
    }

    @Test
    public void getCommentTest() {
        CommentsBody comment = Preconditions.createComment();
        CommentResponse response = given()
            .when()
            .get(COMMENTS + "/" + comment.getId())
            .then()
            .spec(responseSpecification)
            .extract()
            .as(CommentResponse.class);
        assertAll("response",
            () -> assertTrue(response.getCode() == 200),
            () -> assertTrue(response.getData().equals(comment))
        );
    }

    @Test
    public void postCommentTest() {
        PostBody post = Preconditions.createPost();
        CommentsBody rq = new CommentsBody(post.getId());
        CommentResponse response = given()
            .body(rq)
            .when()
            .post(COMMENTS)
            .then()
            .spec(responseSpecification)
            .extract()
            .as(CommentResponse.class);
        assertAll("response",
            () -> assertTrue(response.getCode() == 201),
            () -> assertTrue(response.getData().equals(rq))
        );
    }

    @Test
    public void putCommentTest() {
        CommentsBody oldComment = Preconditions.createComment();
        CommentsBody newComment = new CommentsBody(oldComment.getPostId());
        newComment.setId(oldComment.getId());
        CommentResponse response = given()
            .body(newComment)
            .when()
            .put(COMMENTS + "/" + newComment.getId())
            .then()
            .spec(responseSpecification)
            .extract()
            .as(CommentResponse.class);
        assertAll("response",
            () -> assertTrue(response.getCode() == 200, "RsCode"),
            () -> assertTrue(response.getData().equals(newComment), "RsData")
        );
    }

    @Test
    public void deleteCommentTest() {
        long id = Preconditions.createComment().getId();
        CommentResponse response = given()
            .when()
            .delete(COMMENTS + "/" + id)
            .then()
            .spec(responseSpecification)
            .extract()
            .as(CommentResponse.class);
        assertAll("response",
            () -> assertTrue(response.getCode() == 204, "RsCode"),
            () -> assertTrue(response.getData() == null)
        );
    }
}
