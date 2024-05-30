package com.example.security.service.impl;

import com.example.model.Role;
import com.example.model.User;
import com.example.repository.UserRepository;
import com.example.security.service.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author bodyzxy
 * @github https://github.com/bodyzxy
 * @date 2024/5/26 22:02
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            return (UserDetails) new UsernameNotFoundException("User not found username" + username);
        } else {
            Set<Role> roles = user.getRoles();
            Set<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();
            for(Role role: roles){
                authorities.add(new SimpleGrantedAuthority(role.getRole()));
            }
            UserDetailsImpl customUserDetail=new UserDetailsImpl();
            customUserDetail.setUsername(username);
            customUserDetail.setPassword(user.getPassword());
            customUserDetail.setAuthorities(authorities);

            return customUserDetail;
        }
    }
}
