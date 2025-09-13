package com.technova.repositories;

import com.technova.models.EmployeeModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<EmployeeModel, Long> {

    @Query("SELECT e FROM EmployeeModel e WHERE LOWER(e.fullName) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "OR LOWER(e.position) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "OR LOWER(e.department) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<EmployeeModel> searchByKeyword(String keyword);

    // ✅ Core team query
    List<EmployeeModel> findByCoreTeamTrue();
}
