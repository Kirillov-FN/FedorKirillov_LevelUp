package api.response;

import api.PeopleBody;
import lombok.Getter;
import lombok.Setter;

public class UserResponse extends BaseResponse {

    @Getter
    @Setter
    public PeopleBody data;
}
