package com.edu.service;

import com.edu.base.PageResponse;
import com.edu.model.Paper;
import com.edu.vo.req.PaperQuery;
import com.edu.vo.res.PaperFinish;

import java.util.List;
import java.util.Map;

public interface PaperService {
    PageResponse<Paper> listPaper(Integer current, Integer size, PaperQuery articleQuery);
    Map<String, Object> listShowPaper(Integer seriesId, Integer userId);
    Paper addPaper(Paper article);
    Paper getPaper(Integer id);
    Paper editPaper(Paper article, Integer id);
    int deletePaper(Integer id);
}
