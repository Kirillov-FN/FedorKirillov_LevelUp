package api;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class Pagination {
    Integer total;
    Integer pages;
    Integer page;
    Integer limit;
}
