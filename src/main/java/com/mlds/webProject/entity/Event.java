package com.mlds.webProject.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Date;
import javax.persistence.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity
public class Event {
    private long id;
    private String title;
    private String description;
    private String detail;
    @JsonFormat(pattern = "dd-MM-yyyy")
    private Date date;
    private List<Participation> participents = new ArrayList<Participation>();
    private List<Interest> intrested = new ArrayList<Interest>();
    private User owner;
    private String photo;

    public Event() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Lob
    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Lob
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

    @Transient
    public String getOwnerName(){
        if(owner==null) return null;
        return owner.getUsername();
    }

    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL)
    @JsonIgnore
    public List<Participation> getParticipents() {
        return participents;
    }

    public void setParticipents(List<Participation> participents) {
        this.participents = participents;
    }

    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL)
    @JsonIgnore
    public List<Interest> getIntrested() {
        return intrested;
    }

    public void setIntrested(List<Interest> intrested) {
        this.intrested = intrested;
    }

    //Return number of participents/interested without save it in event table
    @Transient
    public int getNbParticipents() {
        return this.getParticipents().size();
    }

    @Transient
    public int getNbInterested() {
        return this.getIntrested().size();
    }

    @ManyToOne
    @JsonIgnore
    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    @JsonIgnore
    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    //Return the event image path
    @Transient
    public String getPhotosImagePath() {
        if (photo == null ) return null;
        if (owner == null ) return null;
        return "/user-photos/" + owner.getId() + "/"  + id +  "/" + photo;
    }

    //Return the owner photo path
    @Transient
    public String getOwnerPhotosPath() {
        if (owner == null ) return null;
        return owner.getPhotosImagePath();
    }

    //To delete participations and interests
    public void dismissParticipation(Participation p){
        this.getParticipents().remove(p);
    }

    public void dismissInterest(Interest i){
        this.getIntrested().remove(i);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Event other = (Event) o;
        if (!description.equals(other.getDescription())) return false;

        if (id != other.getId()) return false;
        return true;
    }

    @Override
    public int hashCode() {
        int result = description.hashCode();
        result = 31 * result + title.hashCode();


        return result;
    }

}
