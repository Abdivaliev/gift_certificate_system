package com.epam.esm.controller;

import com.epam.esm.dto.UserDto;
import com.epam.esm.hateoas.HateoasAdder;
import com.epam.esm.service.CRDService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api/v2/users")
public class UserController {
    private final HateoasAdder<UserDto> hateoasAdder;

    private final CRDService<UserDto> userService;

    @Autowired
    public UserController(HateoasAdder<UserDto> hateoasAdder, CRDService<UserDto> userService) {
        this.hateoasAdder = hateoasAdder;

        this.userService = userService;
    }

    @GetMapping(path = "/{id}", consumes = "application/json", produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public UserDto findById(@PathVariable long id) {
        UserDto userDto = userService.findById(id);
        hateoasAdder.addLinks(userDto);
        return userDto;
    }

    @GetMapping(consumes = "application/json", produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public List<UserDto> findAll(@RequestParam(value = "page", defaultValue = "0", required = false) int page,
                                 @RequestParam(value = "size", defaultValue = "5", required = false) int size) {
        List<UserDto> userDtoList = userService.findAll(page, size);
        return userDtoList.stream().peek(hateoasAdder::addLinks).collect(Collectors.toList());
    }
}
