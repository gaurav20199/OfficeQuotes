package com.project.officequiz.service;

import com.project.officequiz.dao.UserManagementRepository;
import com.project.officequiz.dto.UserDTO;
import com.project.officequiz.entity.ActivationToken;
import com.project.officequiz.entity.Authority;
import com.project.officequiz.entity.User;
import com.project.officequiz.exception.UserAlreadyExistsException;
import com.project.officequiz.security.usermanagement.SecurityUser;
import com.project.officequiz.utils.ConversionUtil;
import com.project.officequiz.utils.CredentialsValidator;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UserManagementService implements UserDetailsService {
    private final UserManagementRepository userManagementRepository;
    private final ActivationTokenService activationTokenService;
    private final PasswordEncoder passwordEncoder;

    public UserManagementService(UserManagementRepository userManagementRepository, ActivationTokenService activationTokenService, PasswordEncoder passwordEncoder) {
        this.userManagementRepository = userManagementRepository;
        this.activationTokenService = activationTokenService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> fetchedUser = userManagementRepository.findUserByUserName(username);
        return fetchedUser.map(SecurityUser::new).orElseThrow(() -> new UsernameNotFoundException("User with username::"+username+" not found."));
    }

    public void createUser(UserDTO userDTO) throws Exception {

        if(!CredentialsValidator.validateEmail(userDTO.email()))
            throw new Exception("Email Address is not valid");
        if(!CredentialsValidator.validatePassword(userDTO.password()))
            throw new Exception("Password should have at least 8 Characters and should include one Uppercase,one Numeric and one Special Character");
        if(isUserExists(userDTO.userName(),userDTO.email()))
            throw new UserAlreadyExistsException("User already exists with same user name or email id");

        String encodedPassword = passwordEncoder.encode(userDTO.password());
        User user = ConversionUtil.convertUserDTOToUser(userDTO, encodedPassword);
        Authority authority = new Authority();
        authority.setName("ROLE_USER");
        user.setAuthorities(Set.of(authority));
        authority.setUsers(Set.of(user));
        userManagementRepository.save(user);
        // Generating Activation token
        ActivationToken token = activationTokenService.createToken(user.getEmail());
        String activationToken = token.getToken();

        // sending email



    }

    public boolean isUserExists(String userName, String email) {
        List<User> users = userManagementRepository.isValidUser(userName, email);
        return users!=null && !users.isEmpty();
    }
}
