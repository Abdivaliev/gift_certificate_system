package com.epam.esm.controller;

import com.epam.esm.dto.UserDto;
import com.epam.esm.hateoas.HateoasAdder;
import com.epam.esm.service.CRDService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v2/users")
public class UserController {
    private final HateoasAdder<UserDto> hateoasAdder;
    private final CRDService<UserDto> userService;


    @GetMapping(path = "/{id}", consumes = "application/json", produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public UserDto findById(@PathVariable long id) {
        UserDto userDto = userService.findById(id);
        hateoasAdder.addLinks(userDto);
        return userDto;
    }

    @GetMapping(consumes = "application/json", produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public List<UserDto> findAll(@RequestParam(value = "page", defaultValue = "1", required = false) int page,
                                 @RequestParam(value = "size", defaultValue = "5", required = false) int size) {
        List<UserDto> userDtoList = userService.findAll(page, size);
        userDtoList.forEach(userDto -> hateoasAdder.addLinks(userDto, page, size));
        return userDtoList;
    }
}
