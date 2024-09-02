package dto;

import entity.enumration.Bank;

public record CardDto(
                String expireDate,
                String cardNumber,
                Integer cvv2,
                Bank bank
        ){}
