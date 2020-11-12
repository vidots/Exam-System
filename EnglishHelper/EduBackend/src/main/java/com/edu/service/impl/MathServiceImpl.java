package com.edu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.edu.base.PageResponse;
import com.edu.mapper.MathMapper;
import com.edu.mapper.UnitMapper;
import com.edu.mapper.ConceptMapper;
import com.edu.model.Math;
import com.edu.model.Unit;
import com.edu.model.Concept;
import com.edu.service.MathService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MathServiceImpl implements MathService {

    private final MathMapper mathMapper;
    private final UnitMapper unitMapper;
    private final ConceptMapper conceptMapper;

    public MathServiceImpl(MathMapper mathMapper, UnitMapper unitMapper, ConceptMapper conceptMapper) {
        this.mathMapper = mathMapper;
        this.unitMapper = unitMapper;
        this.conceptMapper = conceptMapper;
    }

    @Override
    public PageResponse<Math> listMaths(Integer current, Integer size) {
        IPage<Math> page = new Page<>(current, size);
        mathMapper.selectPage(page, null);
        return new PageResponse<>(current, size, page.getTotal(), page.getRecords());
    }

    @Override
    public List<Math> listAllMaths() {
        return mathMapper.selectList(null);
    }

    @Override
    public Math addMath(Math math) {
        math.setId(null);
        mathMapper.insert(math);
        return math;
    }

    @Override
    public Math editMath(Math math) {
        mathMapper.updateById(math);
        return math;
    }

    @Override
    public Math getMath(Integer id) {
        return mathMapper.selectById(id);
    }

    @Override
    public Integer deleteMath(Integer id) {
        return mathMapper.deleteById(id);
    }

    @Override
    public PageResponse<Unit> listUnits(Integer current, Integer size, Integer mathId) {
        IPage<Unit> page = new Page<>(current, size);
        QueryWrapper<Unit> wrapper = new QueryWrapper<>();
        wrapper.eq("math_id", mathId);
        unitMapper.selectPage(page, wrapper);
        return new PageResponse<>(current, size, page.getTotal(), page.getRecords());
    }

    @Override
    public List<Unit> listAllUnits(Integer mathId) {
        QueryWrapper<Unit> wrapper = new QueryWrapper<>();
        wrapper.eq("math_id", mathId);
        return unitMapper.selectList(wrapper);
    }

    @Override
    public Unit addUnit(Unit unit) {
        unit.setId(null);
        unitMapper.insert(unit);
        return unit;
    }

    @Override
    public Unit editUnit(Unit unit) {
        unitMapper.updateById(unit);
        return unit;
    }

    @Override
    public Unit getUnit(Integer id) {
        return unitMapper.selectById(id);
    }

    @Override
    public Integer deleteUnit(Integer id) {
        return unitMapper.deleteById(id);
    }

    @Override
    public PageResponse<Concept> listConcepts(Integer current, Integer size, Integer unitId) {
        IPage<Concept> page = new Page<>(current, size);
        QueryWrapper<Concept> wrapper = new QueryWrapper();
        wrapper.eq("unit_id", unitId);
        conceptMapper.selectPage(page, wrapper);
        return new PageResponse<>(current, size, page.getTotal(), page.getRecords());
    }

    @Override
    public List<Concept> listAllConcepts(Integer unitId) {
        QueryWrapper<Concept> wrapper = new QueryWrapper();
        wrapper.eq("unit_id", unitId);
        return conceptMapper.selectList(wrapper);
    }

    @Override
    public Concept addConcept(Concept concept) {
        concept.setId(null);
        conceptMapper.insert(concept);
        return concept;
    }

    @Override
    public Concept editConcept(Concept concept) {
        conceptMapper.updateById(concept);
        return concept;
    }

    @Override
    public Integer deleteConcept(Integer id) {
        return conceptMapper.deleteById(id);
    }

    @Override
    public List<Concept> searchConcepts(String keyword) {
        QueryWrapper<Concept> wrapper = new QueryWrapper();
        wrapper.like("name", keyword).or().like("detail", keyword);
        return conceptMapper.selectList(wrapper);
    }
}
