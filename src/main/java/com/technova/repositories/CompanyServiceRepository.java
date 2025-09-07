package com.technova.repositories;

import com.technova.models.CompanyServicesModel;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.awt.print.Pageable;
import java.util.List;
import java.util.Optional;

public interface CompanyServiceRepository extends JpaRepository<CompanyServicesModel, Long> {

    @Query("SELECT DISTINCT s FROM CompanyServicesModel s LEFT JOIN s.technologies t " +
            "WHERE LOWER(s.serviceHeading) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "OR LOWER(t) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<CompanyServicesModel> searchByHeadingOrTechnologies(String keyword);

    // No need to declare findAll(Pageable pageable), it's inherited from JpaRepository
}