package com.example.teacherssapi.entity;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="marks")
public class Marks {
    @EmbeddedId
    @AttributeOverrides({
            @AttributeOverride(name="studentId", column=@Column(name="student_id")),
            @AttributeOverride(name="subjectId", column=@Column(name="subject_id"))
    })
    private MarksId marksId;

    @Column(name="marks")
    private int marks;

    @Override
    public String toString(){
        return this.marksId.getStudentId()+" "+this.marksId.getSubjectId()+" "+marks;
    }
}
