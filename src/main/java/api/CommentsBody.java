package api;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.javafaker.Faker;
import java.util.Objects;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class CommentsBody {
    @JsonInclude(Include.NON_DEFAULT)
    private long id;
    @JsonProperty("post_id")
    private long postId;
    private String name;
    private String email;
    private String body;
    @JsonInclude(Include.NON_DEFAULT)
    @JsonProperty("created_at")
    private String createdAt;
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

}
