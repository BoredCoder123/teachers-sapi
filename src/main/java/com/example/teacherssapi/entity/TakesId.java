package com.example.teacherssapi.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Embeddable
public class TakesId implements Serializable {
    private int student_id;

    private int subject_id;
}
