package com.technova.repositories;

import com.technova.models.CompanyProductsModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CompanyProductsRepository extends JpaRepository<CompanyProductsModel, Long> {

    @Query("SELECT DISTINCT p FROM CompanyProductsModel p LEFT JOIN p.technologies t " +
            "WHERE LOWER(p.productHeading) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "OR LOWER(t) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<CompanyProductsModel> searchByHeadingOrTechnologies(String keyword);
}

