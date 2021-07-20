package com.example.teacherssapi.service;

import com.example.teacherssapi.entity.Marks;
import com.example.teacherssapi.entity.MarksId;
import com.example.teacherssapi.entity.Teacher;
import com.example.teacherssapi.repository.MarksRepository;
import com.example.teacherssapi.repository.SubjectRepository;
import com.example.teacherssapi.repository.TakesRepository;
import com.example.teacherssapi.repository.TeacherRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.stubbing.OngoingStubbing;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


class TeacherServiceTest {
    @InjectMocks
    TeacherService teacherService;

    @Mock
    TeacherRepository teacherRepository;

    @Mock
    MarksRepository marksRepository;

    @Mock
    SubjectRepository subjectsRepository;

    @Mock
    TakesRepository takesRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindTeacherByIdNotNull() throws Exception {
        Teacher teacher = new Teacher();
        teacher.setId(1);
        teacher.setName("ABC");
        when(teacherRepository.findById(anyInt())).thenReturn(java.util.Optional.of(teacher));
        Teacher newTeacher = teacherService.findTeacherById(1);
        assertNotNull(newTeacher);
        assertEquals(teacher.getId(), newTeacher.getId());
        assertEquals(teacher.getName(), newTeacher.getName());
    }

    @Test
    void testFindTeacherByIdNull() throws Exception {
        when(teacherRepository.findById(anyInt())).thenReturn(null);
        assertThrows(Exception.class, () -> {
            teacherService.findTeacherById(1);
        });
    }

    @Test
    void testFindTeacherByName() {
        List<Teacher> l = new ArrayList<>();
        l.add(new Teacher(1, "ABC"));
        l.add(new Teacher(2, "ABC"));
        l.add(new Teacher(3, "ABC"));
        when(teacherRepository.findByName(anyString())).thenReturn(l);
        List<Teacher> newList = teacherService.findTeacherByName("ABC");
        assertEquals(l, newList);
    }

    @Test
    void testViewAllMarks() {
        List<Marks> l = new ArrayList<>();
        l.add(new Marks(new MarksId(1, 1), 30));
        l.add(new Marks(new MarksId(1, 2), 60));
        l.add(new Marks(new MarksId(1, 3), 80));
        when(marksRepository.findAll()).thenReturn(l);
        List<Marks> newList = teacherService.viewAllMarks();
        assertEquals(l, newList);
    }

    @Test
    void testViewMarksByTeacherId() {
        List<Marks> l = new ArrayList<>();
        l.add(new Marks(new MarksId(1, 1), 30));
        l.add(new Marks(new MarksId(1, 2), 60));
        l.add(new Marks(new MarksId(1, 3), 80));
        when(marksRepository.viewMarksByTeacherId(anyInt())).thenReturn(l);
        List<Marks> newList = teacherService.viewMarksByTeacherId(1);
        assertEquals(l, newList);
    }

    @Test
    void testAddMarksNoCombination() {
        when(subjectsRepository.checkTeacherSubjectCombination(anyInt(), anyInt())).thenReturn(0);
        String s = teacherService.addMarks(new Marks(new MarksId(1, 1), 30), 1);
        assertEquals(s, "The teacher subject combination doesn't exist. Hence unable to add data");
    }

    @Test
    void testAddMarksDataExist(){
        when(subjectsRepository.checkTeacherSubjectCombination(anyInt(), anyInt())).thenReturn(1);
        when(marksRepository.checkMarksAndStudentCombination(anyInt(), anyInt())).thenReturn(1);
        String s = teacherService.addMarks(new Marks(new MarksId(1, 1), 30), 1);
        assertEquals(s, "Data already exist. Go via the update route");
    }

    @Test
    void testAddMarksStudentSubjectFail(){
        when(subjectsRepository.checkTeacherSubjectCombination(anyInt(), anyInt())).thenReturn(1);
        when(marksRepository.checkMarksAndStudentCombination(anyInt(), anyInt())).thenReturn(0);
        when(takesRepository.checkStudentAndSubjectCombination(anyInt(), anyInt())).thenReturn(0);
        String s = teacherService.addMarks(new Marks(new MarksId(1, 1), 30), 1);
        assertEquals(s, "Student and subject combination doesn't match");
    }

    @Test
    void testAddMarksSuccess(){
        when(subjectsRepository.checkTeacherSubjectCombination(anyInt(), anyInt())).thenReturn(1);
        when(marksRepository.checkMarksAndStudentCombination(anyInt(), anyInt())).thenReturn(0);
        when(takesRepository.checkStudentAndSubjectCombination(anyInt(), anyInt())).thenReturn(1);
        when(marksRepository.save(any())).thenReturn(null);
        String s = teacherService.addMarks(new Marks(new MarksId(1, 1), 30), 1);
        assertEquals(s, "Data has been added. 1 1 30");
    }

    @Test
    void testUpdateMarksNoCombination(){
        when(subjectsRepository.checkTeacherSubjectCombination(anyInt(), anyInt())).thenReturn(0);
        String s=teacherService.updateMarks(new Marks(new MarksId(1, 1), 30), 1);
        assertEquals(s, "The teacher subject combination doesn't exist. Hence unable to update data");
    }

    @Test
    void testUpdateMarksNoDataExist(){
        when(subjectsRepository.checkTeacherSubjectCombination(anyInt(), anyInt())).thenReturn(1);
        when(marksRepository.checkMarksAndStudentCombination(anyInt(), anyInt())).thenReturn(0);
        String s=teacherService.updateMarks(new Marks(new MarksId(1, 1), 30), 1);
        assertEquals(s, "Data doesn't exist. Go via the insert route");
    }

    @Test
    void testUpdateMarksNoCombination2(){
        when(subjectsRepository.checkTeacherSubjectCombination(anyInt(), anyInt())).thenReturn(1);
        when(marksRepository.checkMarksAndStudentCombination(anyInt(), anyInt())).thenReturn(1);
        when(takesRepository.checkStudentAndSubjectCombination(anyInt(), anyInt())).thenReturn(0);
        String s=teacherService.updateMarks(new Marks(new MarksId(1, 1), 30), 1);
        assertEquals(s, "Student and subject combination doesn't match");
    }

    @Test
    void testUpdateSuccess(){
        when(subjectsRepository.checkTeacherSubjectCombination(anyInt(), anyInt())).thenReturn(1);
        when(marksRepository.checkMarksAndStudentCombination(anyInt(), anyInt())).thenReturn(1);
        when(takesRepository.checkStudentAndSubjectCombination(anyInt(), anyInt())).thenReturn(1);
        Marks marks=new Marks(new MarksId(1, 1), 30);
        when(marksRepository.findMarksObject(anyInt(), anyInt())).thenReturn(marks);
        OngoingStubbing<Object> newMarks;
        newMarks=when(marksRepository.save(any())).thenReturn(marks);
        String s=teacherService.updateMarks(new Marks(new MarksId(1, 1), 30), 1);
        assertEquals(s, "Data has been updated. 1 1 30");
    }
}