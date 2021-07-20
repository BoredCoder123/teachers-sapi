package com.example.teacherssapi.entity;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@ToString
@Table(name="student")
public class Student {
    @Id
    @GeneratedValue
    @Column(name="roll")
    private int roll;

    @Column(name="name")
    private String name;
}
