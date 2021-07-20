package com.example.teacherssapi.repository;

import com.example.teacherssapi.entity.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface SubjectRepository extends JpaRepository<Subject, Integer>{
    @Query(nativeQuery=true, value="SELECT if(COUNT(*)>0, 1, 0) FROM subject s WHERE s.teacher_id=?1 AND s.id=?2")
    int checkTeacherSubjectCombination(int teacherId, int subjectId);
}
