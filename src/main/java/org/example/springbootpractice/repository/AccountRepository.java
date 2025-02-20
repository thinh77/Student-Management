package org.example.springbootpractice.repository;

import org.example.springbootpractice.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, String> {
    Optional<Account> findByUsername(String username);
    Boolean existsByUsername(String username);
    List<Account> findAllByRole(String role);
}
