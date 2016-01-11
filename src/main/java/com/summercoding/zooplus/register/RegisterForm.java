package com.summercoding.zooplus.register;

import com.summercoding.zooplus.register.validator.Country;
import lombok.Data;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.util.Date;

@Data
class RegisterForm {
    @Size(min = 2, max = 30)
    private String name;

    @Size(min = 2, max = 30)
    private String password;

    @NotEmpty
    @Email
    private String email;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull
    @Past
    private Date birthDate;

    @Size(min = 2, max = 100)
    private String street;

    @Size(min = 2, max = 10)
    private String zipCode;

    @Size(min = 2, max = 100)
    private String city;

    @NotNull
    @Country
    private String country;
}
