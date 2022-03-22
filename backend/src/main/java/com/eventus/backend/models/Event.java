package com.eventus.backend.models;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import javax.persistence.*;
import javax.validation.constraints.Future;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.format.annotation.DateTimeFormat;


@Entity
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonProperty("id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User organizer;

    @Column
    @JsonProperty("title")
    @Size(max=20)
    @NotBlank
    private String title;

    @Column
    @JsonProperty("price")
    @Min(value=0)
    private Double price;

    @Column
    @JsonProperty("startDate")
    @DateTimeFormat(pattern = "YYYY-MM-DDTHH:MM:SS")
    @Future
    private LocalDateTime startDate;

    @Column
    @JsonProperty("endDate")
    @Future
    @DateTimeFormat(pattern = "YYYY-MM-DDTHH:MM:SS")
    private LocalDateTime endDate;

    @Column
    @JsonProperty("description")
    @NotBlank
    @Size(max=120)
    private String description;

    @Embedded
    private Coordinates coordinates;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "event_id")
    @JsonProperty("images")
    private List<Image> images;

    @OneToMany(mappedBy = "event")
    @JsonIgnore
    private Set<Participation> participations = new HashSet<>();

    @OneToMany(mappedBy = "event")
    @JsonIgnore
    private Set<Sponsorship> sponsors = new HashSet<>();

    @OneToMany(mappedBy = "event")
    @JsonIgnore
    private Set<Hosting> hostings = new HashSet<>();

    public Event(){

    }

    public Event(Long id, String title, Double price, String description) {
        this.id = id;
        this.title = title;
        this.price = price;
        this.description = description;
    }

    public Set<Sponsorship> getSponsors() {
        return sponsors;
    }

    public void setSponsors(Set<Sponsorship> sponsors) {
        this.sponsors = sponsors;
    }

    

    public Set<Hosting> getHostings() {
        return hostings;
    }

    public void setHostings(Set<Hosting> hostings) {
        this.hostings = hostings;
    }

    public Set<Participation> getParticipations() {
        return participations;
    }

    public void setParticipations(Set<Participation> participations) {
        this.participations = participations;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @JsonProperty("organizer")
    public User getOrganizer() {
        return organizer;
    }
    @JsonIgnore
    public void setOrganizer(User organizer) {
        this.organizer = organizer;
    }

    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    @Override
    public String toString() {
        return "Event{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", price=" + price +
                ", description='" + description + '\'' +
                ", images=" + images +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Event event = (Event) o;
        return Objects.equals(id, event.id) && Objects.equals(organizer, event.organizer) && Objects.equals(title, event.title) && Objects.equals(price, event.price) && Objects.equals(description, event.description) && Objects.equals(images, event.images);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, organizer, title, price, description, images);
    }
}
