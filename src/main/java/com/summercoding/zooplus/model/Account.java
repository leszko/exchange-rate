package com.summercoding.zooplus.model;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

@ToString(of = {"id", "name"})
@Data
@Entity
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private String name;

    @Column
    private String password;

    @Column
    private String email;

    @Column
    private Date birthDate;

    @Column
    private String street;

    @Column
    private String zipCode;

    @Column
    private String city;

    @Column
    private String country;

    @OneToMany(mappedBy = "account")
    @OrderBy("created_date DESC")
    private List<HistoryElement> history = new LinkedList<>();
}
