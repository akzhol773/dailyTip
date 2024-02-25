package com.neobis.dailytip.dto;



import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import java.io.Serializable;


public record UsersDto(
        @NotNull(message = "Email cannot be null") @NotBlank(message = "Email cannot be blank")
        @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$", message = "Invalid email format")
        String email,

        String name
) implements Serializable {


}
