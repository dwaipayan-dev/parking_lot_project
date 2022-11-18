package com.parkinglot.manage.Admin;

import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private static final Logger logger = LoggerFactory.getLogger(AdminController.class);

    @GetMapping("/")
    public String helloTest() {
        logger.info("HELLO METHOD CALLED");
        return "Hello";
    }

    @PostMapping("/signup")
    public boolean signup(@RequestBody Map<String, Object> request) {
        System.out.println(request.get("name"));
        System.out.println(request.get("password"));
        String userName = (String) (request.get("name"));
        String pwdEncoded = passwordEncoder.encode((String) (request.get("password")));
        boolean queryResult = adminService.createAdmin(userName, pwdEncoded);
        return queryResult;
    }

    @PostMapping("/login")
    public boolean login(@RequestBody Map<String, Object> request) {
        // long id = (long) request.get("id");
        // String password = (String) request.get("password");
        // Optional<Admin> queryResult = adminService.findAdminById(id);
        // if (!queryResult.isPresent()) {
        // return false;
        // } else {
        // String pwdEncoded = queryResult.get().getPwdHash();
        // if (passwordEncoder.matches(password, pwdEncoded) == true) {
        // return true;
        // }
        // return false;
        // }
        return false;
    }
}
