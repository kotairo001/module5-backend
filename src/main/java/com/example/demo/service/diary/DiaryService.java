package com.example.demo.service.diary;

import com.example.demo.model.Category;
import com.example.demo.model.Diary;
import com.example.demo.model.User;
import com.example.demo.repository.IDiaryRepository;
import com.example.demo.security.userprincal.UserDetailService;
import com.example.demo.service.IGenericService;
import com.example.demo.service.IUserService;
import com.example.demo.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DiaryService implements IDiaryService {
    @Autowired
    private UserDetailService userDetailService;
    @Autowired
    private IDiaryRepository diaryRepository;

    @Override
    public List<Diary> findAll() {
        return diaryRepository.findAll();
    }

    @Override
    public void save(Diary diary) {
        User user = userDetailService.getCurrentUser();
        diary.setUser(user);
        diaryRepository.save(diary);
    }

    @Override
    public Page<Diary> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public Optional<Diary> findById(Long id) {
        return diaryRepository.findById(id);
    }

    @Override
    public void deleteById(Long id) {
        diaryRepository.deleteById(id);
    }

    @Override
    public Boolean existsByTitle(String title) {
        return diaryRepository.existsByTitle(title);
    }

    @Override
    public List<Category> findCategoryByDiaryId(Long id) {
        return diaryRepository.findCategoryByDiaryId(id);
    }

    @Override
    public List<Diary> findDiariesByTitleContains(String title) {
        return diaryRepository.findDiariesByTitleContains(title);
    }

    @Override
    public List<Diary> findAllByUserId(Long id) {
        return diaryRepository.findAllByUserId(id);
    }

    @Override
    public Long countDiaries() {
        System.out.println(diaryRepository.countDiaries());
        return diaryRepository.countDiaries();
    }
}
