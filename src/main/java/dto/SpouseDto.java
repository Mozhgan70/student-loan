package dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record SpouseDto(
        @NotBlank(message = "Spouse Name cannot be blank")
        @Size(max = 50, message = "Spouse Name must be less than 50 characters")
        @Pattern(regexp = "^[a-zA-Z\\s]+$", message = "Spouse Name can only contain letters")
         String name,

        @NotBlank(message = "Spouse Last Name cannot be blank")
        @Size(max = 50, message = "Spouse Last Name must be less than 50 characters")
        @Pattern(regexp = "^[a-zA-Z\\s]+$", message = "Spouse Last Name can only contain letters")
         String lastName,

        @NotBlank(message = "Spouse National ID cannot be blank")
        @Pattern(regexp = "^\\d{10}$", message = "Spouse National ID must be exactly 10 digits")
         String nationalCode) {}
