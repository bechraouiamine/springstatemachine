package com.bechraoui.springstatemachine.config.actions;

import com.bechraoui.springstatemachine.domain.PaymentEvent;
import com.bechraoui.springstatemachine.domain.PaymentState;
import com.bechraoui.springstatemachine.services.PaymentServiceImpl;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;
import org.springframework.stereotype.Component;

import java.util.Random;

/**
 * Created by BECHRAOUI, 21/10/2020
 */
@Component
public class AuthAction implements Action<PaymentState, PaymentEvent> {

    @Override
    public void execute(StateContext<PaymentState, PaymentEvent> stateContext) {
        System.out.println("Autho was called !!! ");
        if (new Random().nextInt(10) < 8) {
            System.out.println("Approuved");
            stateContext.getStateMachine().sendEvent(MessageBuilder.withPayload(PaymentEvent.AUTH_APPROVED)
                    .setHeader(PaymentServiceImpl.PAYMENT_ID_HEADER, stateContext.getMessageHeader(PaymentServiceImpl.PAYMENT_ID_HEADER))
                    .build()
            );

        } else {
            System.out.println("Auth Declined! Credit Card invalid !!!");

            stateContext.getStateMachine().sendEvent(MessageBuilder.withPayload(PaymentEvent.AUTH_DECLINED)
                    .setHeader(PaymentServiceImpl.PAYMENT_ID_HEADER, stateContext.getMessageHeader(PaymentServiceImpl.PAYMENT_ID_HEADER))
                    .build()
            );

        }
    }
}
