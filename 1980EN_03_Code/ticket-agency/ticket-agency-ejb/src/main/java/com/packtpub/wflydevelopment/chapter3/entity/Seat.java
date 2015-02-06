package com.packtpub.wflydevelopment.chapter3.entity;

/**
 * Seat is immutable. In order to book use getBookedSeat() which sets
 * the booked parameter to true and place it in the seats store.
 */
public class Seat {

    private final int id;
    private final String name;
    private final int price;
    private final boolean booked;

    public Seat(int id, String name, int price) {
        this(id, name, price, false);
    }

    private Seat(int id, String name, int price, boolean booked) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.booked = booked;
    }

    public Seat getBookedSeat() {
        return new Seat(getId(), getName(), getPrice(), true);
    }

    public int getId() {
        return id;
    }

    public boolean isBooked() {
        return booked;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return "Seat [id=" + id + ", name=" + name + ", price=" + price + ", booked=" + booked + "]";
    }
}
