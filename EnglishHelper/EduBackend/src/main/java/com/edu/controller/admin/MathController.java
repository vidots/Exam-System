package com.edu.controller.admin;

import com.edu.base.PageResponse;
import com.edu.base.RestResponse;
import com.edu.model.Math;
import com.edu.model.Unit;
import com.edu.model.Concept;
import com.edu.service.MathService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
public class MathController {
    private final MathService mathService;

    public MathController(MathService mathService) {
        this.mathService = mathService;
    }

    // 数学科目相关
    @GetMapping("/math/list/{current}/{size}")
    public RestResponse listMath(@PathVariable("current") int current, @PathVariable("size") int size) {
        PageResponse pageResponse = mathService.listMaths(current, size);
        return RestResponse.ok(pageResponse);
    }
    @PostMapping("/math/add")
    public RestResponse addMath(@RequestBody Math math) {
        Math savedMath = mathService.addMath(math);
        return RestResponse.ok(savedMath);
    }
    @PostMapping("/math/edit")
    public RestResponse editMath(@RequestBody Math math) {
        Math updatedMath = mathService.editMath(math);
        return RestResponse.ok(updatedMath);
    }
    @PostMapping("/math/delete/{id}")
    public RestResponse deleteMath(@PathVariable Integer id) {
        mathService.deleteMath(id);
        return RestResponse.ok();
    }
    @GetMapping("/math/{id}")
    public RestResponse getMath(@PathVariable Integer id) {
        return RestResponse.ok(mathService.getMath(id));
    }

    // 单元相关
    @GetMapping("/unit/list/{current}/{size}")
    public RestResponse listUnit(@PathVariable("current") int current, @PathVariable("size") int size, Integer mathId) {
        PageResponse pageResponse = mathService.listUnits(current, size, mathId);
        return RestResponse.ok(pageResponse);
    }
    @PostMapping("/unit/add")
    public RestResponse addUnit(@RequestBody Unit unit) {
        Unit savedUnit = mathService.addUnit(unit);
        return RestResponse.ok(savedUnit);
    }
    @PostMapping("/unit/edit")
    public RestResponse editUnit(@RequestBody Unit unit) {
        Unit updatedUnit = mathService.editUnit(unit);
        return RestResponse.ok(updatedUnit);
    }
    @PostMapping("/unit/{id}")
    public RestResponse editUnit(@PathVariable("id") Integer id) {
        return RestResponse.ok(mathService.getUnit(id));
    }
    @PostMapping("/unit/delete/{id}")
    public RestResponse deleteUnit(@PathVariable("id") Integer id) {
        mathService.deleteUnit(id);
        return RestResponse.ok();
    }

    // 概念相关
    @GetMapping("/concept/list/{current}/{size}")
    public RestResponse listConcept(@PathVariable("current") int current, @PathVariable("size") int size, Integer unitId) {
        PageResponse pageResponse = mathService.listConcepts(current, size, unitId);
        return RestResponse.ok(pageResponse);
    }
    @PostMapping("/concept/add")
    public RestResponse addConcept(@RequestBody Concept concept) {
        Concept savedConcept = mathService.addConcept(concept);
        return RestResponse.ok(savedConcept);
    }
    @PostMapping("/concept/edit")
    public RestResponse editConcept(@RequestBody Concept concept) {
        Concept updatedConcept = mathService.editConcept(concept);
        return RestResponse.ok(updatedConcept);
    }
    @PostMapping("/concept/delete/{id}")
    public RestResponse deleteConcept(@PathVariable Integer id) {
        mathService.deleteConcept(id);
        return RestResponse.ok();
    }
}
