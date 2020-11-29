package com.mlds.webProject.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Date;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Event {
    private long id;
    private String title;
    private String description;
    private Date date;
    private List<Participation> participents = new ArrayList<Participation>();
    private List<Interest> intrested = new ArrayList<Interest>();
    private User owner;

    public Event(){}

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

 /*   @OneToMany(mappedBy="", cascade=CascadeType.ALL, fetch = FetchType.EAGER)
    public List<User> getParticipents() {
        return participents;
    }

    public void setParticipents(List<User> participents) {
        this.participents = participents;
    }
*/

    @OneToMany
    public List<Participation> getParticipents() {
        return participents;
    }

    public void setParticipents(List<Participation> participents) {
        this.participents = participents;
    }

    @OneToMany
    public List<Interest> getIntrested() {
        return intrested;
    }

    public void setIntrested(List<Interest> intrested) {
        this.intrested = intrested;
    }

    @ManyToOne
    public User getOwner() {
        return owner;
    }

    public void setOwner(User creater) {
        this.owner = owner;
    }

}
