package com.edu.vo.res;

import lombok.Data;

import java.io.Serializable;

@Data
public class PaperTotal implements Serializable {
    private Integer paperId;
    private Integer total;
}
