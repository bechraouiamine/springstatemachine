package com.bechraoui.springstatemachine.domain;

/**
 * Created by BECHRAOUI, 20/10/2020
 */
public enum PaymentEvent {
    PRE_AUTHORIZE,
    PRE_AUTH_APPROVED,
    PRE_AUTH_DECLINED,
    AUTHORIZE,
    AUTH_APPROVED,
    AUTH_DECLINED
}
