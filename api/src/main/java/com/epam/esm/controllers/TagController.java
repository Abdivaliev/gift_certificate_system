package com.epam.esm.controllers;

import com.epam.esm.entity.Tag;
import com.epam.esm.exceptions.DaoException;
import com.epam.esm.exceptions.IncorrectParameterException;
import com.epam.esm.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/tags")
public class TagController {
    private final TagService tagService;

    @Autowired
    public TagController(TagService tagService) {
        this.tagService = tagService;
    }


    @GetMapping
    public List<Tag> allTags() throws DaoException {
        return tagService.findAll();
    }


    @GetMapping("/{id}")
    public Tag tagById(@PathVariable("id") long id) throws DaoException, IncorrectParameterException {
        System.out.println("ho");
        return tagService.findById(id);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTag(@PathVariable("id") long id) throws DaoException, IncorrectParameterException {
        tagService.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Success");
    }


    @PostMapping
    public ResponseEntity<?> createTag(@RequestBody Tag tag) throws DaoException, IncorrectParameterException {
        tagService.save(tag);
        return ResponseEntity.status(HttpStatus.CREATED).body("Success");
    }

}
