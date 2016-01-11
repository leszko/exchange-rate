package com.summercoding.zooplus.register;

import com.summercoding.zooplus.model.Account;
import com.summercoding.zooplus.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
class RegisterService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public void registerUser(RegisterForm registerForm) {
        Account account = new Account();
        account.setName(registerForm.getName());
        account.setPassword(bCryptPasswordEncoder.encode(registerForm.getPassword()));
        account.setEmail(registerForm.getEmail());
        account.setBirthDate(registerForm.getBirthDate());
        account.setCity(registerForm.getCity());
        account.setStreet(registerForm.getStreet());
        account.setCountry(registerForm.getCountry());
        account.setZipCode(registerForm.getZipCode());

        accountRepository.save(account);
    }
}
