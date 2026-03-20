package com.ecom.user.config;

import com.ecom.common.model.Roles;
import com.ecom.user.model.UserAccount;
import com.ecom.user.repository.UserAccountRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class AdminSeeder implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(AdminSeeder.class);

    private static final String DEFAULT_ADMIN_EMAIL = "admin@demo.com";
    private static final String DEFAULT_ADMIN_PASSWORD = "admin123";

    @Autowired
    private UserAccountRepository repository;

    @Autowired
    private org.springframework.security.crypto.password.PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        repository.findByEmail(DEFAULT_ADMIN_EMAIL).ifPresentOrElse(
                user -> log.info("Default admin already present: {}", DEFAULT_ADMIN_EMAIL),
                () -> {
                    UserAccount admin = new UserAccount();
                    admin.setName("Admin");
                    admin.setEmail(DEFAULT_ADMIN_EMAIL);
                    admin.setPassword(passwordEncoder.encode(DEFAULT_ADMIN_PASSWORD));
                    admin.setRole(Roles.ADMIN);
                    admin.setMobileNo("9999999999");
                    admin.setAddress("N/A");
                    admin.setCity("N/A");
                    admin.setState("N/A");
                    admin.setPinCode("000000");
                    admin.setImageName("default.jpg");
                    admin.setEnabled(true);
                    admin.setAccountNonLocked(true);
                    admin.setFailedAttempt(0);
                    repository.save(admin);
                    log.info("Created default admin user {} / {}", DEFAULT_ADMIN_EMAIL, DEFAULT_ADMIN_PASSWORD);
                }
        );
    }
}
