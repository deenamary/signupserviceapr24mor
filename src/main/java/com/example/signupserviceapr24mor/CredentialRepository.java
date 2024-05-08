package com.example.signupserviceapr24mor;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CredentialRepository extends JpaRepository<Credential,String> {
    boolean existsByUsername(String username);
}

