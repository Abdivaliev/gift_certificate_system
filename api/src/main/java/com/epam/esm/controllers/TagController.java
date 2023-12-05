package com.epam.esm.controllers;

import com.epam.esm.entity.Tag;
import com.epam.esm.exceptions.DaoException;
import com.epam.esm.service.CRDService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST Controller for handling Tag related requests.
 * This class uses the TagService to process the requests.
 */
@RestController
@RequestMapping("/api/v1/tags")
public class TagController {
    private final CRDService<Tag> crdService;


    @Autowired
    public TagController(CRDService<Tag> crdService) {
        this.crdService = crdService;
    }


    @GetMapping(produces = "application/json")
    public List<Tag> getAllTags() throws DaoException {
        return crdService.findAll();
    }

    @GetMapping(path = "/{id}", produces = "application/json")
    public Tag getTagById(@PathVariable("id") long id) throws DaoException {
        return crdService.findById(id);
    }

    @DeleteMapping(path = "/{id}", produces = "application/json")
    public ResponseEntity<?> deleteTag(@PathVariable("id") long id) throws DaoException {
        crdService.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Success");
    }

    @PostMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> createTag(@RequestBody Tag tag) throws DaoException {
        crdService.save(tag);
        return ResponseEntity.status(HttpStatus.CREATED).body("Success");
    }

}
