package com.example.demomongodb.repository;

import com.example.demomongodb.entity.UserWalletEntity;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.mongodb.repository.Update;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface UserWalletRepository extends ReactiveMongoRepository<UserWalletEntity, String> {

    Mono<UserWalletEntity> findFirstByUsername(String username);

    @Update("{ '$inc' : { 'amount' : ?1 } }")
    @Query("{'username':  ?0 }")
    Mono<Long> deductAmount(String username, Double amount);
}
