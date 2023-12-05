package com.epam.esm.controllers;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.exceptions.DaoException;
import com.epam.esm.service.CRUDService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST Controller for handling GiftCertificate related requests.
 * This class uses the GiftCertificateService to process the requests.
 */
@RestController
@RequestMapping("/api/v1/certificates")
public class GiftCertificateController {
    private final CRUDService<GiftCertificate> crudService;


    @Autowired
    public GiftCertificateController(CRUDService<GiftCertificate> crudService) {
        this.crudService = crudService;
    }

    @GetMapping(produces = "application/json")
    public List<GiftCertificate> getAllGiftCertificates() throws DaoException {
        return crudService.findAll();
    }


    @GetMapping(path = "/{id}", produces = "application/json")
    public GiftCertificate getGiftCertificateById(@PathVariable("id") long id) throws DaoException {
        return crudService.findById(id);
    }


    @DeleteMapping(path = "/{id}", produces = "application/json")
    public ResponseEntity<?> deleteGiftCertificate(@PathVariable("id") long id) throws DaoException {
        crudService.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Success");
    }


    @PostMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> createGiftCertificate(@RequestBody GiftCertificate giftCertificate)
            throws DaoException {
        crudService.save(giftCertificate);
        return ResponseEntity.status(HttpStatus.CREATED).body("Success");
    }

    @PatchMapping(path = "/{id}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<String> updateGiftCertificate(@PathVariable("id") long id, @RequestBody GiftCertificate giftCertificate)
            throws DaoException {
        crudService.update(id, giftCertificate);
        return ResponseEntity.status(HttpStatus.CREATED).body("Success");
    }


    @GetMapping(path = "/filter", produces = "application/json")
    public List<GiftCertificate> getGiftCertificatesByParameter(@RequestParam MultiValueMap<String, String> allRequestParams)
            throws DaoException {
        return crudService.doFilter(allRequestParams);
    }
}
