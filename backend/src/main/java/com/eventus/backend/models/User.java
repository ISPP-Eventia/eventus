package com.eventus.backend.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sun.istack.NotNull;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Objects;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonProperty("id")
    private Long id;

    @Column
    @NotBlank
    @Max(value=20)
    @JsonProperty("firstName")
    private String firstName;

    @Column
    @NotBlank
    @Max(value=40)
    @JsonProperty("lastName")
    private String lastName;

    @Column
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    @JsonProperty("birthDate")
    private LocalDate birthDate;

    @Column
    @JsonProperty("image")
    private Byte[] image;



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

    public Byte[] getImage() {
        return image;
    }

    public void setImage(Byte[] image) {
        this.image = image;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) && Objects.equals(firstName, user.firstName) && Objects.equals(lastName, user.lastName) && Objects.equals(birthDate, user.birthDate) && Arrays.equals(image, user.image);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(id, firstName, lastName, birthDate);
        result = 31 * result + Arrays.hashCode(image);
        return result;
    }
}
