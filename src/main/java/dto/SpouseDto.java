package dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record SpouseDto(
        @NotBlank(message = "Name cannot be blank")
        @Size(max = 50, message = "Name must be less than 50 characters")
        @Pattern(regexp = "^[a-zA-Z\\s]+$", message = "Name can only contain letters")
         String name,

        @NotBlank(message = "Name cannot be blank")
        @Size(max = 50, message = "Name must be less than 50 characters")
        @Pattern(regexp = "^[a-zA-Z\\s]+$", message = "Name can only contain letters")
         String lastName,

        @NotBlank(message = "National ID cannot be blank")
        @Pattern(regexp = "^\\d{10}$", message = "National ID must be exactly 10 digits")
         String nationalCode) {}
