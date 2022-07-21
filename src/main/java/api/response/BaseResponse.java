package api.response;

import api.Meta;
import lombok.Getter;
import lombok.Setter;

public class BaseResponse {
    @Getter
    @Setter
    Integer code;
    @Getter
    @Setter
    Meta meta;
}
