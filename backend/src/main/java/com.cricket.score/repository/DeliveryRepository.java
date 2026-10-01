package com.cricket.score.repository;

import com.cricket.score.entity.Delivery;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DeliveryRepository extends JpaRepository<Delivery, Long> {

    List<Delivery> findByInningsId(Long inningsId);
}