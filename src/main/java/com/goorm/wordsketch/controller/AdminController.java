package com.goorm.wordsketch.controller;

import com.goorm.wordsketch.dto.AdminWordContent;
import com.goorm.wordsketch.service.AdminService;
import com.goorm.wordsketch.util.ErrorHandler;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin("*")
@RestController
@RequestMapping("/admin")
public class AdminController {
    private final AdminService adminService;
    private final ErrorHandler errorHandler;

    @Autowired
    public AdminController(AdminService adminService, ErrorHandler errorHandler) {
        this.adminService = adminService;
        this.errorHandler = errorHandler;
    }

    @GetMapping("/wordlist")
    public ResponseEntity<?> getWordList() {
        try {
            List<AdminWordContent> result = adminService.getAdminWordList();
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            return errorHandler.errorMessage(e);
        }
    }
}
