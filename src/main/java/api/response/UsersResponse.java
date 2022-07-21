package api.response;

import api.PeopleBody;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

public class UsersResponse extends BaseResponse {
    @Getter
    @Setter
    public List<PeopleBody> data;
}
