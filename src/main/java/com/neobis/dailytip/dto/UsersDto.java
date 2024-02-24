package com.neobis.dailytip.dto;



import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.io.Serializable;

/**
 * DTO for {@link com.neobis.dailytip.entities.Users}
 */

public record UsersDto(
        @NotNull(message = "Email cannot be null") @NotBlank(message = "Phone number cannot be blank")
        @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$\n", message = "Invalid email format")
        String email,
        String name) implements Serializable {
}