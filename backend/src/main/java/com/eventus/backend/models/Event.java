package com.eventus.backend.models;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

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
    @Size(max = 25)
    @NotBlank
    private String title;

    @Column
    @JsonProperty("price")
    @Min(value = 0)
    private Double price;

    @Column
    @JsonProperty("prize")
    private Double prize;

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
    @Size(max = 120)
    private String description;

    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Media> media = new HashSet<>();

    @OneToMany(mappedBy = "event")
    @JsonIgnore
    private Set<Participation> participations = new HashSet<>();

    @OneToMany(mappedBy = "event")
    @JsonIgnore
    private Set<Sponsorship> sponsors = new HashSet<>();

    @OneToMany(mappedBy = "event")
    @JsonIgnore
    private Set<Hosting> hostings = new HashSet<>();

    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private Set<EventTag> eventTags = new HashSet<>();

    public Event() {


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

    @JsonProperty("tags")
    public Set<Tag> getTags() {
        return eventTags.stream()
                .map(eventTag -> {
                    Tag tag = eventTag.getTag();

                    tag.setId(eventTag.getId());

                    return tag;
                })
                .collect(Collectors.toSet());
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

    @JsonProperty("media")
    public Set<Media> getMedia() {
        return media;
    }

    @JsonIgnore
    public void setMedia(Set<Media> media) {
        this.media = media;
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

    public Double getPrize() {
        return prize;
    }

    public void setPrize(Double prize) {
        this.prize = prize;
    }

    @JsonProperty("rating")
    public Double getRating() {
        int numPart = participations.size();
        int numSpons = sponsors.size();
        double totalParticipaciones = 0;
        if (numPart != 0) {
            totalParticipaciones = participations.stream().mapToDouble(Participation::getPrice).sum();
        }
        double totalSponsors = 0;
        if (numSpons != 0) {
            totalSponsors = sponsors.stream().mapToDouble(Sponsorship::getQuantity).sum();
        }

        return numPart * 0.4 + numSpons * 0.3 + (totalParticipaciones + totalSponsors) * 0.3;
    }

    @JsonProperty("coordinates")
    public Coordinates getEventCoordinates() {
        Hosting hosting = null;
        Coordinates coordinates = null;
        if (!hostings.isEmpty()) {
            hosting = hostings.stream().filter(x -> x.isAccepted() != null && x.isAccepted()).findFirst().orElse(null);
        }
        if (hosting != null) {
            coordinates = hosting.getLocation().getCoordinates();
        }
        return coordinates;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Event event = (Event) o;
        return Objects.equals(id, event.id) && Objects.equals(organizer, event.organizer)
                && Objects.equals(title, event.title) && Objects.equals(price, event.price)
                && Objects.equals(prize, event.prize) && Objects.equals(startDate, event.startDate)
                && Objects.equals(endDate, event.endDate) && Objects.equals(description, event.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, organizer, title, price, prize, startDate, endDate, description);
    }

    @Override
    public String toString() {
        return "Event{" +
                "id=" + id +
                ", organizer=" + organizer +
                ", title='" + title + '\'' +
                ", price=" + price +
                ", prize=" + prize +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", description='" + description + '\'' +
                '}';
    }
}
