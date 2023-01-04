package com.example.demomongodb.controller;

import com.example.demomongodb.dto.PaymentDetailsDTO;
import com.example.demomongodb.exception.BalanceNotSufficientException;
import com.example.demomongodb.service.PaymentService;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/payment")
@AllArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping("/make-payment")
    public Mono<ResponseEntity<MakePaymentResponse>> makePayment1(@RequestBody @Validated PaymentDetailsDTO paymentDetails) {
        return this.paymentService
                .makePayment(paymentDetails)
                .map(objects -> new MakePaymentResponse(objects.getT1(), objects.getT2().name()))
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build())
                .onErrorResume(throwable -> {
                    if (throwable instanceof BalanceNotSufficientException) {
                        return Mono.just(ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build());
                    } else {
                        return Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build());
                    }
                });
    }

    @Builder
    record MakePaymentResponse(
            @JsonProperty("transaction_id") String transactionId,
            @JsonProperty("status") String status) {
    }
}
