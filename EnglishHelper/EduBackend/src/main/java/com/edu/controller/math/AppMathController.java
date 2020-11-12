package com.edu.controller.math;

import com.edu.base.RestResponse;
import com.edu.service.BookService;
import com.edu.service.MathService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/app/math")
public class AppMathController {

    private final MathService mathService;

    public AppMathController(MathService mathService) {
        this.mathService = mathService;
    }

    @GetMapping("/books")
    public RestResponse getMathBooks() {
        return RestResponse.ok(mathService.listAllMaths());
    }

    @GetMapping("/book/{id}")
    public RestResponse getBookUnits(@PathVariable("id") Integer id) {
        return RestResponse.ok(mathService.listAllUnits(id));
    }

    @GetMapping("/unit/{id}")
    public RestResponse getUnitConcepts(@PathVariable("id") Integer id) {
        return RestResponse.ok(mathService.listAllConcepts(id));
    }

    @GetMapping("/search")
    public RestResponse searchConcepts(String keyword) {
        return RestResponse.ok(mathService.searchConcepts(keyword));
    }

}
