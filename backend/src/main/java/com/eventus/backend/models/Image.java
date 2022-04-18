package com.eventus.backend.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Objects;

@Entity
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonProperty("id")
    private Long id;
    
    @Lob
    byte[] content;

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

    @OneToOne(orphanRemoval = true, mappedBy = "image")
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User uploadedBy;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public byte[] getContent() {
		return content;
	}

	public void setContent(byte[] content) {
		this.content = content;
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

	public User getUploadedBy() {
		return uploadedBy;
	}

	public void setUploadedBy(User uploadedBy) {
		this.uploadedBy = uploadedBy;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(content);
		result = prime * result + Objects.hash(description, id, title, uploadDate, uploadedBy);
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
		Image other = (Image) obj;
		return Arrays.equals(content, other.content) && Objects.equals(description, other.description)
				&& Objects.equals(id, other.id) && Objects.equals(title, other.title)
				&& Objects.equals(uploadDate, other.uploadDate) && Objects.equals(uploadedBy, other.uploadedBy);
	}

	@Override
	public String toString() {
		return "Image [id=" + id + ", content=" + Arrays.toString(content) + ", title=" + title + ", description="
				+ description + ", uploadDate=" + uploadDate + ", uploadedBy=" + uploadedBy + "]";
	}
}

