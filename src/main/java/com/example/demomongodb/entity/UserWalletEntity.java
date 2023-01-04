package com.example.demomongodb.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(value = "wallet")
public record UserWalletEntity(@Id String id,
                               double amount,
                               String username) {
}
