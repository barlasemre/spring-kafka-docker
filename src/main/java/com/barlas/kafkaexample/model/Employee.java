package com.barlas.kafkaexample.model;


import io.swagger.annotations.ApiModel;
import jakarta.persistence.*;

import java.util.Date;
import java.util.List;

@Entity
@ApiModel
public class Employee {
    @Id
    @GeneratedValue
    private Long id;

    @Column(unique=true)
    private String email;

    private String fullName;

    private Date birthday;

    @ElementCollection
    private List<String> hobbies;

    public Employee() {
        super();
    }

    public Employee(Long id, String fullName, Date birthday) {
        super();
        this.id = id;
        this.fullName = fullName;
        this.birthday = birthday;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public List<String> getHobbies() {
        return hobbies;
    }

    public void setHobbies(List<String> hobbies) {
        this.hobbies = hobbies;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", fullName='" + fullName + '\'' +
                ", birthday=" + birthday +
                ", hobbies=" + hobbies +
                '}';
    }
}
