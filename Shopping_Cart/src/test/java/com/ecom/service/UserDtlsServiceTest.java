package com.ecom.service;
import com.ecom.model.UserDtls;
import com.ecom.repository.UserDtlsRepository;
import com.ecom.service.impl.UserDtlsServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.multipart.MultipartFile;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserDtlsServiceTest {

    @Mock
    private MultipartFile multipartFile;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private UserDtlsRepository userDtlsRepository;

    @InjectMocks
    private UserDtlsServiceImpl userDtlsService;

    @Test
    void saveUserDtls() {

        UserDtls userDtls = new UserDtls();
        userDtls.setId(1);
        userDtls.setName("amit singh");
        userDtls.setPassword("123");

        when(passwordEncoder.encode("123")).thenReturn("u%6");

        when(multipartFile.isEmpty()).thenReturn(true);
        when(userDtlsRepository.save(userDtls)).thenReturn(userDtls);
        UserDtls result = userDtlsService.saveUser(userDtls, multipartFile);

        assertNotNull(result);
        assertEquals(1, result.getId());
        assertEquals("amit singh", result.getName());
        assertNotEquals("124", result.getPassword());
    }
}
