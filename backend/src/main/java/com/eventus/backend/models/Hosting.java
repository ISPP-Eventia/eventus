package com.eventus.backend.models;

import javax.persistence.Entity;
import javax.persistence.FetchType;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
public class Hosting {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonProperty("id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "event_id")
    @JsonIgnore
    private Event event;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "location_id")
    @JsonIgnore
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

    @JsonIgnore
    public boolean isAccepted() {
        return isAccepted;
    }

    public void setAccepted(boolean isAccepted) {
        this.isAccepted = isAccepted;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Hosting hosting = (Hosting) o;
        return isAccepted == hosting.isAccepted && Objects.equals(id, hosting.id) && Objects.equals(price, hosting.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, price, isAccepted);
    }

    @Override
    public String toString() {
        return "Hosting{" +
                "id=" + id +
                ", price=" + price +
                ", isAccepted=" + isAccepted +
                '}';
    }
}
