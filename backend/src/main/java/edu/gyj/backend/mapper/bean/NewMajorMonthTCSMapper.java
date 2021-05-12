package edu.gyj.backend.mapper.bean;

import edu.gyj.backend.bean.LevelMonthTCSBean;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface NewMajorMonthTCSMapper {
    List<LevelMonthTCSBean> findAll();

    List<LevelMonthTCSBean> findByIdAndMonth(int id, int year, int month);
}
