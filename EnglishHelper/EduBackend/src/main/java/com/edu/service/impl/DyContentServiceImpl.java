package com.edu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.edu.mapper.DyContentMapper;
import com.edu.model.DyContent;
import com.edu.service.DyContentService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DyContentServiceImpl implements DyContentService {

    private final DyContentMapper dyContentMapper;

    public DyContentServiceImpl(DyContentMapper dyContentMapper) {
        this.dyContentMapper = dyContentMapper;
    }

    @Override
    public DyContent addDyContent(DyContent dyContent) {
        dyContent.setId(null);
        dyContentMapper.insert(dyContent);
        return dyContent;
    }

    @Override
    public DyContent editDyContent(DyContent dyContent) {
        dyContentMapper.updateById(dyContent);
        return dyContent;
    }

    @Override
    public int deleteDyContent(Integer id) {
        return dyContentMapper.deleteById(id);
    }

    @Override
    public DyContent getDyContent(Integer id) {
        return dyContentMapper.selectById(id);
    }

    @Override
    public DyContent getDyContentBy(String subject) {
        QueryWrapper<DyContent> wrapper = new QueryWrapper();
        wrapper.eq("subject", subject);
        return dyContentMapper.selectOne(wrapper);
    }

    @Override
    public List<DyContent> listAllDyContents() {
        return dyContentMapper.selectList(null);
    }
}
