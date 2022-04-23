package com.eventus.backend.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
public class Media {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonProperty("id")
    private Long id;
    
    @Column
    @JsonProperty("path")
    private String path;

    @Column
    @JsonProperty("title")
    private String title;

    @Column
    @JsonProperty("description")
    private String description;

    @Column
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    @CreationTimestamp
    @JsonIgnore
    private LocalDate uploadDate;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User owner;
    
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "event_id")
    @JsonIgnore
    private Event event;
    
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "location_id")
    @JsonIgnore
    private Location location;
    
    @OneToMany(mappedBy = "media", cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<Sponsorship> sponsorship = new HashSet<>();

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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public LocalDate getUploadDate() {
		return uploadDate;
	}

	public void setUploadDate(LocalDate uploadDate) {
		this.uploadDate = uploadDate;
	}

	public User getOwner() {
		return owner;
	}

	public void setOwner(User owner) {
		this.owner = owner;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
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

	public Set<Sponsorship> getSponsorship() {
		return sponsorship;
	}

	public void setSponsorship(Set<Sponsorship> sponsorship) {
		this.sponsorship = sponsorship;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Objects.hash(description, id, path, title, uploadDate, owner);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Media other = (Media) obj;
		return Objects.equals(description, other.description)
				&& Objects.equals(id, other.id) && Objects.equals(path, other.path)
				&& Objects.equals(title, other.title) && Objects.equals(uploadDate, other.uploadDate)
				&& Objects.equals(owner, other.owner);
	}

	@Override
	public String toString() {
		return "Image [id=" + id + ", path=" + path + ", title=" + title
				+ ", description=" + description + ", uploadDate=" + uploadDate + ", owner=" + owner + "]";
	}
	
	
}

