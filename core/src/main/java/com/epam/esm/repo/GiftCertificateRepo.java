package com.epam.esm.repo;

import com.epam.esm.dto.PageRequest;
import com.epam.esm.entity.GiftCertificate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.util.MultiValueMap;

import java.util.List;
import java.util.Optional;

@Repository
public interface GiftCertificateRepo extends JpaRepository<GiftCertificate, Long> {

    String query= "SELECT DISTINCT gc FROM GiftCertificate gc " +
            "JOIN gc.tags t " +
            "WHERE (:name IS NULL OR gc.name = :name) " +
            "AND (:partOfName IS NULL OR gc.name LIKE CONCAT('%', :partOfName, '%')) " +
            "AND (:partOfDescription IS NULL OR gc.description LIKE CONCAT('%', :partOfDescription, '%')) " +
            "AND (:tagNames IS NULL OR t.name IN :tagNames) " +
            "ORDER BY " +
            "CASE WHEN :sortByCreateDate = 'ASC' THEN gc.createDate END ASC, " +
            "CASE WHEN :sortByCreateDate = 'DESC' THEN gc.createDate END DESC, " +
            "CASE WHEN :sortByName = 'ASC' THEN gc.name END ASC, " +
            "CASE WHEN :sortByName = 'DESC' THEN gc.name END DESC";
    Optional<GiftCertificate> findByName(String name);
    @Query(value = query,nativeQuery = true)
    Page<GiftCertificate> findGiftCertificatesWithParams(@Param("name") String name,
                                                         @Param("partOfName") String partOfName,
                                                         @Param("partOfDescription") String partOfDescription,
                                                         @Param("tagNames") List<String> tagNames,
                                                         @Param("sortByCreateDate") String sortByCreateDate,
                                                         @Param("sortByName") String sortByName, Pageable pageable);
}
