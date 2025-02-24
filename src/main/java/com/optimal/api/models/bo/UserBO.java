package com.optimal.api.models.bo;

import com.optimal.api.models.dtos.UserDTO;
import com.optimal.api.settings.BO;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.validation.annotation.Validated;

@NoArgsConstructor(force = true) // Ensures fields are initialized
@AllArgsConstructor // Required for @Builder
@Builder
@Getter
@Setter
public class UserBO implements BO<UserDTO> {
    @NotBlank(message = "Username is required")
    @Size(min = 4, max = 20, message = "Username must be between 4 and 20 characters")
    private String username;

    @NotBlank(message = "Name is required")
    @Size(min = 2, max = 50, message = "Name must be between 2 and 50 characters")
    private String name;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;

    @NotBlank(message = "Gender is required")
    @Pattern(regexp = "Male|Female|Other", message = "Gender must be Male, Female, or Other")
    private String gender;

    @NotBlank(message = "Picture URL is required")
    @Pattern(regexp = "^(https?|ftp)://.*$", message = "Invalid URL format")
    private String picture;

    @NotBlank(message = "Country is required")
    private String country;

    @NotBlank(message = "State is required")
    private String state;

    @NotBlank(message = "City is required")
    private String city;

    @Override
    public UserDTO toRO() {
        return UserDTO.builder()
                .username(this.username)
                .name(this.name)
                .email(this.email)
                .gender(this.gender)
                .picture(this.picture)
                .country(this.country)
                .state(this.state)
                .city(this.city)
                .build();
    }
}
