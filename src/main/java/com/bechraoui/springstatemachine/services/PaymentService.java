package com.bechraoui.springstatemachine.services;

import com.bechraoui.springstatemachine.domain.Payment;
import com.bechraoui.springstatemachine.domain.PaymentEvent;
import com.bechraoui.springstatemachine.domain.PaymentState;
import org.springframework.statemachine.StateMachine;

/**
 * Created by BECHRAOUI, 20/10/2020
 */
public interface PaymentService {

    Payment newPayment(Payment payment);

    StateMachine<PaymentState, PaymentEvent> preAuth(Long paymenetId);

    StateMachine<PaymentState, PaymentEvent> authorizePayment(Long paymenetId);

    StateMachine<PaymentState, PaymentEvent> declineAuth(Long paymenetId);
}
