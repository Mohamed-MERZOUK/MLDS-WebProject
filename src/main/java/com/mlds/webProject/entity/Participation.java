package com.mlds.webProject.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;

import java.util.Date;
import javax.persistence.*;

@Entity
public class Participation {
    private long id;
    private User participent;
    private Event event;
    private Date date;

    public Participation(){}

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @ManyToOne
    @JsonBackReference
    public User getParticipent() {
        return participent;
    }

    public void setParticipent(User participent) {
        this.participent = participent;
    }

    @ManyToOne
    @JsonBackReference
    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }
}
