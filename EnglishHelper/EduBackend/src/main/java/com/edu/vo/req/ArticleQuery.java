package com.edu.vo.req;

import lombok.Data;

@Data
public class ArticleQuery {
    private Integer articleType;
    private String query;
}
