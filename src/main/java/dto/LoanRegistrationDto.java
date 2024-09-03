package dto;

import entity.LoanTypeCondition;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record LoanRegistrationDto(

        LoanTypeCondition loanType,

        @Valid
        SpouseDto spouse,
        @NotNull(message = "Card data is required.")
        @Valid
        CardDto card,


        @Size(max = 255, message = "address must be less than 255 characters")
        String address,


        @Size(max = 13,min = 13, message = "contract Number must be 13 characters")
        String contractNumber
        ) {}