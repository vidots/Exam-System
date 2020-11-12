package com.edu.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.edu.model.Paper;
import com.edu.vo.res.PaperFinish;
import com.edu.vo.res.PaperTotal;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface PaperMapper extends BaseMapper<Paper> {
    @Select("select paper_id AS paperId, COUNT(*) AS total FROM p_question " +
            "WHERE  paper_id in " +
            "(SELECT id FROM p_paper WHERE series_id = #{seriesId}) " +
            "GROUP BY paper_id;")
    List<PaperTotal> getPaperTotal(Integer seriesId);
    @Select("select paper_id AS paperId, COUNT(*) AS finish FROM user_answer " +
            "WHERE  user_id = #{userId} AND paper_id in " +
            "(SELECT id FROM p_paper WHERE series_id = #{seriesId}) " +
            "GROUP BY paper_id;")
    List<PaperFinish> getPaperFinish(Integer userId, Integer seriesId);
}







