package com.example.contestplatform.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.contestplatform.model.Contestant;
import com.example.contestplatform.repository.ContestantRepository;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private ContestantRepository contestantRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Contestant> optionalContestant = contestantRepository.findOneByUsernameIgnoreCase(username);

        if (optionalContestant.isPresent()) {
            Contestant contestant = optionalContestant.get();
            return new User(contestant.getUsername(), contestant.getPassword(), Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")));
        } else {
            log.error("User not found");
            throw new UsernameNotFoundException("User not found");
        }      
    }
}

