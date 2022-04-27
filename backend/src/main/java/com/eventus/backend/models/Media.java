package com.eventus.backend.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;

import java.time.LocalDate;
import java.util.Objects;

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
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    @CreationTimestamp
    private LocalDate uploadDate;

    @ManyToOne()
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User owner;
    
    @ManyToOne()
    @JoinColumn(name = "event_id")
    @JsonIgnore
    private Event event;
    
    @ManyToOne()
    @JoinColumn(name = "location_id")
    @JsonIgnore
    private Location location;
    
    @ManyToOne()
    @JoinColumn(name = "sponsorship_id")
    @JsonIgnore
    private Sponsorship sponsorship;

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

	public Sponsorship getSponsorship() {
		return sponsorship;
	}

	public void setSponsorship(Sponsorship sponsorship) {
		this.sponsorship = sponsorship;
	}

	@Override
	public int hashCode() {
		return (int)(id * 31);
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
		return Objects.equals(id, other.id);
	}

	@Override
	public String toString() {
		return "Image [id=" + id + ", path=" + path + ", title=" + title
				+ ", uploadDate=" + uploadDate + ", owner=" + owner + "]";
	}
	
	
}

