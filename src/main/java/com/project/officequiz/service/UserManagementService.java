package com.project.officequiz.service;

import com.project.officequiz.dao.UserManagementRepository;
import com.project.officequiz.dto.UserDTO;
import com.project.officequiz.entity.Authority;
import com.project.officequiz.entity.User;
import com.project.officequiz.exception.InvalidUserDetailsException;
import com.project.officequiz.security.usermanagement.SecurityUser;
import com.project.officequiz.utils.ConversionUtil;
import com.project.officequiz.utils.CredentialsValidator;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Service
public class UserManagementService implements UserDetailsService {
    private final UserManagementRepository userManagementRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;

    public UserManagementService(UserManagementRepository userManagementRepository, PasswordEncoder passwordEncoder, EmailService emailService) {
        this.userManagementRepository = userManagementRepository;
        this.passwordEncoder = passwordEncoder;
        this.emailService = emailService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> fetchedUser = userManagementRepository.findUserByUserName(username);
        return fetchedUser.map(SecurityUser::new).orElseThrow(() -> new UsernameNotFoundException("User with username::"+username+" not found."));
    }

    public void createUser(UserDTO userDTO) throws Exception {

        if(!CredentialsValidator.validateEmail(userDTO.email()))
            throw new InvalidUserDetailsException(HttpStatus.BAD_REQUEST,"Email Address is not valid");
        if(!CredentialsValidator.validatePassword(userDTO.password()))
            throw new Exception("Password should have at least 8 Characters and should include one Uppercase,one Numeric and one Special Character");
        if(isUserExists(userDTO.userName(),userDTO.email()))
            throw new InvalidUserDetailsException(HttpStatus.CONFLICT,"User already exists with same user name or email id");

        String encodedPassword = passwordEncoder.encode(userDTO.password());
        User user = ConversionUtil.convertUserDTOToUser(userDTO, encodedPassword);
        Authority authority = new Authority();
        authority.setName("ROLE_USER");
        user.setAuthorities(Set.of(authority));
        authority.setUsers(Set.of(user));
        // Generating Activation token
        populateTokenDetails(user);
        userManagementRepository.save(user);
        // sending email
        emailService.sendEmailUsingTemplate(user.getUserName(),user.getEmail(),user.getActivationToken());
    }

    public boolean isUserExists(String userName, String email) {
        List<User> users = userManagementRepository.isValidUser(userName, email);
        return users!=null && !users.isEmpty();
    }

    private void populateTokenDetails(User user) {
        user.setActivationToken(UUID.randomUUID().toString());
        // since needs to check only for token expiration and not parsing. Storing in form of miliseconds works
        user.setTokenExpiryTime(System.currentTimeMillis()+10*60*1000);
    }

    public boolean validateUserDetailsForActivation(UserDTO userDTO) {
        User user = userManagementRepository.findUserByUserName(userDTO.userName()).orElseThrow(() -> new InvalidUserDetailsException(HttpStatus.UNAUTHORIZED,"Invalid User Name or Password"));
        if (!passwordEncoder.matches(userDTO.password(), user.getPassword()))
            throw new InvalidUserDetailsException(HttpStatus.UNAUTHORIZED,"Invalid User Name or Password");

        if(user.isActive())
            throw new InvalidUserDetailsException(HttpStatus.CONFLICT,"Account is Already Activated. Please proceed with login");

        if(user.getActivationToken().equals(userDTO.activationCode()) && user.getTokenExpiryTime()>System.currentTimeMillis()) {
            user.setActive(true);
            user.setTokenExpiryTime(null);
            user.setActivationToken(null);
            userManagementRepository.save(user);
            return true;
        }
        return false;
    }
}
