package com.ecom.user.repository;

import com.ecom.user.model.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserAccountRepository extends JpaRepository<UserAccount, Integer> {
    Optional<UserAccount> findByEmail(String email);
    List<UserAccount> findByRole(String role);
    boolean existsByEmail(String email);
}
