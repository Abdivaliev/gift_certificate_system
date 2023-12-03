package com.epam.esm.controllers;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.exceptions.DaoException;
import com.epam.esm.exceptions.IncorrectParameterException;
import com.epam.esm.service.GiftCertificateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/v1/certificates")
public class GiftCertificateController {
    private final GiftCertificateService giftCertificateService;

    @Autowired
    public GiftCertificateController(GiftCertificateService giftCertificateService) {
        this.giftCertificateService = giftCertificateService;
    }

    @GetMapping
    public List<GiftCertificate> allGiftCertificates() throws DaoException {
        return giftCertificateService.findAll();
    }


    @GetMapping("/{id}")
    public GiftCertificate giftCertificateById(@PathVariable("id") long id) throws DaoException, IncorrectParameterException {
        return giftCertificateService.findById(id);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteGiftCertificate(@PathVariable("id") long id) throws DaoException, IncorrectParameterException {
        giftCertificateService.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Success");
    }

    @PostMapping
    public ResponseEntity<?> createGiftCertificate(@RequestBody GiftCertificate giftCertificate)
            throws DaoException, IncorrectParameterException {
        giftCertificateService.save(giftCertificate);
        return ResponseEntity.status(HttpStatus.CREATED).body("Success");
    }

    @PatchMapping("/{id}")
    public ResponseEntity<String> updateGiftCertificate(@PathVariable("id") long id, @RequestBody GiftCertificate giftCertificate)
            throws DaoException, IncorrectParameterException {
        giftCertificateService.update(id, giftCertificate);
        return ResponseEntity.status(HttpStatus.CREATED).body("Success");
    }


    @GetMapping("/filter")
    public List<GiftCertificate> giftCertificatesByParameter(@RequestParam MultiValueMap<String, String> allRequestParams)
            throws DaoException {
        return giftCertificateService.doFilter(allRequestParams);
    }
}
