package com.project.officequiz.service;

import com.project.officequiz.dao.UserManagementRepository;
import com.project.officequiz.dto.UserDTO;
import com.project.officequiz.entity.Authority;
import com.project.officequiz.entity.User;
import com.project.officequiz.security.usermanagement.SecurityUser;
import com.project.officequiz.utils.ConversionUtil;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.Set;

@Service
public class UserManagementService implements UserDetailsService {
    private final UserManagementRepository userManagementRepository;

    private final PasswordEncoder passwordEncoder;

    public UserManagementService(UserManagementRepository userManagementRepository,PasswordEncoder passwordEncoder) {
        this.userManagementRepository = userManagementRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> fetchedUser = userManagementRepository.findUserByUserName(username);
        return fetchedUser.map(SecurityUser::new).orElseThrow(() -> new UsernameNotFoundException("User with username::"+username+" not found."));
    }

    public void createUser(UserDTO userDTO) {
        String encodedPassword = passwordEncoder.encode(userDTO.password());
        User user = ConversionUtil.convertUserDTOToUser(userDTO, encodedPassword);
        Authority authority = new Authority();
        authority.setName("ROLE_USER");
        user.setAuthorities(Set.of(authority));
        authority.setUsers(Set.of(user));
        userManagementRepository.save(user);
    }
}
