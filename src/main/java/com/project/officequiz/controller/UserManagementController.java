package com.project.officequiz.controller;

import com.project.officequiz.dto.UserDTO;
import com.project.officequiz.service.UserManagementService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UserManagementController {

    private final UserManagementService userManagementService;

    public UserManagementController(UserManagementService userManagementService) {
        this.userManagementService = userManagementService;
    }

    @GetMapping("/login")
    public String showLoginForm(@RequestParam(required = false) String error) {
        return "login";
    }

    @GetMapping("/activate")
    public String showAccountActivationForm(@RequestParam(name = "code") String activationCode, Model model) {
        model.addAttribute("activationCode",activationCode);
        return "activateUser";
    }

    @PostMapping("/activate")
    public String activateUser(@ModelAttribute UserDTO userDTO) {
        boolean isValidUser = userManagementService.validateUserDetailsForActivation(userDTO);
        return isValidUser?"redirect:/account-activation-success":"error";
    }

    @GetMapping("/register")
    public String showRegistrationForm() {
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute UserDTO userDTO) throws Exception{
        userManagementService.createUser(userDTO);
        return "redirect:/quiz";
    }
}
