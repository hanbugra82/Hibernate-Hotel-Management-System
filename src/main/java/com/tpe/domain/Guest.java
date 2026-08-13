package com.tpe.domain;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "t_guest")
public class Guest {

    @Id
    @GeneratedValue(generator = "sequence", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "sequence", sequenceName = "guest_seq",
            initialValue = 100, allocationSize = 10)
    private Long id;

    @Column(nullable = false)
    private String name;

    private LocalDateTime createOn;

    @Embedded
    private Address address;

    @OneToMany(mappedBy = "guest",orphanRemoval = true)
    private List<Reservation> reservations = new ArrayList<>();

    //----------------Parametresiz cons.
    public Guest() {
    }

    //----------------Parametreli cons.
    public Guest(String name, Address address) {
        this.name = name;
        this.address = address;
    }

    //----------------getter-setter
    public Long getId() {
        return id;
    }

/*    public void setId(Long id) {
        this.id = id;
    }*/

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getCreateOn() {
        return createOn;
    }

    @PrePersist
    public void setCreateOn() {
        this.createOn = LocalDateTime.now(); //kayit tarihini otomatik atar
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
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
        return "Guest{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", createOn=" + createOn +
                ", address=" + address +
                '}';
    }
}