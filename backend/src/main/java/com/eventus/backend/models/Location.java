package com.eventus.backend.models;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

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

    @Embedded
    private Coordinates coordinates;

    @Column
    @JsonProperty("price")
    @NotNull(message = "Price shouldn't be null. Set it to 0.")
    private Double price;

    // @OneToMany
    // private Media media;

    @Column
    @JsonProperty("name")
    @NotBlank
    private String name;

    @Column
    @JsonProperty("description")
    @NotBlank
    private String description; 

    @OneToMany(mappedBy = "location", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private Set<Hosting> hostings = new HashSet<>();

    @OneToMany(mappedBy = "location")
    @JsonIgnore
    private Set<Media> media = new HashSet<>();

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

    public Set<Media> getMedia() {
		return media;
	}

	public void setMedia(Set<Media> media) {
		this.media = media;
	}

	@JsonProperty("owner")
    public User getOwner() {
        return owner;
    }

    @JsonIgnore
    public void setOwner(User owner) {
        this.owner = owner;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
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
        Location location = (Location) o;
        return Objects.equals(id, location.id) && Objects.equals(coordinates, location.coordinates) && Objects.equals(price, location.price) && Objects.equals(name, location.name) && Objects.equals(description, location.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, coordinates, price, name, description);
    }

    @Override
    public String toString() {
        return "Location{" +
                "id=" + id +
                ", coordinates=" + coordinates +
                ", price=" + price +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
