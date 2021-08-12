package com.hanium.safetyvest.web.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SignupRequestDto {
    private String name;
    private String username;
    private String password;
    private String email;
    private String phonenumber;
    private boolean admin = false;
    private String adminToken = "";
}
