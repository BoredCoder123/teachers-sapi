package com.example.teacherssapi.service;

import com.example.teacherssapi.entity.Marks;
import com.example.teacherssapi.entity.Takes;
import com.example.teacherssapi.entity.Teacher;
import com.example.teacherssapi.repository.MarksRepository;
import com.example.teacherssapi.repository.SubjectRepository;
import com.example.teacherssapi.repository.TakesRepository;
import com.example.teacherssapi.repository.TeacherRepository;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@Service
public class TeacherService {
    private final static Logger logger = Logger.getLogger(TeacherService.class.getName());

    @Autowired
    private TeacherRepository teacherRepository;

    @Autowired
    private MarksRepository marksRepository;

    @Autowired
    private SubjectRepository subjectsRepository;

    @Autowired
    private TakesRepository takesRepository;

    public Teacher findTeacherById(int id) throws Exception {
        Teacher t= teacherRepository.findById(id).orElse(null);
        if(t==null)
            throw new Exception("Teacher not found");
        return t;
    }

    public List<Teacher> findTeacherByName(String name){
        return teacherRepository.findByName(name);
    }

    public List<Teacher> findTeachers() {
        return teacherRepository.findAll();
    }

    public List<Marks> viewAllMarks(){
        return marksRepository.findAll();
    }

    public List<Marks> viewMarksByTeacherId(int teacherId) {
        return marksRepository.viewMarksByTeacherId(teacherId);
    }

    public String addMarks(Marks marks, int teacherId) {
        logger.info("MarksObject: "+marks.toString()+" teacherId: "+teacherId);
        int teacherAndSubjectCombination=subjectsRepository.checkTeacherSubjectCombination(teacherId, marks.getMarksId().getSubjectId());
        if(teacherAndSubjectCombination==0)
            return "The teacher subject combination doesn't exist. Hence unable to add data";
        int marksAndStudentCombination=marksRepository.checkMarksAndStudentCombination(marks.getMarksId().getSubjectId(), marks.getMarksId().getStudentId());
        if(marksAndStudentCombination==1)
            return "Data already exist. Go via the update route";
        int studentAndSubjectCombination= takesRepository.checkStudentAndSubjectCombination(marks.getMarksId().getStudentId(), marks.getMarksId().getSubjectId());
        if(studentAndSubjectCombination==0)
            return "Student and subject combination doesn't match";
        marksRepository.save(marks);
        return "Data has been added. " + marks;
    }

    public String updateMarks(Marks marks, Integer teacherId) {
        int teacherAndSubjectCombination=subjectsRepository.checkTeacherSubjectCombination(teacherId, marks.getMarksId().getSubjectId());
        if(teacherAndSubjectCombination==0)
            return "The teacher subject combination doesn't exist. Hence unable to update data";
        int marksAndStudentCombination=marksRepository.checkMarksAndStudentCombination(marks.getMarksId().getSubjectId(), marks.getMarksId().getStudentId());
        if(marksAndStudentCombination==0)
            return "Data doesn't exist. Go via the insert route";
        int studentAndSubjectCombination= takesRepository.checkStudentAndSubjectCombination(marks.getMarksId().getStudentId(), marks.getMarksId().getSubjectId());
        if(studentAndSubjectCombination==0)
            return "Student and subject combination doesn't match";
        Marks existingMarks=marksRepository.findMarksObject(marks.getMarksId().getStudentId(), marks.getMarksId().getSubjectId());
        existingMarks.setMarks(marks.getMarks());
        marksRepository.save(existingMarks);
        return "Data has been updated. " + existingMarks;
    }
}
