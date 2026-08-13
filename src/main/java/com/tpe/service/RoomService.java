package com.tpe.service;

import com.tpe.controller.HotelManagementApp;
import com.tpe.domain.Hotel;
import com.tpe.domain.Room;
import com.tpe.exception.RoomNotFoundException;
import com.tpe.repository.RoomRepository;

import java.util.List;
import java.util.Scanner;

public class RoomService {

    private final Scanner scanner = HotelManagementApp.scanner;

    //Bagimliliklar (Constructor Injection)
    private final RoomRepository roomRepository;
    private final HotelService hotelService;

    public RoomService(RoomRepository roomRepository, HotelService hotelService) {
        this.roomRepository = roomRepository;
        this.hotelService = hotelService;
    }

    // -------------------------------------------------------
    // 1- SAVE ROOM (Oda Kaydetme)
    // -------------------------------------------------------
    public void saveRoom() {

        Room room = new Room();

        System.out.println("Enter room number : ");
        room.setNumber(scanner.next());

        System.out.println("Enter room capacity : ");
        room.setCapacity(scanner.nextInt());
        scanner.nextLine(); //Dummy

        //KRITIK NOKTA: Odayi bir otele baglama
        System.out.println("Enter hotel id : ");
        long hotelId = scanner.nextLong();
        scanner.nextLine();

        //HotelService'i kullanarak oteli bulalim.
        Hotel hotel = hotelService.findHotelById(hotelId);

        if (hotel != null) {
            //Eger otel geldiyse iliskiyi kuralim.
            room.setHotel(hotel);

            roomRepository.save(room);
            System.out.println("Room is saved successfully.....Room id : " + room.getId());
        } else {
            System.out.println("Room NOT saved because Hotel not found!");
        }
    }

    // ---------------------------------------------------------------
    // 2- FIND ROOM BY ID (ID ile Oda Bulma)
    // ---------------------------------------------------------------
    public Room findRoomById(Long id) {

        Room found = roomRepository.findById(id);

        try {
            if (found != null) {
                System.out.println("-------------------------------------");
                System.out.println(found);
                System.out.println("-------------------------------------");
            } else {
                throw new RoomNotFoundException("Room not found by ID : " + id);
            }

        } catch (RoomNotFoundException e) {
            System.out.println(e.getMessage());
        }
        return found;
    }

    // ---------------------------------------------------------------
    // 3- FIND ALL ROOMS (Tüm Odaları Listeleme)
    // ---------------------------------------------------------------
    public void getAllRooms() {
        List<Room> roomList = roomRepository.findAll();

        if (roomList.isEmpty()) {
            System.out.println("Room list is EMPTY!!!");
        } else {
            System.out.println("---------------------ALL ROOMS-------------------");
            for (Room room : roomList) {
                System.out.println(room);
            }
            System.out.println("-------------------------------------------------");
        }
    }

    // ---------------------------------------------------------------
    // 4- DELETE ROOM (Oda Silme)
    // ---------------------------------------------------------------
    public void deleteRoomById(Long id) {
        Room room = findRoomById(id);

        if (room != null) {
            roomRepository.delete(room);
            System.out.println("Room is deleted successfully. ID : " + id);
        }
    }

    // ---------------------------------------------------------------
    // 5- UPDATE ROOM (Oda Güncelleme)
    // ---------------------------------------------------------------
    public void updateRoomById(Long id) {

        //Guncellenecek odayi bulalim
        Room existingRoom = findRoomById(id);

        if (existingRoom != null) {

            //ID ve Hotel degismez, sadece oda ozellikleri degisebilir

            System.out.println("Enter new room number : ");
            existingRoom.setNumber(scanner.next());

            System.out.println("Enter new capacity : ");
            existingRoom.setCapacity(scanner.nextInt());
            scanner.nextLine();

            roomRepository.update(existingRoom);
            System.out.println("Room is updated");

        }
    }

    // ---------------------------------------------------------------
    // YENİ METOT: Otelin Odalarını Listele
    // ---------------------------------------------------------------
    public void getAllRoomsByHotelId(Long hotelId) {

        //1.ADIM: Once otel var mi diye kontrol edelim.
        Hotel hotel = hotelService.findHotelById(hotelId);

        if (hotel != null) {

            //2.ADIM: Otel var, odalarini getirelim
            List<Room> rooms = roomRepository.findAllRoomsByHotelId(hotelId);

            if (rooms.isEmpty()) {
                System.out.println("No rooms found for tthis hotel (ID: " + hotelId + ") ");
            } else {
                System.out.println("---------------ROOMS OF HOTEL: " + hotel.getName() + "------------");
                for (Room room : rooms) {
                    System.out.println(room);
                }
                System.out.println("-------------------------------------------------------------------");
            }
            //Otel yoksa zaten findHotelById metodu hata mesaji veriyor, else yazmaya gerek yok
        }

    }

}