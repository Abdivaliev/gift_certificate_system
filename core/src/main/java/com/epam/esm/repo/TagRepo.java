package com.epam.esm.repo;

import com.epam.esm.entity.Tag;
import jakarta.persistence.Tuple;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TagRepo extends JpaRepository<Tag, Long> {
    String query ="WITH UserTagCosts AS (SELECT u.id         AS user_id,\n" +
            "                             t.id         as tag_id,\n" +
            "                             t.name       AS tag_name,\n" +
            "                             COUNT(*)     AS tag_count,\n" +
            "                             SUM(uo.purchasecost) AS total_cost\n" +
            "                      FROM users u\n" +
            "                               JOIN orders uo ON u.id = uo.user_id\n" +
            "                               JOIN gift_certificates gc ON uo.giftcertificate_id = gc.id\n" +
            "                               JOIN gift_certificates_tags gct ON gc.id = gct.gift_certificate_id\n" +
            "                               JOIN tags t ON gct.tag_id = t.id\n" +
            "                      WHERE u.id = :userId\n" +
            "                      GROUP BY u.id, t.name,t.id)\n" +
            "SELECT user_id,\n" +
            "       tag_id,\n" +
            "       tag_name,\n" +
            "       tag_count,\n" +
            "       total_cost\n" +
            "FROM UserTagCosts\n" +
            "WHERE (tag_count, total_cost) = (SELECT MAX(tag_count),\n" +
            "                                        MAX(total_cost)\n" +
            "                                 FROM UserTagCosts);";


    Optional<Tag> findByName(String name);

    @Query(value = query, nativeQuery = true)
    List<Tuple> findMostUsedTagByUserId(@Param("userId") Long userId);

    @Query(value = "EXPLAIN " + query, nativeQuery = true)
    List<Tuple> findMostUsedTagByUserIdExplanation(@Param("userId") Long userId);
}
