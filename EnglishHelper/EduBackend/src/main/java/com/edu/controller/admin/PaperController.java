package com.edu.controller.admin;

import com.edu.base.PageResponse;
import com.edu.base.RestResponse;
import com.edu.model.Paper;
import com.edu.service.PaperService;
import com.edu.vo.req.PaperQuery;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/admin")
public class PaperController {

    private final PaperService paperService;

    public PaperController(PaperService paperService) {
        this.paperService = paperService;
    }

    @GetMapping("/paper/list/{current}/{size}")
    public RestResponse listPaper(@PathVariable("current") int current, @PathVariable("size") int size, PaperQuery paperQuery) {
        PageResponse pageResponse = paperService.listPaper(current, size, paperQuery);
        return RestResponse.ok(pageResponse);
    }

    @GetMapping("/paper/{id}")
    public RestResponse getPaper(@PathVariable("id") Integer id) {
        return RestResponse.ok(paperService.getPaper(id));
    }

    @PostMapping("/paper/add")
    public RestResponse addPaper(@RequestBody Paper paper) {
        Paper savedPaper = paperService.addPaper(paper);
        return RestResponse.ok(savedPaper);
    }

    @PostMapping("/paper/edit/{id}")
    public RestResponse editPaper(@PathVariable("id")Integer id, @RequestBody Paper paper) {
        Paper editedPaper = paperService.editPaper(paper, id);
        return RestResponse.ok(editedPaper);
    }

    @PostMapping("/paper/delete/{id}")
    public RestResponse deletePaper(@PathVariable Integer id) {
        paperService.deletePaper(id);
        return RestResponse.ok();
    }

}
