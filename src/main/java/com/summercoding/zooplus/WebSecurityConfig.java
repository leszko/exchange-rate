package com.summercoding.zooplus;

import com.summercoding.zooplus.security.UserDetailServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    private static final String MANAGEMENT_ROLE = "MANAGEMENT_ROLE";

    @Value("${management.user.name}")
    private String managementUserName;

    @Value("${management.user.password}")
    private String managementUserPassword;


    @Autowired
    private UserDetailServiceImpl userDetailService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/register").permitAll()
                .antMatchers("/css/**").permitAll()
                .antMatchers("/webjars/bootstrap/**").permitAll()
                .antMatchers("/webjars/jquery/**").permitAll()
                .antMatchers("/management/**").hasRole(MANAGEMENT_ROLE)
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .permitAll()
                .and()
                .logout()
                .permitAll();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser(managementUserName).password(managementUserPassword).roles(MANAGEMENT_ROLE);
        auth.userDetailsService(userDetailService)
                .passwordEncoder(bCryptPasswordEncoder);
    }

}