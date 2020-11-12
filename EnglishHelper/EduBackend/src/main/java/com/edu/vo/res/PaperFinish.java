package com.edu.vo.res;

import lombok.Data;

import java.io.Serializable;

/**
 * 记录单张试卷的进度
 */
@Data
public class PaperFinish implements Serializable {
    private Integer paperId;
    private Integer finish;
}
