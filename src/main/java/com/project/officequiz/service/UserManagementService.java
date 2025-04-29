package com.project.officequiz.service;

import com.project.officequiz.entity.Users;
import com.project.officequiz.repository.UserManagementRepository;
import com.project.officequiz.dto.UserDTO;
import com.project.officequiz.entity.Authority;
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
        Optional<Users> fetchedUser = userManagementRepository.findUserByUserName(username);
        return fetchedUser.map(SecurityUser::new).orElseThrow(() -> new UsernameNotFoundException("User with username::"+username+" not found."));
    }

    public void createUser(UserDTO userDTO) throws Exception {

        if(!CredentialsValidator.validateEmail(userDTO.email()))
            throw new InvalidUserDetailsException(HttpStatus.BAD_REQUEST,"Email Address is not valid");
        if(!CredentialsValidator.validatePassword(userDTO.password()))
            throw new InvalidUserDetailsException(HttpStatus.BAD_REQUEST,"Password should have at least 8 Characters and should include one Uppercase,one Numeric and one Special Character");
        if(!CredentialsValidator.validatePasswordInfo(userDTO.password(), userDTO.confirmedPassword()))
            throw new InvalidUserDetailsException(HttpStatus.BAD_REQUEST,"Password and Confirmed Password should match");

        if(isUserExists(userDTO.userName(),userDTO.email()))
            throw new InvalidUserDetailsException(HttpStatus.CONFLICT,"User already exists with same user name or email id");

        String encodedPassword = passwordEncoder.encode(userDTO.password());
        Users users = ConversionUtil.convertUserDTOToUser(userDTO, encodedPassword);
        Authority authority = new Authority();
        authority.setName("ROLE_USER");
        users.setAuthorities(Set.of(authority));
        authority.setUsers(Set.of(users));
        // Generating Activation token
        populateTokenDetails(users);
        userManagementRepository.save(users);
        // sending email
        emailService.sendEmailUsingTemplate(users.getUserName(), users.getEmail(), users.getActivationToken());
    }

    public void resendActivationCode(String userName) throws Exception {
        Users users = userManagementRepository.findUserByUserName(userName).orElseThrow(() -> new InvalidUserDetailsException(HttpStatus.UNAUTHORIZED, "Invalid Username or Password"));
        if(users.isActive())
            throw new InvalidUserDetailsException(HttpStatus.CONFLICT,"Account is Already Activated. Please proceed with login");

        if(users.getActivationToken()!=null && users.getTokenExpiryTime()>System.currentTimeMillis())
            throw new InvalidUserDetailsException(HttpStatus.CONFLICT,"Please check your email for activation link");

        populateTokenDetails(users);
        userManagementRepository.save(users);
        emailService.sendEmailUsingTemplate(users.getUserName(), users.getEmail(), users.getActivationToken());

    }

    public boolean isUserExists(String userName, String email) {
        List<Users> users = userManagementRepository.isValidUser(userName, email);
        return users!=null && !users.isEmpty();
    }

    private void populateTokenDetails(Users users) {
        users.setActivationToken(UUID.randomUUID().toString());
        // since needs to check only for token expiration and not parsing. Storing in form of miliseconds works
        users.setTokenExpiryTime(System.currentTimeMillis()+10*60*1000);
    }

    public boolean validateUserDetailsForActivation(UserDTO userDTO) {
        Users users = userManagementRepository.findUserByUserName(userDTO.userName()).orElseThrow(() -> new InvalidUserDetailsException(HttpStatus.UNAUTHORIZED,"Invalid User Name or Password"));
        if (!passwordEncoder.matches(userDTO.password(), users.getPassword()))
            throw new InvalidUserDetailsException(HttpStatus.UNAUTHORIZED,"Invalid User Name or Password");

        if(users.isActive())
            throw new InvalidUserDetailsException(HttpStatus.CONFLICT,"Account is Already Activated. Please proceed with login");

        if(users.getActivationToken().equals(userDTO.activationCode()) && users.getTokenExpiryTime()>System.currentTimeMillis()) {
            users.setActive(true);
            users.setTokenExpiryTime(null);
            users.setActivationToken(null);
            userManagementRepository.save(users);
            return true;
        }else {
            throw new InvalidUserDetailsException(HttpStatus.UNAUTHORIZED,"Invalid Authentication Token");
        }
    }
}
