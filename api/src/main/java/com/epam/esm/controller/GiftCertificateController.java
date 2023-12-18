package com.epam.esm.controller;

import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.hateoas.HateoasAdder;
import com.epam.esm.service.GiftCertificateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api/v2/certificates")
public class GiftCertificateController {
    private final GiftCertificateService giftCertificateService;
    private final HateoasAdder<GiftCertificateDto> hateoasAdder;

    @Autowired
    public GiftCertificateController(GiftCertificateService giftCertificateService, HateoasAdder<GiftCertificateDto> hateoasAdder) {
        this.giftCertificateService = giftCertificateService;
        this.hateoasAdder = hateoasAdder;
    }


    @GetMapping(consumes = "application/json", produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public List<GiftCertificateDto> findAll(@RequestParam(value = "page", defaultValue = "1", required = false) int page,
                                            @RequestParam(value = "size", defaultValue = "5", required = false) int size) {
        List<GiftCertificateDto> certificateDtoList = giftCertificateService.findAll(page, size);
        return certificateDtoList.stream().peek(hateoasAdder::addLinks).collect(Collectors.toList());
    }


    @GetMapping(path = "/{id}", consumes = "application/json", produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public GiftCertificateDto findById(@PathVariable("id") long id) {
        GiftCertificateDto giftCertificateDto = giftCertificateService.findById(id);
        hateoasAdder.addLinks(giftCertificateDto);
        return giftCertificateDto;
    }


    @DeleteMapping(path = "/{id}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> delete(@PathVariable("id") long id) {
        giftCertificateService.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Success");
    }


    @PostMapping(consumes = "application/json", produces = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public GiftCertificateDto save(@RequestBody GiftCertificateDto giftCertificateDto) {
        GiftCertificateDto savedDto = giftCertificateService.save(giftCertificateDto);
        hateoasAdder.addLinks(savedDto);
        return giftCertificateDto;
    }

    @PatchMapping(path = "/{id}", consumes = "application/json", produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public GiftCertificateDto update(@PathVariable("id") long id, @RequestBody GiftCertificateDto giftCertificateDto) {
        GiftCertificateDto updatedDto = giftCertificateService.update(id, giftCertificateDto);
        hateoasAdder.addLinks(updatedDto);
        return giftCertificateDto;
    }

    @GetMapping(path = "/filter", consumes = "application/json", produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public List<GiftCertificateDto> getGiftCertificatesByParameter(@RequestParam MultiValueMap<String, String> allRequestParams,
                                                                   @RequestParam(value = "page", defaultValue = "1", required = false) int page,
                                                                   @RequestParam(value = "size", defaultValue = "5", required = false) int size) {
        List<GiftCertificateDto> certificateDtoList = giftCertificateService.doFilter(allRequestParams, page, size);
        return certificateDtoList.stream().peek(hateoasAdder::addLinks).collect(Collectors.toList());
    }
}
