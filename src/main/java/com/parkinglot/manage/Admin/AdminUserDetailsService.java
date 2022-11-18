package com.parkinglot.manage.Admin;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * AdminUserDetailsService
 */

@Service
public class AdminUserDetailsService implements UserDetailsService {
    @Autowired
    private AdminRepository adminRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Admin> queryResult = adminRepo.findByName(username);
        if (!queryResult.isPresent()) {
            throw new UsernameNotFoundException("Admin not found. Please create admin account");
        } else {
            Admin admin = queryResult.get();
            UserDetails adminDetails = User.withUsername(admin.getName()).password(admin.getPwdHash())
                    .authorities("ADMIN").build();
            return adminDetails;
        }
    }

}