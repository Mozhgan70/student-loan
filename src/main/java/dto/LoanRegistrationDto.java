package dto;

import entity.LoanTypeCondition;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record LoanRegistrationDto(

        LoanTypeCondition loanType,
        SpouseDto spouse,
        CardDto card,

        @NotBlank(message = "address cannot be blank")
        @Size(max = 255, message = "address must be less than 255 characters")
        String address,

        @NotBlank(message = "contract Number cannot be blank")
        @Size(max = 13,min = 13, message = "contract Number must be 13 characters")
        String contractNumber
        ) {}