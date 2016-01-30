package com.summercoding.zooplus.register;

import com.summercoding.zooplus.model.User;
import com.summercoding.zooplus.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Registers new user.
 */
@Slf4j
@Service
class RegisterService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public void registerUser(RegisterDto registerDto) {
        User user = new User();
        user.setName(registerDto.getName());
        user.setPassword(bCryptPasswordEncoder.encode(registerDto.getPassword()));
        user.setEmail(registerDto.getEmail());
        user.setBirthDate(registerDto.getBirthDate());
        user.setCity(registerDto.getCity());
        user.setStreet(registerDto.getStreet());
        user.setCountry(registerDto.getCountry());
        user.setZipCode(registerDto.getZipCode());

        log.info("Registering user: {}", user);

        userRepository.save(user);
    }
}
