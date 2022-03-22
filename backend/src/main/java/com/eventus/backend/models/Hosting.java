package com.eventus.backend.models;

import javax.persistence.Entity;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
public class Hosting {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonProperty("id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "event_id")
    @JsonProperty("event")
    private Event event;

    @ManyToOne
    @JoinColumn(name = "location_id")
    @JsonProperty("location")
    private Location location;

    @Column
    @JsonProperty("price")
    private Double price;

    @Column
    @JsonProperty("isAccepted")
    private boolean isAccepted;

    

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public boolean isAccepted() {
        return isAccepted;
    }

    public void setAccepted(boolean isAccepted) {
        this.isAccepted = isAccepted;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((event == null) ? 0 : event.hashCode());
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + (isAccepted ? 1231 : 1237);
        result = prime * result + ((location == null) ? 0 : location.hashCode());
        result = prime * result + ((price == null) ? 0 : price.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Hosting that = (Hosting) o;
        return Objects.equals(id, that.id) && Objects.equals(event, that.event) && Objects.equals(location, that.location) && Objects.equals(price, that.price) && Objects.equals(isAccepted, that.isAccepted);
    }

    @Override
    public String toString() {
        return "Hosting [event=" + event + ", id=" + id + ", isAccepted=" + isAccepted + ", location=" + location
                + ", price=" + price + "]";
    }

    
}
