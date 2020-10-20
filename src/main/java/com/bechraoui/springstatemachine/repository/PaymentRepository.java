package com.bechraoui.springstatemachine.repository;

import com.bechraoui.springstatemachine.domain.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by BECHRAOUI, 20/10/2020
 */
public interface PaymentRepository extends JpaRepository<Payment, Long> {
}
