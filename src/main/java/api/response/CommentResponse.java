package api.response;

import api.CommentsBody;
import lombok.Getter;
import lombok.Setter;

public class CommentResponse extends BaseResponse {
    @Getter
    @Setter
    private CommentsBody data;
}
