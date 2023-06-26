package com.example.demo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "diary", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"title"})
})
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Diary {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    @Size(min = 5)
    private String title;
    @NotBlank
    private String description;
    @NotBlank
    private String detail;
    @Lob
    private String avatar;
    private Date createDate = new Date();
    @ManyToOne
    User user;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "category_diary_relationship",
            joinColumns = @JoinColumn(name = "diary_id"), inverseJoinColumns = @JoinColumn(name = "category_id"))
    List<Category> categoryList = new ArrayList<>();

}
