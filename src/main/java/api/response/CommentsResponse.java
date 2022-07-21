package api.response;

import api.CommentsBody;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

public class CommentsResponse extends BaseResponse {
    @Getter
    @Setter
    private List<CommentsBody> data;
}
