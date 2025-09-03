package com.ies.repositories;


import com.ies.entities.EligEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EligRepo extends JpaRepository<EligEntity, Integer> {
}