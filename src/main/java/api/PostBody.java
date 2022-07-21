package api;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.javafaker.Faker;
import java.util.Objects;
import lombok.Getter;
import lombok.Setter;

public class PostBody {

    @Getter
    @Setter
    @JsonInclude(Include.NON_DEFAULT)
    private long id;
    @Getter
    @Setter
    @JsonProperty("user_id")
    private long userId;
    @Getter
    @Setter
    private String title;
    @Getter
    @Setter
    private String body;
    @Getter
    @Setter
    @JsonInclude(Include.NON_NULL)
    @JsonProperty("created_at")
    private String createdAt;
    @Getter
    @Setter
    @JsonProperty("updated_at")
    @JsonInclude(Include.NON_NULL)
    private String updatedAt;

    @JsonCreator
    public PostBody() {
    }

    @JsonIgnore
    public PostBody(long userId) {
        Faker faker = new Faker();
        setUserId(userId);
        setTitle(faker.howIMetYourMother().character());
        setBody(faker.howIMetYourMother().catchPhrase());
    }

    @JsonIgnore
    public boolean isNotNull() {
        if (id != 0 || userId != 0 || !title.isEmpty() || !body.isEmpty()) {
            return true;
        }
        return false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PostBody)) {
            return false;
        }
        PostBody postBody = (PostBody) o;
        return userId == postBody.userId && Objects.equals(title, postBody.title) && Objects.equals(
            body, postBody.body) && Objects.equals(createdAt, postBody.createdAt) && Objects.equals(
            updatedAt, postBody.updatedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, title, body, createdAt, updatedAt);
    }
}
