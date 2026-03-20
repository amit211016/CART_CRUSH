package com.ecom.user.config;

import com.ecom.user.model.UserAccount;
import com.ecom.user.repository.UserAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserAccountRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserAccount user = repository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("user not found"));
        return new CustomUserDetails(user);
    }
}
