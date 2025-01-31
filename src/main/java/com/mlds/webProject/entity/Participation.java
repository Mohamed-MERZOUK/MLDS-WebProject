package com.mlds.webProject.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Date;
import javax.persistence.*;

@Entity
public class Participation {
    private long id;
    private User participent;
    private Event event;
    @JsonFormat(pattern = "dd-MM-yyyy")
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
    @JsonIgnore
    public User getParticipent() {
        return participent;
    }

    public void setParticipent(User participent) {
        this.participent = participent;
    }

    @ManyToOne
    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    //To remove participation
    public void dismissParticipent(){
        this.participent.dismissParticipation(this);
        this.participent=null;

    }
    public void dismissEvent(){
        this.event.dismissParticipation(this);
        this.event=null;
    }
}
