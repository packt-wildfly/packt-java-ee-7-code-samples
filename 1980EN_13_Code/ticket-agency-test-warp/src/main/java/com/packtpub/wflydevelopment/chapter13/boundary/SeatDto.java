package com.packtpub.wflydevelopment.chapter13.boundary;

import com.packtpub.wflydevelopment.chapter13.entity.Seat;

/**
 * @author Michal Matloka
 */
public class SeatDto {

    private int id;
    private String name;
    private int price;
    private boolean booked;

    public SeatDto() {
        /* empty for serialization */
    }

    public SeatDto(int id, String name, int price, boolean booked) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.booked = booked;
    }

    public static SeatDto fromSeat(Seat seat) {
        return new SeatDto(seat.getId(), seat.getName(), seat.getPrice(), seat.isBooked());
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public boolean isBooked() {
        return booked;
    }

    public void setBooked(boolean booked) {
        this.booked = booked;
    }

    @Override
    public String toString() {
        return "SeatDto [id=" + id + ", name=" + name + ", price=" + price + ", booked=" + booked + "]";
    }
}
