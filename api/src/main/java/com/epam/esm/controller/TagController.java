package com.epam.esm.controller;

import com.epam.esm.dto.TagDto;
import com.epam.esm.hateoas.HateoasAdder;
import com.epam.esm.service.CRDService;
import com.epam.esm.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * REST Controller for handling Tag related requests.
 * This class uses the TagService to process the requests.
 */
@RestController
@RequestMapping("/api/v2/tags")
public class TagController {
    private final TagService tagService;
    private final HateoasAdder<TagDto> hateoasAdder;

    @Autowired
    public TagController(TagService tagService, HateoasAdder<TagDto> hateoasAdder) {
        this.tagService = tagService;
        this.hateoasAdder = hateoasAdder;
    }


    @GetMapping(consumes = "application/json", produces = "application/json")
    public List<TagDto> findAll(@RequestParam(value = "page", defaultValue = "0", required = false) int page,
                                @RequestParam(value = "size", defaultValue = "5", required = false) int size) {
        List<TagDto> tagDtoList = tagService.findAll(page, size);
        return tagDtoList.stream().peek(hateoasAdder::addLinks).collect(Collectors.toList());
    }

    @GetMapping(path = "/{id}", consumes = "application/json", produces = "application/json")
    public TagDto findById(@PathVariable("id") long id) {
        TagDto tagDto = tagService.findById(id);
        hateoasAdder.addLinks(tagDto);
        return tagDto;
    }

    @GetMapping(path = "/most-used",consumes = "application/json", produces = "application/json")
    public TagDto findMostUsedTag() {
        TagDto tagDto = tagService.findMostUsedTag();
        hateoasAdder.addLinks(tagDto);
        return tagDto;
    }

    @DeleteMapping(path = "/{id}", consumes = "application/json", produces = "application/json")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<?> delete(@PathVariable("id") long id) {
        tagService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping(consumes = "application/json", produces = "application/json")
    public TagDto save(@RequestBody TagDto tagDto) {
        return tagService.save(tagDto);
    }

}
