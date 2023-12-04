package com.epam.esm.controllers;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.exceptions.DaoException;
import com.epam.esm.service.GiftCertificateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST Controller for handling GiftCertificate related requests.
 * This class uses the GiftCertificateService to process the requests.
 *
 * @author Sarvar
 * @version 1.0
 * @since 2023-12-03
 */
@RestController
@RequestMapping("/api/v1/certificates")
public class GiftCertificateController {
    private final GiftCertificateService giftCertificateService;

    /**
     * Constructor for the GiftCertificateController class.
     * It sets the GiftCertificateService.
     *
     * @param giftCertificateService The GiftCertificateService to set.
     */
    @Autowired
    public GiftCertificateController(GiftCertificateService giftCertificateService) {
        this.giftCertificateService = giftCertificateService;
    }

    /**
     * Handles the GET request to get all gift certificates.
     *
     * @return List of all gift certificates.
     * @throws DaoException If there is an error with the database.
     */
    @GetMapping
    public List<GiftCertificate> allGiftCertificates() throws DaoException {
        return giftCertificateService.findAll();
    }

    /**
     * Handles the GET request to get a gift certificate by its ID.
     *
     * @param id The ID of the gift certificate to get.
     * @return The gift certificate with the given ID.
     * @throws DaoException                If there is an error with the database.
     */

    @GetMapping("/{id}")
    public GiftCertificate giftCertificateById(@PathVariable("id") long id) throws DaoException {
        return giftCertificateService.findById(id);
    }

    /**
     * Handles the DELETE request to delete a gift certificate by its ID.
     *
     * @param id The ID of the gift certificate to delete.
     * @return ResponseEntity with the status and message.
     * @throws DaoException                If there is an error with the database.
     */

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteGiftCertificate(@PathVariable("id") long id) throws DaoException {
        giftCertificateService.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Success");
    }

    /**
     * Handles the POST request to create a gift certificate.
     *
     * @param giftCertificate The gift certificate to create.
     * @return ResponseEntity with the status and message.
     * @throws DaoException                If there is an error with the database.
     */
    @PostMapping
    public ResponseEntity<?> createGiftCertificate(@RequestBody GiftCertificate giftCertificate)
            throws DaoException {
        giftCertificateService.save(giftCertificate);
        return ResponseEntity.status(HttpStatus.CREATED).body("Success");
    }

    /**
     * Handles the PATCH request to update a gift certificate by its ID.
     *
     * @param id              The ID of the gift certificate to update.
     * @param giftCertificate The gift certificate with the updated values.
     * @return ResponseEntity with the status and message.
     * @throws DaoException                If there is an error with the database.
     */
    @PatchMapping("/{id}")
    public ResponseEntity<String> updateGiftCertificate(@PathVariable("id") long id, @RequestBody GiftCertificate giftCertificate)
            throws DaoException {
        giftCertificateService.update(id, giftCertificate);
        return ResponseEntity.status(HttpStatus.CREATED).body("Success");
    }

    /**
     * Handles the GET request to get gift certificates by parameters.
     *
     * @param allRequestParams The parameters to filter the gift certificates by.
     * @return List of gift certificates that match the parameters.
     * @throws DaoException If there is an error with the database.
     */

    @GetMapping("/filter")
    public List<GiftCertificate> giftCertificatesByParameter(@RequestParam MultiValueMap<String, String> allRequestParams)
            throws DaoException {
        return giftCertificateService.doFilter(allRequestParams);
    }
}
