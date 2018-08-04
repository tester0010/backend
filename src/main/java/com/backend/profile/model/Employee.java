package com.backend.profile.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Data
@Entity
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String name;
    @JsonFormat(pattern = "MM/dd/yyyy")
    private Date joindate;
    private int age;
    private String company;
    private String email;
    private String phone;
    private BigDecimal salary;
    @ManyToOne @Cascade(value = org.hibernate.annotations.CascadeType.ALL)
    private Address address;
}
