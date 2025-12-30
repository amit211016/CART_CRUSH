package com.ecom.service.impl;


import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import com.ecom.dto.request.UserDtlsRequestDto;
import com.ecom.dto.response.UserDtlsResponseDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import com.ecom.model.UserDtls;
import com.ecom.repository.UserDtlsRepository;
import com.ecom.service.UserDtlsService;
import com.ecom.util.AppConstant;

@Service
public class UserDtlsServiceImpl implements UserDtlsService{

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
	private UserDtlsRepository userDtlsRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Override
	public UserDtlsResponseDto saveUser(UserDtlsRequestDto userDtlsRequestDto, MultipartFile img) {
		UserDtls userDtls = modelMapper.map(userDtlsRequestDto, UserDtls.class);
        userDtls.setRole("ROLE_USER");
        String encodePassword = passwordEncoder.encode(userDtlsRequestDto.getPassword());
        userDtls.setPassword(encodePassword);
        userDtls.setAccountNonLocked(true);
        userDtls.setFailedAttempt(0);
        String imgName = img.isEmpty() ? "default.jpg" : img.getOriginalFilename();
        userDtls.setImageName(imgName);
        userDtls.setIsEnable(true);
		UserDtls saveUserDtls = userDtlsRepository.save(userDtls);


		if(!ObjectUtils.isEmpty(saveUserDtls)) {
			try {
				File saveFile = new ClassPathResource("static/img").getFile();

				Path path = Paths.get(saveFile.getAbsolutePath() + File.separator + "profile_img" + File.separator
						+ imgName);
				Files.copy(img.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
			}catch(Exception ex) {
				ex.printStackTrace();
			}
			return modelMapper.map(saveUserDtls, UserDtlsResponseDto.class);
		}
		return null;
	}

	@Override
	public UserDtlsResponseDto findByEmail(String email) {
		UserDtls userDtls = userDtlsRepository.findByEmail(email);
		return modelMapper.map(userDtls, UserDtlsResponseDto.class);
	}

	@Override
	public List<UserDtls> getAllUsersOfUserRole(String role) {
        return userDtlsRepository.findByRole(role);
    }

	@Override
	public Boolean updateAccountStatus(Integer id, Boolean status) {
		Optional<UserDtls> userDtls = userDtlsRepository.findById(id);
		
		//obj.isPresent() is a method used in Java when working with the Optional class. It is used to check if a value is present (i.e., not null) inside the Optional object.


		if(userDtls.isPresent()) {
			//objectUserDtls.get() is used when you're working with an Optional<T> in Java and you want to retrieve the actual value stored inside the Optional—assuming it is present.
//Important:You must call .get() only after checking that the value is present using .isPresent() to avoid NoSuchElementException.
			UserDtls user = userDtls.get();
			user.setIsEnable(status);
			userDtlsRepository.save(user);
			return true;
		}
		
		return false;
	}

	@Override
	public void increaseFailedAttempt(UserDtls user) {
		user.setFailedAttempt(user.getFailedAttempt()+1);
		userDtlsRepository.save(user);
	}

	@Override
	public void userAccountLock(UserDtls user) {
		user.setAccountNonLocked(false);
		user.setLockTime(new Date());
		userDtlsRepository.save(user);
	}

	@Override
	public Boolean unlockAccountTimeExpired(UserDtls user) {
		Long lockTime = user.getLockTime().getTime();
		Long unLockTime = lockTime + AppConstant.UNLOCK_DURATION_TIME;
		
		Long currentTime = System.currentTimeMillis();
		if(unLockTime < currentTime) {
			resetAttempt(user);
			user.setAccountNonLocked(true);
			user.setLockTime(null);
			userDtlsRepository.save(user);
			return true;
		}
		return false;
	}

	@Override
	public void resetAttempt(UserDtls user) {
		user.setFailedAttempt(0);
	}

	@Override
	public Boolean checkAccountNonLocked(UserDtls user) {
		 if(!ObjectUtils.isEmpty(user.getLockTime())) {
			 return unlockAccountTimeExpired(user);
		 }
		return true;
	}

}
