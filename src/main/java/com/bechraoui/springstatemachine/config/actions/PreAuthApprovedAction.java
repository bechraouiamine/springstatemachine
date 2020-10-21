package com.bechraoui.springstatemachine.config.actions;

import com.bechraoui.springstatemachine.domain.PaymentEvent;
import com.bechraoui.springstatemachine.domain.PaymentState;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;
import org.springframework.stereotype.Component;

/**
 * Created by BECHRAOUI, 21/10/2020
 */
@Component
public class PreAuthApprovedAction implements Action<PaymentState, PaymentEvent> {
    @Override
    public void execute(StateContext<PaymentState, PaymentEvent> context) {
        System.out.println("Sending Notification of PreAuth Approved");
    }
}
