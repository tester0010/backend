package com.backend.profile.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class Profile {
    private String name;
    @JsonFormat(pattern = "MM/dd/yyyy")
    private Date joindate;
    private int age;
    private String company;
    private String email;
    private String phone;
    private BigDecimal salary;
    private Address address;
}
