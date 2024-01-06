package com.epam.esm.controller;

import com.epam.esm.dto.TagDto;
import com.epam.esm.hateoas.HateoasAdder;
import com.epam.esm.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v3/tags")
public class TagController {
    private final TagService tagService;
    private final HateoasAdder<TagDto> hateoasAdder;


    @GetMapping(consumes = "application/json", produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyAuthority('USER','ADMIN')")
    public List<TagDto> findAll(@RequestParam(value = "page", defaultValue = "1", required = false) int page,
                                @RequestParam(value = "size", defaultValue = "5", required = false) int size) {
        List<TagDto> tagDtoList = tagService.findAll(page, size);
        tagDtoList.forEach(tagDto -> hateoasAdder.addLinks(tagDto, page, size));
        return tagDtoList;
    }

    @GetMapping(path = "/{id}", consumes = "application/json", produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyAuthority('USER','ADMIN')")
    public TagDto findById(@PathVariable("id") long id) {
        TagDto tagDto = tagService.findById(id);
        hateoasAdder.addLinks(tagDto);
        return tagDto;
    }

    @GetMapping(path = "/users/tag/{userId}", consumes = "application/json", produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyAuthority('USER','ADMIN')")
    public List<TagDto> findMostUsedTagListByUserId(@RequestParam(value = "page", defaultValue = "1", required = false) int page,
                                                    @RequestParam(value = "size", defaultValue = "5", required = false) int size,
                                                    @PathVariable("userId") long userId) {
        List<TagDto> tagList = tagService.findMostUsedTagListByUserId(userId, page, size);
        tagList.forEach(tagDto -> hateoasAdder.addLinks(tagDto, page, size));
        return tagList;
    }

    @DeleteMapping(path = "/{id}", consumes = "application/json", produces = "application/json")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> delete(@PathVariable("id") long id) {
        tagService.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Success");
    }

    @PostMapping(consumes = "application/json", produces = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAuthority('ADMIN')")
    public TagDto save(@RequestBody TagDto tagDto) {
        TagDto savedDTO = tagService.save(tagDto);
        hateoasAdder.addLinks(savedDTO);
        return savedDTO;
    }

}
