package com.example.teacherssapi.repository;

import com.example.teacherssapi.entity.Takes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TakesRepository extends JpaRepository<Takes, Integer> {
    @Query(nativeQuery=true, value="SELECT if(COUNT(*)>0, 1, 0) FROM takes t WHERE t.student_id=?1 AND t.subject_id=?2")
    int checkStudentAndSubjectCombination(int studentId, int subjectId);
}
