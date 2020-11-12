package com.edu.base;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class PageResponse<T> {
    private Integer current;
    private Integer size;
    private Long total;
    private List<T> records;
}
