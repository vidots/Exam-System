package com.edu.controller.admin;

import com.edu.base.RestResponse;
import com.edu.model.DyContent;
import com.edu.service.DyContentService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class DyContentController {
    private final DyContentService dyContentService;

    public DyContentController(DyContentService dyContentService) {
        this.dyContentService = dyContentService;
    }

    @GetMapping("/dycontent/list")
    public RestResponse listDyContents() {
        List<DyContent> dyContentList = dyContentService.listAllDyContents();
        return RestResponse.ok(dyContentList);
    }

    @PostMapping("/dycontent/add")
    public RestResponse addDyContent(@RequestBody DyContent dyContent) {
        DyContent savedDC = dyContentService.addDyContent(dyContent);
        return RestResponse.ok(savedDC);
    }

    @PostMapping("/dycontent/edit")
    public RestResponse editDyContent(@RequestBody DyContent dyContent) {
        DyContent editedDC = dyContentService.editDyContent(dyContent);
        return RestResponse.ok(editedDC);
    }

    @PostMapping("/dycontent/delete/{id}")
    public RestResponse deleteDyContent(@PathVariable Integer id) {
        dyContentService.deleteDyContent(id);
        return RestResponse.ok();
    }

    @GetMapping("/dycontent/{id}")
    public RestResponse getDyContent(@PathVariable("id") Integer id) {
        return RestResponse.ok(dyContentService.getDyContent(id));
    }

}
