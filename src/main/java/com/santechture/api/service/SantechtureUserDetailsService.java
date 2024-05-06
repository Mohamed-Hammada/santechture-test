package com.santechture.api.service;

import com.santechture.api.entity.IRoles;
import com.santechture.api.repository.AdminRepository;
import com.santechture.api.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class SantechtureUserDetailsService implements UserDetailsService {


    private final AdminRepository adminRepository;

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return adminRepository.findByUsernameIgnoreCase(username)
                .map(admin -> new User(admin.getUsername(), admin.getPassword(), List.of(new SimpleGrantedAuthority(IRoles.ROLE_ADMIN.name()))))
                .orElseGet(() -> userRepository.findByUsernameIgnoreCase(username)
                        .map(user -> new User(user.getUsername(), "Default Password", List.of(new SimpleGrantedAuthority(IRoles.ROLE_USER.name()))))
                        .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username)));
    }

}
