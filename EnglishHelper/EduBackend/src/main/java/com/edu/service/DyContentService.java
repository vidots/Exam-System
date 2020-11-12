package com.edu.service;

import com.edu.base.PageResponse;
import com.edu.model.DyContent;

import java.util.List;

public interface DyContentService {
    DyContent addDyContent(DyContent dyContent);
    DyContent editDyContent(DyContent dyContent);
    int deleteDyContent(Integer id);
    DyContent getDyContent(Integer id);
    DyContent getDyContentBy(String subject);
    List<DyContent> listAllDyContents();
}
