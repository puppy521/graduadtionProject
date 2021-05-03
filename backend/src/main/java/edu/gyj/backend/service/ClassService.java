package edu.gyj.backend.service;

import edu.gyj.backend.entity.classCS.*;
import edu.gyj.backend.result.ClassResult;

import java.util.Date;
import java.util.List;

public interface ClassService {
    public List<ClassResult> getAll();

    public List<ClassEntity> getMajorId(int majorId);

    public int add(ClassEntity classEntity);

    public int update(ClassEntity classEntity);

    public int delete(ClassEntity classEntity);

    public List<ClassMonthCSEntity> findByClassIdAndYearAndMonth(int classId, int year, int month);

    public List<ClassMonthCSEntity> findByClassIdAndYear(int classId, int year);

    public List<ClassDayCSEntity> findByClassIdAndDates(int classId, Date start, Date end);

    public List<ClassMonthTCSEntity> findThreeByClassIdAndYearAndMonth(int classId, int year, int month);

    public List<ClassMonthTCSEntity> findThreeByClassIdAndYear(int classId, int year);

    public List<ClassDayTCSEntity> findThreeByClassIdAndDates(int classId, Date start, Date end);

    public List<ClassCSEntity> findByClassId(int classId);

    public List<ClassTCSEntity> findThreeByClassId(int classId);
}
