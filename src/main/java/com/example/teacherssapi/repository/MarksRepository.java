package com.example.teacherssapi.repository;

import com.example.teacherssapi.entity.Marks;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MarksRepository extends JpaRepository<Marks, Integer> {
    @Query(nativeQuery = true, value = "select m.subject_id, m.student_id, m.marks from Subject s join Marks m ON s.id=m.subject_id where teacher_id=?1")
    List<Marks> viewMarksByTeacherId(int teacherId);

    @Query(nativeQuery = true, value="SELECT if(COUNT(*)>0, 1, 0) FROM marks m WHERE m.subject_id=?1 AND m.student_id=?2")
    int checkMarksAndStudentCombination(int subjectId, int studentId);

    @Query(nativeQuery = true, value="SELECT * FROM marks m WHERE m.subject_id=?1 AND m.student_id=?2")
    Marks findMarksObject(int subjectId, int studentId);
}
