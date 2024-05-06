package com.santechture.api.service;

import com.santechture.api.dto.GeneralResponse;
import com.santechture.api.dto.user.UserDto;
import com.santechture.api.entity.User;
import com.santechture.api.exception.BusinessExceptions;
import com.santechture.api.repository.UserRepository;
import com.santechture.api.validation.AddUserRequest;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Service;

import java.net.http.HttpResponse;

@RequiredArgsConstructor
@Service
public class UserService {


    private final UserRepository userRepository;

    public ResponseEntity<GeneralResponse> list(Pageable pageable){
        return new GeneralResponse().response(userRepository.findAll(pageable));
    }

    public ResponseEntity<GeneralResponse> addNewUser(AddUserRequest userRequest) throws BusinessExceptions {
        if(userRepository.existsByUsernameIgnoreCase(userRequest.getUsername())){
            throw new BusinessExceptions("username.exist");
        }
        if (userRepository.existsByEmailIgnoreCase(userRequest.getEmail())) {
            throw new BusinessExceptions("email.exist");
        }
        User user = new User(userRequest.getUsername(),userRequest.getEmail());
        userRepository.save(user);
        // Clear the security context and log out the current user
        SecurityContextHolder.clearContext();

        return new GeneralResponse().response(new UserDto(user));
    }

}
