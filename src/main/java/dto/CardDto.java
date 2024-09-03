package dto;

import entity.enumration.Bank;
import jakarta.validation.constraints.*;

public record CardDto(

                @Pattern(regexp = "^\\d{2}\\/(0[1-9]|1[0-2])$", message = "expire date must be this format YY/MM and MM must be <13")
                @NotBlank(message = "expire date cannot be blank")
                String expireDate,

                @NotBlank(message = "card number cannot be blank")
                @Size(max = 16,min = 16, message = "card number must be 16 character")
                String cardNumber,

                @NotNull(message = "CVV2 cannot be null")
                @Min(value = 100, message = "CVV2 must be at least 3 digits")
                @Max(value = 9999, message = "CVV2 cannot be more than 4 digits")
                Integer cvv2,
                Bank bank
        ){}
