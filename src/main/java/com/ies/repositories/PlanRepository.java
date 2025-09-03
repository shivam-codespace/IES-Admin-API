package com.ies.repositories;

import com.ies.entities.PlansEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface PlanRepository extends JpaRepository<PlansEntity, Integer> {

    @Modifying
    @Transactional
    @Query("update PlansEntity set activeSw = :status where user.id = :userId")
    public Integer changePStatus(Integer userId, String status);
}
