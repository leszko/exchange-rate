package com.summercoding.zooplus.security;

import com.summercoding.zooplus.model.User;
import com.summercoding.zooplus.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByName(username);
        if (user != null) {
            return new org.springframework.security.core.userdetails.User(user.getName(), user.getPassword(), true, true, true, true, AuthorityUtils.createAuthorityList("USER"));
        } else {
            throw new UsernameNotFoundException("could not find the user '" + username + "'");
        }


    }
}
