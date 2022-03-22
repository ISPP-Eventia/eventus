package com.eventus.backend.models;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;



@Entity
public class Location {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonProperty("id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    @JsonIgnore
    private User owner;

    @Column
    @JsonProperty("location")
    private String location;

    @Column
    @JsonProperty("price")
    private Double price;

    // @OneToMany
    // private Media media;

    @Column
    @JsonProperty("name")
    private String name;

    @Column
    @JsonProperty("description")
    private String description; 

    @OneToMany(mappedBy = "location", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private Set<Hosting> hostings = new HashSet<>();



    public Set<Hosting> getHostings() {
        return hostings;
    }

    public void setHostings(Set<Hosting> hostings) {
        this.hostings = hostings;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Location location1 = (Location) o;
        return Objects.equals(id, location1.id) && Objects.equals(location, location1.location) && Objects.equals(price, location1.price) && Objects.equals(name, location1.name) && Objects.equals(description, location1.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, location, price, name, description);
    }

    @Override
    public String toString() {
        return "Location{" +
                "id=" + id +
                ", location='" + location + '\'' +
                ", price=" + price +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
