package com.summercoding.zooplus.model;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@ToString(of = {"id", "currency", "date"})
@Data
@Entity
public class HistoryElement {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private String currency;

    @Column
    private String date;

    @Column
    private BigDecimal exchangeRate;

    @Column(name = "created_date")
    private Date createdDate = new Date();

    @ManyToOne
    @JoinColumn(name = "account")
    private Account account;
}
