package com.summercoding.zooplus.register;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@RunWith(MockitoJUnitRunner.class)
public class RegisterControllerTest {

    @Mock
    private RegisterService registerService;

    @InjectMocks
    private RegisterController registerController;

    private MockMvc mockMvc;

    @Before
    public void before() {
        mockMvc = standaloneSetup(registerController).build();
    }

    @Test
    public void Should_ShowRegisterForm_When_Get() throws Exception {
        // when, then
        mockMvc.perform(get("/register"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("countries"))
                .andExpect(view().name("registerAccount"));
    }

    @Test
    public void Should_ShowRegisterForm_When_PostWithInvalidParameters() throws Exception {
        // when, then
        mockMvc.perform(post("/register"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("countries"))
                .andExpect(view().name("registerAccount"));
    }

    @Test
    public void Should_RegisterUserAndRedirect_When_PostWithValidParameters() throws Exception {
        // when, then
        mockMvc.perform(post("/register")
                .param("name", "Alex Szymanski")
                .param("password", "somepassword")
                .param("email", "alex@gmail.com")
                .param("birthDate", "2000-10-10")
                .param("street", "Chemin de Reculet")
                .param("zipCode", "123456")
                .param("city", "Munich")
                .param("country", "Germany"))
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("/"));
        verify(registerService).registerUser(any(RegisterForm.class));
    }
}