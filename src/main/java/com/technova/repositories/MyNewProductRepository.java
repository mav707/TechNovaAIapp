package com.technova.repositories;

import com.technova.models.MyNewProductModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MyNewProductRepository extends JpaRepository<MyNewProductModel, Long> {
}
