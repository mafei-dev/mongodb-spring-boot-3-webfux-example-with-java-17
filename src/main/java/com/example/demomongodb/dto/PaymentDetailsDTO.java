package com.example.demomongodb.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;


@Builder
public record PaymentDetailsDTO(
        @NotNull @JsonProperty("username") String username,
        @NotNull @JsonProperty("ref_id") String refId,
        @NotNull @Min(0L) @JsonProperty("amount") double amount
) {

}
