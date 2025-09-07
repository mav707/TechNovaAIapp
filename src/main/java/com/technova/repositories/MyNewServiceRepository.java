package com.technova.repositories;

import com.technova.models.MyNewServiceModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MyNewServiceRepository extends JpaRepository<MyNewServiceModel, Long> {
}
