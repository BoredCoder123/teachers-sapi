package com.example.teacherssapi.entity;

import lombok.*;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@ToString
@Table(name="takes")
public class Takes {
    @EmbeddedId
    private TakesId takesId;
}
