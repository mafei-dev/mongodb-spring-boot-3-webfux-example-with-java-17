package com.example.demomongodb.repository;

import com.example.demomongodb.entity.UserOrderPaymentEntity;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserOrderPaymentRepository extends ReactiveMongoRepository<UserOrderPaymentEntity,String> {
}
