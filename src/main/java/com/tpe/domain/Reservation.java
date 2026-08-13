package com.tpe.domain;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "t_reservation")
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private LocalDate checkIn;

    @Column(nullable = false)
    private LocalDate checkOut;

    //ILISKI 1: Bir rezervasyon, yalnizca bir odaya aittir, bir odanin birden fazla rezervasyonu olabilir
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private Room room;

    //ILISKI 2: Bir rezervasyon, yalnizca bir misafire aittir, bir misafirin birden fazla rezervasyonu olabilir
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private Guest guest;

    //----------------Parametresiz cons.
    public Reservation() {
    }

    //----------------Parametreli cons.
    public Reservation(LocalDate checkIn, LocalDate checkOut, Room room, Guest guest) {
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.room = room;
        this.guest = guest;
    }
    //----------------getter-setter
    public Long getId() {
        return id;
    }

/*    public void setId(Long id) {
        this.id = id;
    }*/

    public LocalDate getCheckIn() {
        return checkIn;
    }

    public void setCheckIn(LocalDate checkIn) {
        this.checkIn = checkIn;
    }

    public LocalDate getCheckOut() {
        return checkOut;
    }

    public void setCheckOut(LocalDate checkOut) {
        this.checkOut = checkOut;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public Guest getGuest() {
        return guest;
    }

    public void setGuest(Guest guest) {
        this.guest = guest;
    }

    //----------------toString (OZELLESTIRILDI)
    @Override
    public String toString() {
        return "Reservation{" +
                "id=" + id +
                ", checkIn=" + checkIn +
                ", checkOut=" + checkOut +
                // DİKKAT: Tüm 'room' objesini yazdırmak yerine sadece ID'sini alıyoruz.
                // Aksi takdirde sonsuz döngü (StackOverflow) riski olabilir.
                ", room_id=" + (room != null ? room.getId() : "null") +
                ", guest_id=" + (guest != null ? guest.getId() : "null") +
                '}';
    }

}