package com.bechraoui.springstatemachine.services;

import com.bechraoui.springstatemachine.domain.Payment;
import com.bechraoui.springstatemachine.domain.PaymentEvent;
import com.bechraoui.springstatemachine.domain.PaymentState;
import com.bechraoui.springstatemachine.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineFactory;
import org.springframework.statemachine.support.DefaultStateMachineContext;
import org.springframework.stereotype.Service;

import javax.swing.plaf.nimbus.State;

/**
 * Created by BECHRAOUI, 20/10/2020
 */
@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private PaymentRepository paymentRepository;
    private StateMachineFactory<PaymentState, PaymentEvent> stateMachineFactory;

    public static final String PAYMENT_ID_HEADER = "payment_id";


    @Override
    public Payment newPayment(Payment payment) {
        payment.setState(PaymentState.NEW);

        return paymentRepository.save(payment);
    }

    @Override
    public StateMachine<PaymentState, PaymentEvent> preAuth(Long paymenetId) {
        StateMachine<PaymentState, PaymentEvent> sm = build(paymenetId);

        sendEvent(paymenetId, sm, PaymentEvent.PRE_AUTHORIZE);

        return null;
    }

    @Override
    public StateMachine<PaymentState, PaymentEvent> authorizePayment(Long paymenetId) {
        StateMachine<PaymentState, PaymentEvent> sm = build(paymenetId);

        sendEvent(paymenetId, sm, PaymentEvent.AUTH_APPROVED);

        return null;
    }

    @Override
    public StateMachine<PaymentState, PaymentEvent> declineAuth(Long paymenetId) {
        StateMachine<PaymentState, PaymentEvent> sm = build(paymenetId);

        sendEvent(paymenetId, sm, PaymentEvent.AUTH_DECLINED);

        return null;
    }

    private StateMachine<PaymentState, PaymentEvent> build(Long paymentId) {
        Payment payment = paymentRepository.getOne(paymentId);

        StateMachine<PaymentState, PaymentEvent> sm = stateMachineFactory.getStateMachine(Long.toString(payment.getId()));

        sm.stop();

        sm.getStateMachineAccessor().doWithRegion(sma -> {
            sma.resetStateMachine(new DefaultStateMachineContext<>(payment.getState(), null,null,null));
        });

        sm.start();

        return sm;
    }

    private void sendEvent(Long paymentId, StateMachine<PaymentState, PaymentEvent> sm, PaymentEvent event) {
        Message msg = MessageBuilder.withPayload(event)
                .setHeader(PAYMENT_ID_HEADER, paymentId)
                .build();
        sm.sendEvent(msg);
    }
}
