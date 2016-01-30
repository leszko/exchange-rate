package com.summercoding.zooplus.register;

import com.summercoding.zooplus.model.User;
import com.summercoding.zooplus.repository.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static com.google.common.truth.Truth.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class RegisterServiceTest {
    private static final String PASSWORD = "password";
    private static final String ENCRYPTED = "encrypted";

    @Mock
    private UserRepository userRepository;

    @Mock
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @InjectMocks
    private RegisterService registerService;

    @Test
    public void Should_RegisterAccountWithEncryptedPassword() {
        // given
        given(bCryptPasswordEncoder.encode(PASSWORD)).willReturn(ENCRYPTED);

        RegisterDto registerDto = new RegisterDto();
        registerDto.setPassword(PASSWORD);

        // when
        registerService.registerUser(registerDto);

        // then
        ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(captor.capture());
        assertThat(captor.getValue().getPassword()).isEqualTo(ENCRYPTED);
    }
}