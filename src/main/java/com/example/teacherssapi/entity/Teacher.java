package com.example.teacherssapi.entity;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name="teacher")
public class Teacher {
    @Id
    @GeneratedValue
    @Column(name ="id")
    private int id;

    @Column(name="name")
    private String name;
}
