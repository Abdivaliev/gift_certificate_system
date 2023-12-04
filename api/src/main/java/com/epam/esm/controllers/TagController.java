package com.epam.esm.controllers;

import com.epam.esm.entity.Tag;
import com.epam.esm.exceptions.DaoException;
import com.epam.esm.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST Controller for handling Tag related requests.
 * This class uses the TagService to process the requests.
 *
 * @author Sarvar
 * @version 1.0
 * @since 2023-12-03
 */
@RestController
@RequestMapping("/api/v1/tags")
public class TagController {
    private final TagService tagService;

    /**
     * Constructor for the TagController class.
     * It sets the TagService.
     *
     * @param tagService The TagService to set.
     */
    @Autowired
    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    /**
     * Handles the GET request to get all tags.
     *
     * @return List of all tags.
     * @throws DaoException If there is an error with the database.
     */
    @GetMapping
    public List<Tag> allTags() throws DaoException {
        return tagService.findAll();
    }

    /**
     * Handles the GET request to get a tag by its ID.
     *
     * @param id The ID of the tag to get.
     * @return The tag with the given ID.
     * @throws DaoException                If there is an error with the database.
     */
    @GetMapping("/{id}")
    public Tag tagById(@PathVariable("id") long id) throws DaoException {
        System.out.println("ho");
        return tagService.findById(id);
    }

    /**
     * Handles the DELETE request to delete a tag by its ID.
     *
     * @param id The ID of the tag to delete.
     * @return ResponseEntity with the status and message.
     * @throws DaoException                If there is an error with the database.
     */

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTag(@PathVariable("id") long id) throws DaoException {
        tagService.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Success");
    }

    /**
     * Handles the POST request to create a tag.
     *
     * @param tag The tag to create.
     * @return ResponseEntity with the status and message.
     * @throws DaoException                If there is an error with the database.
     */
    @PostMapping
    public ResponseEntity<?> createTag(@RequestBody Tag tag) throws DaoException {
        tagService.save(tag);
        return ResponseEntity.status(HttpStatus.CREATED).body("Success");
    }

}
