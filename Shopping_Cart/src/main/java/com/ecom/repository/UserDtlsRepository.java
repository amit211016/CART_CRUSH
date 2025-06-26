package com.ecom.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ecom.model.UserDtls;

@Repository
public interface UserDtlsRepository extends JpaRepository<UserDtls, Integer> {
	public abstract UserDtls findByEmail(String email);

	public abstract List<UserDtls> findByRole(String role);
}
