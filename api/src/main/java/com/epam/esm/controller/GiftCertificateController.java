package com.epam.esm.controller;

import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.hateoas.HateoasAdder;
import com.epam.esm.service.GiftCertificateService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v3/certificates")
public class GiftCertificateController {
    private final GiftCertificateService giftCertificateService;
    private final HateoasAdder<GiftCertificateDto> hateoasAdder;


    @GetMapping(consumes = "application/json", produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyAuthority('USER','ADMIN')")
    public List<GiftCertificateDto> findAll(@RequestParam(value = "page", defaultValue = "1", required = false) int page,
                                            @RequestParam(value = "size", defaultValue = "5", required = false) int size) {

        List<GiftCertificateDto> certificateDtoList = giftCertificateService.findAll(page, size);
        certificateDtoList.forEach(giftCertificateDto -> hateoasAdder.addLinks(giftCertificateDto, page, size));
        return certificateDtoList;
    }


    @GetMapping(path = "/{id}", consumes = "application/json", produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyAuthority('USER','ADMIN')")
    public GiftCertificateDto findById(@PathVariable("id") long id) {
        GiftCertificateDto giftCertificateDto = giftCertificateService.findById(id);
        hateoasAdder.addLinks(giftCertificateDto);
        return giftCertificateDto;
    }


    @DeleteMapping(path = "/{id}", consumes = "application/json", produces = "application/json")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> delete(@PathVariable("id") long id) {
        giftCertificateService.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Success");
    }


    @PostMapping(consumes = "application/json", produces = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAuthority('ADMIN')")
    public GiftCertificateDto save(@RequestBody GiftCertificateDto giftCertificateDto) {
            GiftCertificateDto savedDto = giftCertificateService.save(giftCertificateDto);
        hateoasAdder.addLinks(savedDto);
        return savedDto;
    }

    @PatchMapping(path = "/{id}", consumes = "application/json", produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAuthority('ADMIN')")
    public GiftCertificateDto update(@PathVariable("id") long id, @RequestBody GiftCertificateDto giftCertificateDto) {
        GiftCertificateDto updatedDto = giftCertificateService.update(id, giftCertificateDto);
        hateoasAdder.addLinks(updatedDto);
        return updatedDto;
    }

    @GetMapping(path = "/filter", consumes = "application/json", produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyAuthority('USER','ADMIN')")
    public List<GiftCertificateDto> findByParameter(@RequestParam MultiValueMap<String, String> allRequestParams,
                                                    @RequestParam(value = "page", defaultValue = "1", required = false) int page,
                                                    @RequestParam(value = "size", defaultValue = "5", required = false) int size) {

        List<GiftCertificateDto> certificateDtoList = giftCertificateService.doFilter(allRequestParams, page, size);
        certificateDtoList.forEach(giftCertificateDto -> hateoasAdder.addLinks(giftCertificateDto, page, size));
        return certificateDtoList;
    }

    @GetMapping(path = "/tag-names", consumes = "application/json", produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyAuthority('USER','ADMIN')")
    public List<GiftCertificateDto> findByTagNames(@RequestParam(value = "tagNames", required = false) Set<String> tagNames,
                                                   @RequestParam(value = "page", defaultValue = "1", required = false) int page,
                                                   @RequestParam(value = "size", defaultValue = "5", required = false) int size) {

        List<GiftCertificateDto> certificateDtoList = giftCertificateService.findByTagNames(tagNames, page, size);

        certificateDtoList.forEach(giftCertificateDto -> hateoasAdder.addLinks(giftCertificateDto, page, size));
        return certificateDtoList;
    }
}
