package com.eventus.backend.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.Min;
import java.time.LocalDate;
import java.util.Objects;


@Entity
public class Participation {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonProperty("id")
    private Long id;

    @Column
    @DateTimeFormat(pattern = "YYYY-MM-DDTHH:MM:SS")
    @CreationTimestamp
    @JsonProperty("buyDate")
    private LocalDate buyDate;

    @Column
    @JsonProperty("ticket")
    private String ticket;

    @Column
    @JsonProperty("price")
    @Min(value=0)
    private Double price;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "user_id")
    User user;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "event_id")
    Event event;

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }


    public LocalDate getBuyDate() {
        return buyDate;
    }

    public void setBuyDate(LocalDate buyDate) {
        this.buyDate = buyDate;
    }

    public String getTicket() {
        return ticket;
    }

    public void setTicket(String ticket) {
        this.ticket = ticket;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Participation that = (Participation) o;
        return Objects.equals(id, that.id) && Objects.equals(buyDate, that.buyDate) && Objects.equals(ticket, that.ticket) && Objects.equals(price, that.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, buyDate, ticket, price);
    }


}
