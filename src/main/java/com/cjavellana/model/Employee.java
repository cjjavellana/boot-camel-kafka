package com.cjavellana.model;


import com.cjavellana.constants.Constants;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "employee")
public class Employee implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "userid")
    private Integer userid;

    @Column(name = "name")
    private String name;

    @Column(name = "last_name")
    private String lastName;

    @Basic
    @Temporal(TemporalType.DATE)
    @Column(name = "birthdate")
    private Date birthdate;

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Date getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
    }

    public String toCSV() {
        return String.format("\"%s\",\"%s\",\"%s\",\"%s\"\n",
                (userid == null) ? "" : userid,
                (name == null) ? "" : name,
                (lastName == null) ? "" : lastName,
                (birthdate == null) ? "" : Constants.DATE_FORMAT.format(birthdate));
    }
}
