package com.parkinglot.manage.Admin;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminService {

    @Autowired
    private AdminRepository adminRepo;

    public boolean createAdmin(String name, String pwdHash) {
        Optional<Admin> queryExists = adminRepo.findByName(name);
        if (queryExists.isPresent())
            return false;
        try {
            adminRepo.save(new Admin(name, pwdHash));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public Optional<Admin> findAdminById(Long Id) {
        Optional<Admin> queryResult = adminRepo.findById(Id);
        if (queryResult.isPresent()) {
            return queryResult;
        }
        return null;
    }
}
