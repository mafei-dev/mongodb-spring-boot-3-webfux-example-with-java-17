package com.example.demomongodb.service;

import com.example.demomongodb.dto.PaymentDetailsDTO;
import com.example.demomongodb.entity.UserOrderPaymentEntity;
import com.example.demomongodb.entity.UserWalletEntity;
import com.example.demomongodb.exception.BalanceNotSufficientException;
import com.example.demomongodb.repository.UserOrderPaymentRepository;
import com.example.demomongodb.repository.UserWalletRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;
import reactor.util.function.Tuples;

import java.time.LocalDateTime;

@AllArgsConstructor
@Service
@Slf4j
@Transactional
public class PaymentService {

    private final UserOrderPaymentRepository orderPaymentRepository;
    private final UserWalletRepository userWalletRepository;
//    private final MongoTemplate mongoTemplate;

    /**
     * @param paymentDetails
     * @return T1 = transaction-id, T2 = payment-status
     */
    public Mono<Tuple2<String, UserOrderPaymentEntity.PaymentStatus>> makePayment(PaymentDetailsDTO paymentDetails) {
        return this.userWalletRepository
                .deductAmount(paymentDetails.username(), paymentDetails.amount() * -1)
                .flatMap(aLong -> {
                    if (aLong == 1) {
                        return this.userWalletRepository.findFirstByUsername(paymentDetails.username());
                    } else {
                        return Mono.error(new RuntimeException("Something went wrong."));
                    }
                })
                .flatMap(userWalletEntity -> {
                            System.out.println("userWalletEntity " + userWalletEntity);
                            if (userWalletEntity.amount() <= 0) {
                                return Mono.error(new BalanceNotSufficientException("Balance is not enough."));
                            } else {
                                return this.orderPaymentRepository
                                        .save(new UserOrderPaymentEntity(
                                                null,
                                                paymentDetails.amount(),
                                                UserOrderPaymentEntity.PaymentStatus.SUCCESS,
                                                paymentDetails.username(),
                                                LocalDateTime.now()
                                        ));
                            }
                        }
                )
                .map(entity ->
                        Tuples.of(entity.userOrderPaymentId(), entity.paymentStatus())
                );
    }

    public Mono<Void> createWallet(String username, double initAmount) {
        return this.userWalletRepository
                .findFirstByUsername(username)
                .doOnNext(userWalletEntity -> log.info("the user ({}) already has a wallet.", userWalletEntity.username()))
                .switchIfEmpty(this.userWalletRepository.save(new UserWalletEntity(null, initAmount, username)))
                .then();
    }

}
