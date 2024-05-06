package com.santechture.api.service;

import com.santechture.api.dto.GeneralResponse;
import com.santechture.api.dto.admin.AdminDto;
import com.santechture.api.entity.Admin;
import com.santechture.api.entity.IRoles;
import com.santechture.api.exception.BusinessExceptions;
import com.santechture.api.repository.AdminRepository;
import com.santechture.api.utils.JwtUtils;
import com.santechture.api.validation.LoginRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class AdminService {
    private final AdminRepository adminRepository;
    private final JwtUtils jwtUtils;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    public ResponseEntity<GeneralResponse> login(LoginRequest request) throws BusinessExceptions {
        Admin admin = adminRepository.findByUsernameIgnoreCase(request.getUsername())
                .orElseThrow(() -> new BusinessExceptions("login.credentials.not.match"));
        if (!bCryptPasswordEncoder.matches(request.getPassword(), admin.getPassword())) {
            throw new BusinessExceptions("login.credentials.not.match");
        }
        return new GeneralResponse().response(adminDto(admin));
    }

    private AdminDto adminDto(Admin admin) {
        String jwt = jwtUtils.generateJwtToken(admin, List.of(IRoles.ROLE_ADMIN, IRoles.ROLE_USER));
        AdminDto adminDto = new AdminDto(admin);
        adminDto.setAccessToken(jwt);
        return adminDto;
    }
}
