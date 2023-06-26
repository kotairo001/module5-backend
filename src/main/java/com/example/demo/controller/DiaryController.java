package com.example.demo.controller;

import com.example.demo.dto.response.ResponMessage;
import com.example.demo.model.Diary;
import com.example.demo.service.diary.IDiaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin("*")
@RequestMapping("/diary")
public class DiaryController {
    @Autowired
    private IDiaryService diaryService;
    @GetMapping("/count")
    public ResponseEntity<?> getDiaryNumber() {
        return new ResponseEntity<>(diaryService.findAll().size(), HttpStatus.OK) ;
    }
    @GetMapping("/page")
    public ResponseEntity<?> pageDiary(Pageable pageable) {
        return new ResponseEntity<>(diaryService.findAll(pageable), HttpStatus.OK);
    }

    @GetMapping("/all/{id}")
    public ResponseEntity<?> listDiary(@PathVariable Long id) {
        return new ResponseEntity<>(diaryService.findAllByUserId(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> createDiary(@RequestBody Diary diary) {
        diaryService.save(diary);
        return new ResponseEntity<>(new ResponMessage("create_success"), HttpStatus.OK);

    }

    @GetMapping("/list/{id}")
    public ResponseEntity<?> getCategory(@PathVariable Long id) {
        return new ResponseEntity<>(diaryService.findCategoryByDiaryId(id), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getDiaryDetail(@PathVariable Long id) {
        Optional<Diary> diary = diaryService.findById(id);
        if (!diary.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(diary, HttpStatus.OK);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateDiary(@PathVariable Long id, @RequestBody Diary diary) {
        Optional<Diary> diary1 = diaryService.findById(id);
        if (!diary1.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        if (!diary.getTitle().equalsIgnoreCase(diary1.get().getTitle())) {
            if (diaryService.existsByTitle(diary.getTitle())) {
                return new ResponseEntity<>(new ResponMessage("title_existed"), HttpStatus.OK);
            }
        }
        if (!diary.getDescription().equalsIgnoreCase(diary1.get().getDescription())) {
            diary.setId(diary1.get().getId());
        }
        if (!diary.getDetail().equalsIgnoreCase(diary1.get().getDetail())) {
            diary.setId(diary1.get().getId());
        }
        if (!diary.getAvatar().equalsIgnoreCase(diary1.get().getAvatar())) {
            diary.setId(diary1.get().getId());
        }
        if (diary.getTitle().equalsIgnoreCase(diary1.get().getTitle()) && diary.getDescription().equalsIgnoreCase(diary1.get().getDescription())
                && diary.getDetail().equalsIgnoreCase(diary1.get().getDetail()) && diary.getAvatar().equalsIgnoreCase(diary1.get().getAvatar())) {
            return new ResponseEntity<>(new ResponMessage("no_change"), HttpStatus.OK);
        }
        diary.setId(diary1.get().getId());
        diaryService.save(diary);
        return new ResponseEntity<>(new ResponMessage("update_success"), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteDiary(@PathVariable Long id) {
        diaryService.deleteById(id);
        return new ResponseEntity<>(new ResponMessage("delete_success"), HttpStatus.OK);
    }

    @GetMapping("search/{title}")
    public ResponseEntity<?> searchDiaries(@PathVariable String title) {

        return new ResponseEntity<>(diaryService.findDiariesByTitleContains(title), HttpStatus.OK);

    }
}
