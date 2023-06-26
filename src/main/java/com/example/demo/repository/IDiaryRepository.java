package com.example.demo.repository;

import com.example.demo.model.Category;
import com.example.demo.model.Diary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IDiaryRepository extends JpaRepository<Diary, Long> {
    Boolean existsByTitle(String title);
    @Query("select dr.categoryList from Diary as dr " +
            "where dr.id = :id")
    List<Category> findCategoryByDiaryId (@Param("id") Long id);
    List<Diary> findDiariesByTitleContains(String title);
    List<Diary> findAllByUserId(Long id);
    @Query("select count(dr.title) from Diary as dr")
    Long countDiaries();

}
