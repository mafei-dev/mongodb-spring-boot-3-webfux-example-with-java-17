package com.example.demomongodb.entity;

import lombok.Builder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;


@Builder
@Document(value = "user_order_payment")
public record UserOrderPaymentEntity(
        @Id
        String userOrderPaymentId,
        double amount,
        PaymentStatus paymentStatus,
        String username,
        LocalDateTime transactionTime
) {
    public enum PaymentStatus {
        SUCCESS,
        FAILED
    }
}
