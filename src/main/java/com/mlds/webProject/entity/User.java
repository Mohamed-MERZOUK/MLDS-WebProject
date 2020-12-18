package com.mlds.webProject.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
public class User {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String username;
    private String password;
    private String type;
    private String email;
    private String phone;
    private Date birthday;
    private String sexe;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getSexe() {
        return sexe;
    }

    public void setSexe(String sexe) {
        this.sexe = sexe;
    }

    @OneToMany(mappedBy="owner", cascade=CascadeType.ALL ,fetch = FetchType.EAGER)
    @JsonIgnore
    private List<Event> events =  new ArrayList<Event>();

    @OneToMany(mappedBy="interested",cascade=CascadeType.ALL)
    @JsonIgnore
    private List<Interest> intrests =  new ArrayList<Interest>();
    
    @OneToMany(mappedBy="participent",cascade=CascadeType.ALL)
    @JsonIgnore
    private List<Participation> participations =  new ArrayList<Participation>();


    public User(){ }

    public List<Interest> getIntrests() {
        return intrests;
    }

    public void setIntrests(List<Interest> intrests) {
        this.intrests = intrests;
    }

    public List<Participation> getParticipations() {
        return participations;
    }

    public void setParticipations(List<Participation> participations) {
        this.participations = participations;
    }

    public List<Event> getEvents() {
        return events;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }







    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String name) {
        this.username = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User other = (User) o;
        if (!username.equals(other.getUsername())) return false;

        if (id != other.getId()) return false;
        return true;
    }
    @Override
    public int hashCode() {
        int result = username.hashCode();
        result = 31 * result + type.hashCode();
        result = 31 * result + password.hashCode();

        return result;
    }
}
