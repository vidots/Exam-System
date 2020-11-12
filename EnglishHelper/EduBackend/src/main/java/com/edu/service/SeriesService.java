package com.edu.service;

import com.edu.model.Series;

import java.util.List;

public interface SeriesService {
    List<Series> listSeries();
    Series addSeries(Series series);
    Series editSeries(Series series);
    int deleteSeries(Integer id);
}
