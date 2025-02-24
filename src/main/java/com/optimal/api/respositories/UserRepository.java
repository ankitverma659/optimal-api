package com.optimal.api.respositories;


import com.optimal.api.models.dtos.UserDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserDTO, Long> {
    Optional<UserDTO> findByUsername(String username);

    Page<UserDTO> findAll(Pageable pageable);

    boolean existsByUsername(String username);
}
