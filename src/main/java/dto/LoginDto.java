package dto;

import jakarta.validation.constraints.Pattern;

public record LoginDto(

        @Pattern(regexp = "^\\d{10}$", message = "User Name must be exactly 10 digits")
        String userName,
        @Pattern(regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\\\d)(?=.*[@#$%&]).{8,}$", message = "Password must be have 8 char and contain symbol number upper and lower case char ")
        String password
) {
}
