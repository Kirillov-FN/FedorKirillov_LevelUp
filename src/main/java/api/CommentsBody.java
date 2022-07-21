package api;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.javafaker.Faker;
import java.util.Objects;
import lombok.Getter;
import lombok.Setter;

public class CommentsBody {
    @Getter
    @Setter
    @JsonInclude(Include.NON_DEFAULT)
    private long id;
    @Getter
    @Setter
    @JsonProperty("post_id")
    private long postId;
    @Getter
    @Setter
    private String name;
    @Getter
    @Setter
    private String email;
    @Getter
    @Setter
    private String body;
    @Getter
    @Setter
    @JsonInclude(Include.NON_DEFAULT)
    @JsonProperty("created_at")
    private String createdAt;
    @Getter
    @Setter
    @JsonInclude(Include.NON_DEFAULT)
    @JsonProperty("updated_at")
    private String updatedAt;

    @JsonCreator
    public CommentsBody() {
    }

    public CommentsBody(long postId) {
        Faker faker = new Faker();
        setPostId(postId);
        setName(faker.friends().character());
        setEmail(faker.internet().emailAddress());
        setBody(faker.buffy().bigBads());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CommentsBody)) {
            return false;
        }
        CommentsBody that = (CommentsBody) o;
        return postId == that.postId && Objects.equals(name, that.name) && Objects.equals(email,
            that.email) && Objects.equals(body, that.body);
    }

    @Override
    public int hashCode() {
        return Objects.hash(postId, name, email, body);
    }
}
