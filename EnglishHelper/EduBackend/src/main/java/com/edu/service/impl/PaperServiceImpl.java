package com.edu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.edu.base.PageResponse;
import com.edu.mapper.PaperMapper;
import com.edu.model.Paper;
import com.edu.service.PaperService;
import com.edu.vo.req.PaperQuery;
import com.edu.vo.res.PaperFinish;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PaperServiceImpl implements PaperService {

    private final PaperMapper paperMapper;

    public PaperServiceImpl(PaperMapper paperMapper) {
        this.paperMapper = paperMapper;
    }

    @Override
    public PageResponse<Paper> listPaper(Integer current, Integer size, PaperQuery paperQuery) {
        IPage<Paper> page = new Page<>(current, size);
        QueryWrapper<Paper> wrapper = new QueryWrapper<>();
        if (paperQuery.getSeriesId() != null) {
            wrapper.eq("series_id", paperQuery.getSeriesId());
        }
        if (paperQuery.getQuery() != null) {
            wrapper.like("name", paperQuery.getQuery());
        }
        paperMapper.selectPage(page, wrapper);
        return new PageResponse<>(current, size, page.getTotal(), page.getRecords());
    }

    @Override
    public Map<String, Object> listShowPaper(Integer seriesId, Integer userId) {
        Map<String, Object> map = new HashMap<>();
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("series_id", seriesId);
        map.put("papers", paperMapper.selectList(wrapper));
        map.put("total", paperMapper.getPaperTotal(seriesId));
        map.put("finish", paperMapper.getPaperFinish(userId, seriesId));
        return map;
    }

    @Override
    public Paper addPaper(Paper paper) {
        paper.setId(null);
        paperMapper.insert(paper);
        return paper;
    }

    @Override
    public Paper getPaper(Integer id) {
        return paperMapper.selectById(id);
    }

    @Override
    public Paper editPaper(Paper paper, Integer id) {
        paperMapper.updateById(paper);
        return paper;
    }

    @Override
    public int deletePaper(Integer id) {
        return paperMapper.deleteById(id);
    }
}
