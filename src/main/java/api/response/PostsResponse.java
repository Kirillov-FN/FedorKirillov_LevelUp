package api.response;

import api.PostBody;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

public class PostsResponse extends BaseResponse {
    @Getter
    @Setter
    public List<PostBody> data;
}
