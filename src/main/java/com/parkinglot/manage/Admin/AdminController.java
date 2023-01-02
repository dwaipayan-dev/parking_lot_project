package com.parkinglot.manage.Admin;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.parkinglot.manage.RequestModel.JwtRequest;
import com.parkinglot.manage.ResponseModel.JwtResponse;
import com.parkinglot.manage.Utilities.JwtUtilities.JwtUtil;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtTokenUtil;

    @Autowired
    private AdminUserDetailsService adminUserDetailsService;

    private static final Logger logger = LoggerFactory.getLogger(AdminController.class);

    @GetMapping("/")
    public String helloTest() {
        logger.info("HELLO METHOD CALLED");
        return "Hello";
    }

    @PostMapping("/signup")
    public boolean signup(@RequestBody JwtRequest request) {
        System.out.println(request.getUsername());
        System.out.println(request.getPassword());
        String userName = request.getUsername();
        String pwdEncoded = passwordEncoder.encode(request.getPassword());
        boolean queryResult = adminService.createAdmin(userName, pwdEncoded);
        return queryResult;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody JwtRequest request) {
        final UserDetails userDetails = adminUserDetailsService.loadUserByUsername(request.getUsername());
        final String token = jwtTokenUtil.generateToken(userDetails);
        return ResponseEntity.ok(new JwtResponse(token));
    }
}
