package com.packtpub.wflydevelopment.chapter5.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;

/**
 * The persistent class for the seat_type database table.
 */
@Entity
@Table(name = "seat_type")
public class SeatType implements Serializable {

    private static final long serialVersionUID = 3643635L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 1, max = 25, message = "Enter a Seat Description (max 25 char)")
    @Pattern(regexp = "[A-Za-z ]*", message = "Description must contain only letters and spaces")
    private String description;

    private SeatPosition position;

    @NotNull
    private Integer price;

    @NotNull
    private Integer quantity;

    // bi-directional many-to-one association to Seat
    @OneToMany(mappedBy = "seatType", fetch = FetchType.EAGER)
    private List<Seat> seats;

    public SeatType() {
        // empty for jpa
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getPrice() {
        return this.price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public List<Seat> getSeats() {
        return this.seats;
    }

    public void setSeats(List<Seat> seats) {
        this.seats = seats;
    }

    public SeatPosition getPosition() {
        return position;
    }

    public void setPosition(SeatPosition position) {
        this.position = position;
    }

}