package com.edu.vo.req;

import lombok.Data;

@Data
public class NewsQuery {
    private Integer articleType;
    private String query;
}
