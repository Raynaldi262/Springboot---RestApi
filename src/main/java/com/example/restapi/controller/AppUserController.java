package com.example.restapi.controller;

import com.example.restapi.dto.AppUserData;
import com.example.restapi.dto.ResponseData;
import com.example.restapi.model.entity.AppUser;
import com.example.restapi.service.AppUserService;
import io.swagger.models.Response;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class AppUserController {

    @Autowired
    private AppUserService appUserService;

    @Autowired
    private ModelMapper modelMapper;

    @PostMapping("/register")
    public ResponseEntity<ResponseData<AppUser>> register(@RequestBody AppUserData userData){

        ResponseData<AppUser> response = new ResponseData<>();
        AppUser appUser = modelMapper.map(userData, AppUser.class);

        response.setPayload(appUserService.regisUser(appUser));
        response.setStatus(true);
        response.getMessage().add("AppUser saved");
        return ResponseEntity.ok(response);
    }
}
