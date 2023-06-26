package com.example.demo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "category", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"name"})
})
@NoArgsConstructor
@AllArgsConstructor
@Data

public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    @Column(unique = true)
    private String name;
    @ManyToOne
    User user;
}
