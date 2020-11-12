package com.edu.controller.admin;

import com.edu.base.RestResponse;
import com.edu.model.Series;
import com.edu.model.Series;
import com.edu.service.ArticleService;
import com.edu.service.SeriesService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class SeriesController {
    private final SeriesService seriesService;

    public SeriesController(SeriesService seriesService) {
        this.seriesService = seriesService;
    }

    @GetMapping("/series/list")
    public RestResponse listSeries() {
        List<Series> series = seriesService.listSeries();
        return RestResponse.ok(series);
    }

    @PostMapping("/series/add")
    public RestResponse addSeries(@RequestBody Series series) {
        Series savedSeries = seriesService.addSeries(series);
        return RestResponse.ok(savedSeries);
    }

    @PostMapping("/series/edit")
    public RestResponse editSeries(@RequestBody Series series) {
        Series editedSeries = seriesService.editSeries(series);
        return RestResponse.ok(editedSeries);
    }

    @PostMapping("/series/delete/{id}")
    public RestResponse deleteSeries(@PathVariable Integer id) {
        seriesService.deleteSeries(id);
        return RestResponse.ok();
    }

}
