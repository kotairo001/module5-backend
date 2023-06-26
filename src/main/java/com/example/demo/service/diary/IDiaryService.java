package com.example.demo.service.diary;

import com.example.demo.model.Category;
import com.example.demo.model.Diary;
import com.example.demo.service.IGenericService;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IDiaryService extends IGenericService<Diary> {
    Boolean existsByTitle(String title);

    List<Category> findCategoryByDiaryId(Long id);
    List<Diary> findDiariesByTitleContains(String title);
    List<Diary> findAllByUserId(Long id);
    Long countDiaries();

}
