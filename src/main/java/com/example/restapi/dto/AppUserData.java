package com.example.restapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppUserData {
    private String fullname;
    private String email;
    private String password;
    private String appUserRole;

}
