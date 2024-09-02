package dto;

import entity.enumration.Bank;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record CardDto(

                @Pattern(regexp = "^\\d{2}\\/(0[1-9]|1[0-2])$", message = "User Name must be exactly 10 digits")
                @NotBlank(message = "expire date cannot be blank")
                String expireDate,

                @NotBlank(message = "card number cannot be blank")
                @Size(max = 16,min = 16, message = "card number must be 16 character")
                String cardNumber,

                @NotBlank(message = "cvv2 cannot be blank")
                @Size(max = 4,min = 3, message = "card number must be 16 character")
                Integer cvv2,
                Bank bank
        ){}
