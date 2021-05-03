package edu.gyj.backend.service.impl;

import edu.gyj.backend.entity.classCS.*;
import edu.gyj.backend.entity.college.CollegeEntity;
import edu.gyj.backend.entity.major.MajorEntity;
import edu.gyj.backend.mapper.classCS.*;
import edu.gyj.backend.mapper.college.CollegeMapper;
import edu.gyj.backend.mapper.major.MajorMapper;
import edu.gyj.backend.result.ClassResult;
import edu.gyj.backend.service.ClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.ArrayList;
import java.util.List;

@Service
public class ClassServiceImpl implements ClassService {
    @Autowired
    ClassMapper classMapper;
    @Autowired
    MajorMapper majorMapper;
    @Autowired
    CollegeMapper collegeMapper;
    @Autowired
    ClassMonthCSMapper classMonthCSMapper;
    @Autowired
    ClassDayCSMapper classDayCSMapper;
    @Autowired
    ClassDayTCSMapper classDayTCSMapper;
    @Autowired
    ClassMonthTCSMapper classMonthTCSMapper;
    @Autowired
    ClassTCSMapper classTCSMapper;
    @Autowired
    ClassCSMapper classCSMapper;
    @Override
    public List<ClassResult> getAll() {
        List<ClassResult> classResults = new ArrayList<>();
        List<ClassEntity> classEntities = classMapper.findAll();
        for (ClassEntity classEntity : classEntities) {
            MajorEntity majorEntity = majorMapper.findById(classEntity.getMajorId());
            CollegeEntity collegeEntity = collegeMapper.findById(majorEntity.getCollegeId());
            classResults.add(new ClassResult(classEntity.getId(), classEntity.getName(),
                    majorEntity.getMajor(), collegeEntity.getCollege(), majorEntity.getId(),
                    collegeEntity.getId()));
        }
        return classResults;
    }

    @Override
    public List<ClassEntity> getMajorId(int majorId) {
        return classMapper.findByMajorId(majorId);
    }

    @Override
    public int add(ClassEntity classEntity) {
        List<ClassEntity> classEntities = classMapper.findAll();
        boolean tag = false;
        for (ClassEntity c : classEntities) {
            if (c.getName().compareTo(classEntity.getName()) == 0 && c.getMajorId() == classEntity.getMajorId()) {
                tag = true;
                break;
            }
        }
        if (tag) {
            return -1;
        }
        return classMapper.insertClass(classEntity);
    }

    @Override
    public int update(ClassEntity classEntity) {
        return classMapper.updateClass(classEntity);
    }

    @Override
    public int delete(ClassEntity classEntity) {
        return classMapper.deleteClass(classEntity.getId());
    }

    @Override
    public List<ClassMonthCSEntity> findByClassIdAndYearAndMonth(int classId, int year, int month) {
        return classMonthCSMapper.findByClassIdAndYearAndMonth(classId, year, month);
    }

    @Override
    public List<ClassMonthCSEntity> findByClassIdAndYear(int classId, int year) {
        return classMonthCSMapper.findByClassIdAndYear(classId, year);
    }

    @Override
    public List<ClassDayCSEntity> findByClassIdAndDates(int classId, Date start, Date end) {
        return classDayCSMapper.findByDates(classId,new java.sql.Date(start.getTime()),new java.sql.Date(end.getTime()));
    }

    @Override
    public List<ClassMonthTCSEntity> findThreeByClassIdAndYearAndMonth(int classId, int year, int month) {
        return classMonthTCSMapper.findByClassIdAndYearAndMonth(classId,year,month);
    }

    @Override
    public List<ClassMonthTCSEntity> findThreeByClassIdAndYear(int classId, int year) {
        return classMonthTCSMapper.findByClassIdAndYear(classId,year);
    }

    @Override
    public List<ClassDayTCSEntity> findThreeByClassIdAndDates(int classId, Date start, Date end) {
        return classDayTCSMapper.findByDates(classId,new java.sql.Date(start.getTime()),new java.sql.Date(end.getTime()));
    }

    @Override
    public List<ClassCSEntity> findByClassId(int classId) {
        return classCSMapper.findByClassId(classId);
    }

    @Override
    public List<ClassTCSEntity> findThreeByClassId(int classId) {
        return classTCSMapper.findByClassId(classId);
    }

}
