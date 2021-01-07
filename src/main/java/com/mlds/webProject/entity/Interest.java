package com.mlds.webProject.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.*;

@Entity
public class Interest {
    private long id;
    private User interested;
    private Event event;
    @JsonFormat(pattern = "dd-MM-yyyy")
    private Date date;


    public Interest(){}

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
    public User getInterested() {
        return interested;
    }

    public void setInterested(User interested) {
        this.interested = interested;
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

    //To remove interest
    public void dismissInterested(){
       this.interested.dismissIntrest(this);
        this.interested=null;
    }
    public void dismissEvent(){
        this.event.dismissInterest(this);
        this.event=null;
    }
}
