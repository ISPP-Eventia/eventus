package com.eventus.backend.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.util.Objects;

@Entity
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonProperty("id")
    private Long id;

    @Column
    @NotBlank
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

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Image image = (Image) o;
        return Objects.equals(id, image.id) && Objects.equals(path, image.path) && Objects.equals(title, image.title) && Objects.equals(description, image.description) && Objects.equals(uploadDate, image.uploadDate) && Objects.equals(uploadedBy, image.uploadedBy);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, path, title, description, uploadDate, uploadedBy);
    }
}

