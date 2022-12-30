package com.parkinglot.manage.Admin;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger logger = LoggerFactory.getLogger(AdminUserDetailsService.class);

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Admin> queryResult = adminRepo.findByName(username);
        if (!queryResult.isPresent()) {
            throw new UsernameNotFoundException("Admin not found. Please create admin account");
        } else {
            Admin admin = queryResult.get();
            UserDetails adminDetails = User.withUsername(admin.getName()).password(admin.getPwdHash())
                    .authorities("ADMIN").build();
            logger.info("UserNAME Is: " + username);
            return adminDetails;
        }
    }

}