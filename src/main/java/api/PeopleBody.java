package api;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.javafaker.Faker;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class PeopleBody {
    @JsonInclude(Include.NON_DEFAULT)
    private long id;
    private String name;
    private String gender;
    private String email;
    private String status;
    @JsonProperty("created_at")
    private String createdAt;
    @JsonProperty("updated_at")
    private String updatedAt;

    @JsonIgnore
    public boolean isNotNull() {
        if (id != 0 || !name.isEmpty() || !email.isEmpty() || !gender.isEmpty() || !status.isEmpty()) {
            return true;
        }
        return false;
    }

    public PeopleBody() {
        Faker faker = new Faker();
        this.name = faker.name().name();
        this.gender = faker.demographic().sex().toLowerCase(Locale.ROOT);
        this.email = faker.internet().emailAddress();
        List<String> statusList = new ArrayList<String>();
        statusList.add("active");
        statusList.add("inactive");
        this.status = statusList.get(new SecureRandom().nextInt(statusList.size()));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PeopleBody)) {
            return false;
        }
        PeopleBody that = (PeopleBody) o;
        return Objects.equals(name, that.name) && gender.equals(that.gender) && Objects.equals(email,
            that.email) && status.equals(that.status) && Objects.equals(createdAt, that.createdAt)
            && Objects.equals(updatedAt, that.updatedAt);
    }

}
