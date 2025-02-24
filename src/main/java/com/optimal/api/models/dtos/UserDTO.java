package com.optimal.api.models.dtos;

import com.optimal.api.models.bo.UserBO;
import com.optimal.api.settings.RO;
import jakarta.persistence.*;
import lombok.*;
import jakarta.persistence.Id;

@Entity
@Table(name = "users")
@NoArgsConstructor(force = true) // Ensures fields are initialized
@AllArgsConstructor // Required for @Builder
@Builder
@Getter
@Setter
public class UserDTO implements RO<UserBO> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;
    private String name;
    private String email;
    private String gender;
    private String picture;
    private String country;
    private String state;
    private String city;

    @Override
    public UserBO toBO() {
        return UserBO.builder()
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
