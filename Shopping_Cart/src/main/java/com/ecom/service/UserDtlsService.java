package com.ecom.service;

import java.util.List;

import com.ecom.dto.request.UserDtlsRequestDto;
import com.ecom.dto.response.UserDtlsResponseDto;
import org.springframework.web.multipart.MultipartFile;

import com.ecom.model.UserDtls;

public interface UserDtlsService {
	
	public abstract UserDtlsResponseDto saveUser(UserDtlsRequestDto userDtlsRequestDto, MultipartFile file);

	public abstract UserDtlsResponseDto findByEmail(String email);

	public abstract List<UserDtls> getAllUsersOfUserRole(String role);

	public abstract Boolean updateAccountStatus(Integer id, Boolean status);
	
	public abstract void increaseFailedAttempt(UserDtls user);
	
	public abstract void userAccountLock(UserDtls user);
	
	public abstract Boolean unlockAccountTimeExpired(UserDtls user);
	
	public abstract void resetAttempt(UserDtls user);

	public abstract Boolean checkAccountNonLocked(UserDtls user);
}
