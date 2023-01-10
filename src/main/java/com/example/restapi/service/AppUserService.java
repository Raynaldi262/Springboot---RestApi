package com.example.restapi.service;

import com.example.restapi.model.entity.AppUser;
import com.example.restapi.model.repository.AppUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AppUserService implements UserDetailsService {

    @Autowired
    private AppUserRepository appUserRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return appUserRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    public AppUser regisUser(AppUser user){
        boolean userExist = appUserRepository.findByEmail(user.getEmail()).isPresent();
        if(userExist){
            throw new RuntimeException("User already Existt");
        }

        String encodePass = bCryptPasswordEncoder.encode(user.getPassword());
        user.setPassword(encodePass);
        return appUserRepository.save(user);

    }
}
