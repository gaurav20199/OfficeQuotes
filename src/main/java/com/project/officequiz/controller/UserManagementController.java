package com.project.officequiz.controller;

import com.project.officequiz.dto.UserDTO;
import com.project.officequiz.service.UserManagementService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

@Controller
public class UserManagementController {

    private final UserManagementService userManagementService;

    public UserManagementController(UserManagementService userManagementService) {
        this.userManagementService = userManagementService;
    }

    @GetMapping("/login")
    public String showLoginForm(@RequestParam(required = false) String error) {
        return "new-login";
    }

    @GetMapping("/activate")
    public String showAccountActivationForm(@RequestParam(name = "code") String activationCode, Model model) {
        model.addAttribute("activationCode",activationCode);
        return "activate-user";
    }

    @GetMapping("/token")
    public String generateActivationToken(@RequestParam("userName") String encodedUserName) throws Exception{
        String userName = URLDecoder.decode(encodedUserName, StandardCharsets.UTF_8);
        userManagementService.resendActivationCode(userName);
        return "redirect:/register/success";
    }

    @PostMapping("/activate")
    public String activateUser(@ModelAttribute UserDTO userDTO) {
        boolean isValidUser = userManagementService.validateUserDetailsForActivation(userDTO);
        return isValidUser?"redirect:/activate/success":"error";
    }
    @GetMapping("/activate/success")
    public String getAccountActivationSuccessPage() {
        return "account-activation-success";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute UserDTO userDTO) throws Exception{
        userManagementService.createUser(userDTO);
        return "redirect:/registration-success";
    }

    @GetMapping("/register/success")
    public String showSuccessPage() {
        return "registration-success";
    }
}
