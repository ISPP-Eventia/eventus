package com.eventus.backend.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.Objects;


@Entity
public class Participation {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonProperty("id")
    private Long id;

    @Column
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    @CreationTimestamp
    @JsonProperty("buyDate")
    private LocalDate buyDate;

    @Column
    @Size(max=100)
    @JsonProperty("ticket")
    private String ticket;

    @Column
    @JsonProperty("price")
    @Min(value=0)
    private Double price;

    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "user_id")
    User user;
 /*
    @ManyToOne
    @MapsId("eventId")
    @JoinColumn(name = "event_id")
    Event event;*/

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
