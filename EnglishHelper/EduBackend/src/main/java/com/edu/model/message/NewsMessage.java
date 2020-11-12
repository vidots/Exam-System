package com.edu.model.message;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class NewsMessage extends BaseMessage{
    /**
     * 图文消息个数，限制为10条以内
     */
    private Integer ArticleCount;

    /**
     * 多条图文消息信息，默认第一个item为大图
     */
    private List<MultiArticle> Articles;

}
