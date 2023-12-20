package com.epam.esm.controller;

import com.epam.esm.dto.SignInDto;
import com.epam.esm.dto.AuthResponseDto;
import com.epam.esm.dto.UserDto;
import com.epam.esm.dto.SignUpDto;
import com.epam.esm.hateoas.HateoasAdder;
import com.epam.esm.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v3/users")
public class UserController {
    private final HateoasAdder<UserDto> hateoasAdder;

    private final UserService userService;

    @PostMapping(path = "/signUp", consumes = "application/json", produces = "application/json")
    public ResponseEntity<AuthResponseDto> signUp(@RequestBody SignUpDto signUpDto) {
        return ResponseEntity.ok(userService.signUp(signUpDto));
    }

    @PostMapping(path = "/signIn", consumes = "application/json", produces = "application/json")
    public ResponseEntity<AuthResponseDto> signIn(@RequestBody SignInDto signInDto) {
        return ResponseEntity.ok(userService.signIn(signInDto));
    }

    @GetMapping(path = "/{id}", consumes = "application/json", produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    @Secured({"Role.USER","Role.ADMIN"})
    public UserDto findById(@PathVariable long id) {
        UserDto userDto = userService.findById(id);
        hateoasAdder.addLinks(userDto);
        return userDto;
    }

    @GetMapping(consumes = "application/json", produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    @Secured({"Role.USER","Role.ADMIN"})
    public List<UserDto> findAll(@RequestParam(value = "page", defaultValue = "1", required = false) int page,
                                 @RequestParam(value = "size", defaultValue = "5", required = false) int size) {
        List<UserDto> userDtoList = userService.findAll(page, size);
        return userDtoList.stream().peek(hateoasAdder::addLinks).collect(Collectors.toList());
    }
}
