package com.technova.repositories;

import com.technova.models.DemoScheduleModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DemoScheduleRepository extends JpaRepository<DemoScheduleModel, Long> {
}
