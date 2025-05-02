package com.project.officequiz.controller;

import com.project.officequiz.dto.UserDTO;
import com.project.officequiz.service.UserManagementService;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

@Controller
@SessionAttributes("logoutSuccess")
public class UserManagementController {

    private final UserManagementService userManagementService;

    public UserManagementController(UserManagementService userManagementService) {
        this.userManagementService = userManagementService;
    }

    @GetMapping("/login")
    public String showLoginForm(Model model, @RequestParam(required = false) String error, SessionStatus sessionStatus) {

        Boolean logoutSuccess = (Boolean) model.getAttribute("logoutSuccess");

        if (Boolean.TRUE.equals(logoutSuccess))
            sessionStatus.setComplete();

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if ((!(auth instanceof AnonymousAuthenticationToken) && auth.isAuthenticated()))
            return "landing-page";


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

    @ModelAttribute("logoutSuccess")
    public Boolean logoutSuccess() {
        return false;
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
