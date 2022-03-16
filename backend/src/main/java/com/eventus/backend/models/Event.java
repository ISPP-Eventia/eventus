package com.eventus.backend.models;

import java.util.List;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonProperty;


@Entity
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonProperty("id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonProperty("organizer")
    private User organizer;

    @Column
    @JsonProperty("title")
    @NotBlank
    private String title;

    @Column
    @JsonProperty("price")
    private Double price;

    @Column
    @JsonProperty("description")
    @NotBlank
    private String description;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "event_id")
    private List<Image> images;

    public Event(){

    }

    public Event(Long id, String title, Double price, String description) {
        this.id = id;
        this.title = title;
        this.price = price;
        this.description = description;
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

    public User getOrganizer() {
        return organizer;
    }

    public void setOrganizer(User organizer) {
        this.organizer = organizer;
    }

    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }

    @Override
    public String toString() {
        return "Event{" +
                "id=" + id +
                ", organizer=" + organizer +
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
