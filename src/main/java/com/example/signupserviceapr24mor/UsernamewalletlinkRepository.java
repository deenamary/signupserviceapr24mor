package com.example.signupserviceapr24mor;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UsernamewalletlinkRepository extends JpaRepository<Usernamewalletlink, String> {
    Usernamewalletlink findByUsername(String username);
}
