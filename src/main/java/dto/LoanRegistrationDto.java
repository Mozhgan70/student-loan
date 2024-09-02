package dto;

import entity.LoanTypeCondition;

public record LoanRegistrationDto(
        LoanTypeCondition loanType,
        SpouseDto spouse,
        CardDto card,
        String address,
        String contractNumber
        ) {}
