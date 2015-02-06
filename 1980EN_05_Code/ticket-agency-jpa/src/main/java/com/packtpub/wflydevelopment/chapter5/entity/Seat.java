package com.packtpub.wflydevelopment.chapter5.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;


/**
 * The persistent class for the seat database table.
 */
@Entity
public class Seat implements Serializable {

    private static final long serialVersionUID = 89897231L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private boolean booked;

    //bi-directional many-to-one association to SeatType
    @ManyToOne
    @JoinColumn(name = "seat_id")
    private SeatType seatType;

    public Seat() {
        // empty for jpa
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean getBooked() {
        return this.booked;
    }

    public void setBooked(boolean booked) {
        this.booked = booked;
    }

    public SeatType getSeatType() {
        return this.seatType;
    }

    public void setSeatType(SeatType seatType) {
        this.seatType = seatType;
    }

    @Override
    public String toString() {
        return "Seat [id=" + id + ", booked=" + booked + ", seatType="
                + seatType + "]";
    }
}