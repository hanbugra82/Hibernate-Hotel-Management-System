package com.tpe.service;

import com.tpe.controller.HotelManagementApp;
import com.tpe.domain.Guest;
import com.tpe.domain.Reservation;
import com.tpe.domain.Room;
import com.tpe.exception.ReservationNotFoundException;
import com.tpe.exception.RoomAlreadyReservedException;
import com.tpe.repository.ReservationRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class ReservationService {

    private Scanner scanner = HotelManagementApp.scanner;

    private final ReservationRepository reservationRepository;
    private final RoomService roomService;
    private final GuestService guestService;

    public ReservationService(ReservationRepository reservationRepository, RoomService roomService, GuestService guestService) {
        this.reservationRepository = reservationRepository;
        this.roomService = roomService;
        this.guestService = guestService;
    }

    // ---------------------------------------------------------------
    // 1- FIND RESERVATION BY ID
    // ---------------------------------------------------------------
    public Reservation findReservationById(Long id) {

        Reservation found = reservationRepository.findById(id);

        try {

            if (found != null) {
                System.out.println("---------------------------------------");
                System.out.println(found);
                System.out.println("---------------------------------------");
            } else {
                throw new ReservationNotFoundException("Reservation not found by ID : " + id);
            }

        } catch (ReservationNotFoundException e) {
            System.out.println(e.getMessage());
        }
        return found;
    }

    // ---------------------------------------------------------------
    // 2- GET ALL RESERVATIONS
    // ---------------------------------------------------------------
    public void getAllReservations() {

        List<Reservation> reservationList = reservationRepository.findAll();

        if (reservationList.isEmpty()) {
            System.out.println("Reservation list is EMPTY!!!");
        } else {
            System.out.println("----------------ALL RESERVATIONS-------------------");
            for (Reservation reservation : reservationList) {
                System.out.println(reservation);
            }
            System.out.println("----------------------------------------------------");
        }
    }

    // ---------------------------------------------------------------
    // 3- CREATE RESERVATION (Çakışma Kontrollü)
    // ---------------------------------------------------------------
    public void createReservation() {

        Reservation reservation = new Reservation();

        //1.Tarihleri alalim
        //String --> localDate donusumu
        System.out.println("Enter check-in date(yyyy-MM-dd) : ");
        LocalDate checkIn = LocalDate.parse(scanner.nextLine());

        System.out.println("Enter check-out date(yyyy-MM-dd) : ");
        LocalDate checkOut = LocalDate.parse(scanner.nextLine());

        //Validation: Giris tarihi cikistan sonra olamaz

        if (checkIn.isAfter(checkOut)) {
            throw new IllegalArgumentException("Check-in date cannot be after Check-out date!");
            //Bu exception'i controller da handle edecegiz
        }
        reservation.setCheckIn(checkIn);
        reservation.setCheckOut(checkOut);

        //2.oda ve misafir secimi
        System.out.println("Enter room id : ");
        long roomId = scanner.nextLong();
        scanner.nextLine();

        System.out.println("Enter guest id : ");
        long guestId = scanner.nextLong();
        scanner.nextLine();

        //Diger servisleri kullanarak Oda ve Misafiri bulalim
        Room room = roomService.findRoomById(roomId);
        Guest guest = guestService.findGuestById(guestId);

        //3. Kontrol ve kayit
        if (room != null && guest != null) { //oda ve misafir varsa

            //Musaitlik kontrolu
            //odanin o tarihlerde bos olup olmadigi kontrol eden metodu cagiralim
            boolean isAvailable = checkRoomAvailability(room, checkIn, checkOut);

            if (isAvailable) {
                //Oda bos, iliskiyi kuralim ve kaydedelim
                reservation.setRoom(room);
                reservation.setGuest(guest);
                reservationRepository.save(reservation);
                System.out.println("Reservation is created successfully...");
            } else {
                //Oda doluysa HATA firlat
                throw new RoomAlreadyReservedException("Room is already reserved for these dates!");
            }

        } else {
            System.out.println("Reservatin couldn't be created because Room or Guest not found!");
        }
    }

    // ---------------------------------------------------------------
    // 4- DELETE RESERVATION
    // ---------------------------------------------------------------
    public void deleteReservationById(Long id) {
        Reservation reservation = findReservationById(id);

        if (reservation != null) {
            reservationRepository.delete(reservation);
            System.out.println("Reservation is deleted successfully...id : " + id);
        }
    }

    // ---------------------------------------------------------------
    // 5- UPDATE RESERVATION (Rezervasyon Güncelleme)
    // ---------------------------------------------------------------
    public void updateReservation(Long id) {
        //1. Once guncellenmek istenen rezervasyon var mi diye kontrol edelim
        Reservation existingReservation = findReservationById(id);

        if (existingReservation != null) {
            //2.Kullanicidan yeni bilgileri alalim
            System.out.println("Enter new check-in date(yyyy-MM-dd)");
            LocalDate checkIn = LocalDate.parse(scanner.nextLine());

            System.out.println("Enter new check-out date(yyyy-MM-dd)");
            LocalDate checkOut = LocalDate.parse(scanner.nextLine());

            //Tarih mantik kontrolu
            if (checkIn.isAfter(checkOut)) {
                throw new IllegalArgumentException("Check-in date cannot be after Check-out date!");
            }

            System.out.println("Enter new room id : ");
            long roomId = scanner.nextLong();
            scanner.nextLine();

            System.out.println("Enter new guest id : ");
            long guestId = scanner.nextLong();
            scanner.nextLine();

            //3. Oda ve misafiri bulalim
            Room room = roomService.findRoomById(roomId);
            Guest guest = guestService.findGuestById(guestId);

            //4.Musaitlik kontrolu
            if (room != null && guest != null) {

                boolean isAvailable = checkRoomAvailability(room, checkIn, checkOut);

                if (isAvailable) {
                    //5.Guncelleme ve kayit
                    existingReservation.setCheckIn(checkIn);
                    existingReservation.setCheckOut(checkOut);
                    existingReservation.setRoom(room);
                    existingReservation.setGuest(guest);

                    reservationRepository.update(existingReservation);
                    System.out.println("Reservation is updated successfully...");

                } else {
                    throw new RoomAlreadyReservedException("Room is already reserved for these dates...");
                }

            } else {
                System.out.println("Update failed: Room or Guest not found");
            }
        }
    }

    // =================================================================
    // PRIVATE HELPER METHOD: Müsaitlik Kontrolü (Business Logic)
    // =================================================================
    // Bu metot sadece bu class içinde kullanılacağı için private yapıldı.

    private boolean checkRoomAvailability(Room room, LocalDate checkIn, LocalDate checkOut) {

        //Bu islemi repo'ya tasiyin
        //1.DB'deki tum rezervasyonlari getirelim
        List<Reservation>  allReservations = reservationRepository.findAll();

        for (Reservation res : allReservations) {
            //Sadece ilgilendigimiz odaya ait rezervasyonlara bakalim
            if (res.getRoom().getId().equals(room.getId())) {

                //Cakisma kontrolu :
                if ((res.getCheckIn().isBefore(checkOut)) && (res.getCheckOut().isAfter(checkIn))) {
                    System.out.println("Room is occupied between " + res.getCheckIn() + " and " + res.getCheckOut());
                    return false; //Cakisma var, oda musait degil
                }
            }
        }
        return true; //Dongu bitti ve cakisma bulunmadi, oda musait
    }
}
