package com.edu.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.edu.model.Collect;
import com.edu.model.Question;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface CollectMapper extends BaseMapper<Collect> {

    @Select("SELECT * FROM p_question WHERE id in (SELECT question_id  FROM question_collection WHERE user_id = #{userId})")
    List<Question> listCollects(Integer userId);
}
