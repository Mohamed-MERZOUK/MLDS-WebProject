package com.mlds.webProject.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class User {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;



    private String name;


    private String password;



    private String type;



    @OneToMany(mappedBy="owner", cascade={CascadeType.PERSIST,CascadeType.REMOVE} ,fetch = FetchType.EAGER)
    @JsonIgnore
    private List<Event> events =  new ArrayList<Event>();



    @OneToMany
    private List<Interest> intrests =  new ArrayList<Interest>();


    @OneToMany
    private List<Participation> participations =  new ArrayList<Participation>();




    public User(){


    }

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
        if (!name.equals(other.getName())) return false;

        if (id != other.getId()) return false;
        return true;
    }
    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + type.hashCode();
        result = 31 * result + password.hashCode();

        return result;
    }
}
