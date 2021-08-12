package com.hanium.safetyvest.service.user;

import com.hanium.safetyvest.domain.user.Role;
import com.hanium.safetyvest.domain.user.User;
import com.hanium.safetyvest.domain.user.UserRepository;
import com.hanium.safetyvest.web.dto.SignupRequestDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private static final String ADMIN_TOKEN = "AAABnv/xRVklrnYxKZ0aHgTBcXukeZygoC";

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void registerUser(SignupRequestDto requestDto) {
        String name = requestDto.getName();
        String username = requestDto.getUsername();
        // 회원 ID 중복 확인
        Optional<User> found = userRepository.findByUsername(username);
        if (found.isPresent()) {
            throw new IllegalArgumentException("중복된 사용자 ID가 존재합니다.");
        }
        String password = requestDto.getPassword();
        String email = requestDto.getEmail();
        String phonenumber = requestDto.getPhonenumber();
        // 사용자 ROLE 확인
        Role role = Role.USER;
        // 관리자로 가입하고자 하는 경우
        if (requestDto.isAdmin()) {
            if (!requestDto.getAdminToken().equals(ADMIN_TOKEN)) {
                throw new IllegalArgumentException("관리자 암호가 틀려 등록이 불가능합니다.");
            }
            role = Role.ADMIN;
        }

        User user = User.builder()
                .name(name)
                .username(username)
                .password(password)
                .email(email)
                .phonenumber(phonenumber)
                .role(role)
                .build();
        userRepository.save(user);
    }
}
