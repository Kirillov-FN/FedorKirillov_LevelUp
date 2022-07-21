package api;

import lombok.Getter;
import lombok.Setter;

public class Pagination {
    @Getter
    @Setter
    Integer total;
    @Getter
    @Setter
    Integer pages;
    @Getter
    @Setter
    Integer page;
    @Getter
    @Setter
    Integer limit;
}
