package com.edu.service.impl;

import com.edu.mapper.SeriesMapper;
import com.edu.model.Series;
import com.edu.service.SeriesService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SeriesServiceImpl implements SeriesService {

    private final SeriesMapper seriesMapper;

    public SeriesServiceImpl(SeriesMapper seriesMapper) {
        this.seriesMapper = seriesMapper;
    }

    @Override
    public List<Series> listSeries() {
        return seriesMapper.selectList(null);
    }

    @Override
    public Series addSeries(Series series) {
        series.setId(null);
        seriesMapper.insert(series);
        return series;
    }

    @Override
    public Series editSeries(Series series) {
        seriesMapper.updateById(series);
        return series;
    }

    @Override
    public int deleteSeries(Integer id) {
        return seriesMapper.deleteById(id);
    }
}
