package com.edu.service;

import com.edu.base.PageResponse;
import com.edu.model.Math;
import com.edu.model.Unit;
import com.edu.model.Concept;

import java.util.List;

public interface MathService {
    // 列出所有的数学分支
    PageResponse<Math> listMaths(Integer current, Integer size);
    List<Math> listAllMaths();
    Math addMath(Math math);
    Math editMath(Math math);
    Math getMath(Integer id);
    Integer deleteMath(Integer id);

    // 列出一本书的所有章节
    PageResponse<Unit> listUnits(Integer current, Integer size, Integer mathId);
    List<Unit> listAllUnits(Integer mathId);
    Unit addUnit(Unit chapter);
    Unit editUnit(Unit chapter);
    Unit getUnit(Integer id);
    Integer deleteUnit(Integer id);

    // 列出一个章节的所有段落
    PageResponse<Concept> listConcepts(Integer current, Integer size, Integer unitId);
    List<Concept> listAllConcepts(Integer unitId);
    Concept addConcept(Concept concept);
    Concept editConcept(Concept concept);
    Integer deleteConcept(Integer id);

    List<Concept> searchConcepts(String keyword);
}
