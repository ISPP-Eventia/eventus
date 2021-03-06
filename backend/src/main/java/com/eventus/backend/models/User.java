package com.eventus.backend.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.*;

@Entity
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonProperty("id")
    private Long id;

    @Column
    @NotBlank
    private String password;

    @Column
    @NotBlank
    @Email
    private String email;

    @Column
    @Size(max=20)
    @NotBlank
    @JsonProperty("firstName")
    private String firstName;

    @Column
    @Size(max=40)
    @NotBlank
    @JsonProperty("lastName")
    private String lastName;

    @Column
    @DateTimeFormat(pattern = "YYYY-MM-DD")
    @JsonProperty("birthDate")
    @Past(message = "La fecha de nacimiento no puede ser presente o futura.")
    private LocalDate birthDate;

    @Column
    @JsonProperty("isAdmin")
    @NotNull
    private boolean isAdmin;
    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "owner", orphanRemoval = true)
    private Set<Media> media;

    @OneToMany(mappedBy = "organizer")
    @JsonIgnore
    private Set<Event> events = new HashSet<>();

    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private Set<Participation> participations = new HashSet<>();

    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private Set<Sponsorship> sponsors = new HashSet<>();
    
    @OneToMany(mappedBy = "owner")
    @JsonIgnore
    private Set<Location> locations = new HashSet<>();

    @Column
    @JsonIgnore
    private String customerId;


    public Set<Sponsorship> getSponsors() {
        return sponsors;
    }

    public void setSponsors(Set<Sponsorship> sponsors) {
        this.sponsors = sponsors;
    }

    public Set<Location> getLocations() {
        return locations;
    }

    public void setLocations(Set<Location> locations) {
        this.locations = locations;
    }

    public Set<Event> getEvents() {
        return events;
    }

    public void setEvents(Set<Event> events) {
        this.events = events;
    }

    public Set<Participation> getParticipations() {
        return participations;
    }

    public void setParticipations(Set<Participation> participations) {
        this.participations = participations;
    }

    public Set<Media> getMedia() {
        return media;
    }

    public void setMedia(Set<Media> media) {
        this.media = media;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email=email;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    @JsonProperty("password")
    public void setPassword(String password) {
        this.password = password;
    }

    @JsonIgnore
    @Override
    public Collection<GrantedAuthority> getAuthorities() {
        return new ArrayList<>();
    }

    @JsonIgnore
    @Override
    public String getPassword() {
        return password;
    }

    @JsonIgnore
    @Override
    public String getUsername() {
        return email;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return isAdmin == user.isAdmin && Objects.equals(id, user.id) && Objects.equals(password, user.password) && Objects.equals(email, user.email) && Objects.equals(firstName, user.firstName) && Objects.equals(lastName, user.lastName) && Objects.equals(birthDate, user.birthDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, password, email, firstName, lastName, birthDate, isAdmin);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", birthDate=" + birthDate +
                ", isAdmin=" + isAdmin +
                '}';
    }
}
