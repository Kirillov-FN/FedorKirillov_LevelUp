package api.response;

import api.PostBody;
import lombok.Getter;
import lombok.Setter;

public class PostResponse extends BaseResponse {
    @Getter
    @Setter
    public PostBody data;
}
