package com.tpe.domain;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "t_room")
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String number;

    @Column(nullable = false)
    private Integer capacity;

    @ManyToOne(fetch = FetchType.LAZY)

    //ILISKI 1: Bir oda, yalnizca bir otele aittir

    @JoinColumn(nullable = false) //Otel olmadan oda kaydedilemesin
    private Hotel hotel;

    //ILISKI 2: Bir odanin farkli tarihlerde birden cok rezervasyonu olabilir.(One-to-Many)
    //One --> Room class'i
    //Many --> Reservation class'i

    @OneToMany(mappedBy = "room", orphanRemoval = true)
    private List<Reservation> reservations = new ArrayList<>();

    //----------------Parametresiz cons.
    public Room() {
    }

    //----------------Parametreli cons.
    public Room(String number, Integer capacity) {
        this.number = number;
        this.capacity = capacity;
    }

    //----------------getter-setter
    public Long getId() {
        return id;
    }

/*
    public void setId(Long id) {
        this.id = id;
    }
*/

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public Hotel getHotel() {
        return hotel;
    }

    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
    }

    public List<Reservation> getReservations() {
        return reservations;
    }

    public void setReservations(List<Reservation> reservations) {
        this.reservations = reservations;
    }

    //----------------toString
    @Override
    public String toString() {
        return "Room{" +
                "id=" + id +
                ", number='" + number + '\'' +
                ", capacity=" + capacity +
                '}';
    }
}