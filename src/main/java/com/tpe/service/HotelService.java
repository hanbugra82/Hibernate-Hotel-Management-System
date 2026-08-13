package com.tpe.service;

import com.tpe.controller.HotelManagementApp;
import com.tpe.domain.Hotel;
import com.tpe.exception.HotelNotFoundException;
import com.tpe.repository.HotelRepository;

import java.util.List;
import java.util.Scanner;

public class HotelService {

    private final Scanner scanner = HotelManagementApp.scanner;

    private final HotelRepository hotelRepository;

    //Bagimlilik enjeksiyonu (dependency injection)
    //Parametreli constructor : Soz veriyorum, bu servisi olustururken sana calisan bir repo verecegim

    public HotelService(HotelRepository hotelRepository) {
        this.hotelRepository = hotelRepository;
    }

    // ---------------------------------------------------------------
    // 1- ----------------- SAVE HOTEL
    // ---------------------------------------------------------------
    public void saveHotel() {

        Hotel hotel = new Hotel();

        System.out.println("Enter hotel name : " );
        hotel.setName(scanner.nextLine());

        System.out.println("Enter hotel location : " );
        hotel.setLocation(scanner.nextLine());

        //Veritabanina kaydetme emrini Repository'e verelim
        hotelRepository.save(hotel);

        System.out.println("Hotel is saved successfully. Hotel ID : " + hotel.getId());

    }

    // ---------------------------------------------------------------
    // 2- ----------------- FIND HOTEL BY ID
    // ---------------------------------------------------------------
    public Hotel findHotelById(Long id) {

        //Repo dan veriyi istedik. Bulursa nesne, bulamazsa null doner
        Hotel found = hotelRepository.findById(id);

        try {
            if (found != null) {
                System.out.println("---------------------------");
                System.out.println(found);
                System.out.println("---------------------------");
            } else {
                throw new HotelNotFoundException("Hotel not found by ID: " + id);
            }
        } catch (HotelNotFoundException e) {
            System.out.println(e.getMessage());
        }
        return found; //Bulunamadiysa null doner, metodu cagiran yer bunu kontrol etmelidir

    }

    // ---------------------------------------------------------------
    // 3- ---------------- GET ALL HOTELS
    // ---------------------------------------------------------------
    public void getAllHotels() {
        List<Hotel> hotelList = hotelRepository.findAll(); //SQL: SELECT * FROM t_hotel

        if (hotelList.isEmpty()) {
            System.out.println("Hotel list is EMPTY!!!");
        } else {
            System.out.println("-------------------ALL HOTELS-------------------");
            for (Hotel hotel : hotelList) {
                System.out.println(hotel);
            }
            System.out.println("-------------------------------------------------");
        }
    }

    // ---------------------------------------------------------------
    // 4- ---------------- DELETE HOTEL
    // ---------------------------------------------------------------
    public void deleteHotelById(Long id) {

        //DRY (Don't Repeat Yourself) Prensibi:
        //Tekrar kod yazma, kendine tekrar etme
        //Zaten ustte yazdigimiz "findHotelById" metodu otelin var olup olmadigini kontrol ediyor

        Hotel hotel = findHotelById(id);

        if (hotel != null) {
            hotelRepository.delete(hotel);
            System.out.println("Hotel is deleted successfully...ID: " + id);
        } else {
            //findHotelById hata mesaji yazdirdigi icin buraya ekstra mesaj gerekmeyebilir
            System.out.println("Delete operation is CANCELLED!");
        }
    }

    // ---------------------------------------------------------------
    // 5- ----------------- UPDATE HOTEL
    // ---------------------------------------------------------------
    public void updateHotelById(Long id) {

        //1.ADIM: Guncellenecek oteli veritabanindan cekelim
        // ID=1, Ad=A, Konum=X, (1,A,Y)
        Hotel existingHotel = findHotelById(id);

        //2.ADIM: Eger otel varsa guncelleme islemlerini yapalim
        if (existingHotel != null) {
            //ID yani PK asla degismez, sadece ozellikler degisir

            System.out.println("Enter new hotel name : ");
            //Kullanici B girerse
            existingHotel.setName(scanner.nextLine());

            System.out.println("Enter new hotel location : ");
            //Kullanici X girerse
            existingHotel.setLocation(scanner.nextLine());

            //3.ADIM: Guncellenmis objeyi (1, B, X) repo'ya gonderelim
            hotelRepository.update(existingHotel);
            System.out.println("Hotel is updated...");
        }
    }
}