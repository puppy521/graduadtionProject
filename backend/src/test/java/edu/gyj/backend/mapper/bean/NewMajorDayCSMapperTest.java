package edu.gyj.backend.mapper.bean;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Date;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class NewMajorDayCSMapperTest {
    @Autowired
    NewMajorDayCSMapper newClassDayCSMapper;

    @Test
    void findAll() {
        newClassDayCSMapper.findAll().forEach(System.out::println);
    }

    @Test
    void findByIdAndDay() {
        newClassDayCSMapper.findByIdAndDay(1, Date.valueOf("2021-4-1")).forEach(System.out::println);
    }
}