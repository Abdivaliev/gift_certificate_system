package com.epam.esm.controller;

import com.epam.esm.dto.MostUsedTagDto;
import com.epam.esm.dto.TagDto;
import com.epam.esm.hateoas.HateoasAdder;
import com.epam.esm.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * REST Controller for handling Tag related requests.
 * This class uses the TagService to process the requests.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v3/tags")
public class TagController {
    private final TagService tagService;
    private final HateoasAdder<TagDto> hateoasAdder;



    @GetMapping(consumes = "application/json", produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    @Secured({"Role.USER","Role.ADMIN"})
    public List<TagDto> findAll(@RequestParam(value = "page", defaultValue = "1", required = false) int page,
                                @RequestParam(value = "size", defaultValue = "5", required = false) int size) {
        List<TagDto> tagDtoList = tagService.findAll(page, size);
        return tagDtoList.stream().peek(hateoasAdder::addLinks).collect(Collectors.toList());
    }

    @GetMapping(path = "/{id}", consumes = "application/json", produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    @Secured({"Role.USER","Role.ADMIN"})
    public TagDto findById(@PathVariable("id") long id) {
        TagDto tagDto = tagService.findById(id);
        hateoasAdder.addLinks(tagDto);
        return tagDto;
    }

    @GetMapping(path = "/users/most-used-tag/{userId}", consumes = "application/json", produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    @Secured({"Role.USER","Role.ADMIN"})
    public List<MostUsedTagDto> findMostUsedTagByUser(@PathVariable("userId") long userId){
        return tagService.findMostUsedTagByUserId(userId);
    }

    @DeleteMapping(path = "/{id}", consumes = "application/json", produces = "application/json")
    @Secured({"Role.ADMIN"})
    public ResponseEntity<?> delete(@PathVariable("id") long id) {
        tagService.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Success");
    }

    @PostMapping(consumes = "application/json", produces = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    @Secured({"Role.ADMIN"})
    public TagDto save(@RequestBody TagDto tagDto) {
        TagDto savedDTO = tagService.save(tagDto);
        hateoasAdder.addLinks(savedDTO);
        return tagDto;
    }

}
