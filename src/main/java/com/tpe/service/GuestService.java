package com.tpe.service;

import com.tpe.controller.HotelManagementApp;
import com.tpe.domain.Address;
import com.tpe.domain.Guest;
import com.tpe.exception.GuestNotFoundException;
import com.tpe.repository.GuestRepository;

import java.util.List;
import java.util.Scanner;

public class GuestService {

    private Scanner scanner = HotelManagementApp.scanner;

    private final GuestRepository guestRepository;

    public GuestService(GuestRepository guestRepository) {
        this.guestRepository = guestRepository;
    }

    // ---------------------------------------------------------------
    // 1- FIND GUEST BY ID (ID ile Misafir Bulma)
    // ---------------------------------------------------------------
    public Guest findGuestById(Long id) {

        Guest found = guestRepository.findById(id);

        try {

            if (found != null) {
                System.out.println("------------------------------------");
                System.out.println(found);
                System.out.println("------------------------------------");
            } else {
                throw new GuestNotFoundException("Guest not found by ID : " + id);
            }

        } catch (GuestNotFoundException e) {
            System.out.println(e.getMessage());
        }
        return found;
    }

    // ---------------------------------------------------------------
    // 2- GET ALL GUESTS (Tüm Misafirleri Listeleme)
    // ---------------------------------------------------------------
    public void getAllGuests() {

        List<Guest> guestList = guestRepository.findAll();

        if (guestList.isEmpty()) {
            System.out.println("Guest list is EMPTY!!!");
        } else {
            System.out.println("-------------------ALL GUESTS---------------------");
            for (Guest guest : guestList) {
                System.out.println(guest);
            }
            System.out.println("---------------------------------------------------");
        }
    }

    // ---------------------------------------------------------------
    // 3- SAVE GUEST (Misafir Kaydetme)
    // ---------------------------------------------------------------
    public void saveGuest() {

        Guest guest = new Guest();

        System.out.println("Enter name : ");
        guest.setName(scanner.nextLine());

        //Adress baslibasina bir entity degildir
        //Adress class'in db'de ayri bir tablosu yoktur

        Address address = new Address();

        System.out.println("Enter street");
        address.setStreet(scanner.nextLine());

        System.out.println("Enter city : ");
        address.setCity(scanner.nextLine());

        System.out.println("Enter country : ");
        address.setCountry(scanner.nextLine());

        System.out.println("Enter zipcode : ");
        address.setZipcode(scanner.nextLine());

        //Olusturdugumuz adres objesini misafir objesine set edelim
        guest.setAddress(address);

        //guest objesinin ID'si yok, o yuzden INSERT calisti
        guestRepository.saveOrUpdate(guest);
        System.out.println("Guest is saved successfully...");
    }

    // ---------------------------------------------------------------
    // 4- DELETE GUEST (Misafir Silme)
    // ---------------------------------------------------------------
    public void deleteGuestById(Long id) {
        Guest guest = findGuestById(id);

        if (guest != null) {
            guestRepository.delete(guest);
            System.out.println("Guest is deleted successfully...ID : " + id);
        } else {
            System.out.println("Delete operatin is CANCELLED!");
        }
    }

    // ---------------------------------------------------------------
    // 5- UPDATE GUEST (Misafir Güncelleme)
    // ---------------------------------------------------------------
    public void updateGuestById(Long id) {

        //Once guncellenecek misafiri bulalim
        Guest existingGuest = findGuestById(id);

        if (existingGuest != null) {

            //1.Isim guncelleme
            System.out.println("Enter new name : ");
            existingGuest.setName(scanner.nextLine());

            //2.Adres guncelleme (nested update)
            System.out.println("Enter new street : ");
            existingGuest.getAddress().setStreet(scanner.nextLine());

            System.out.println("Enter new city : ");
            existingGuest.getAddress().setCity(scanner.nextLine());

            System.out.println("Enter new country : ");
            existingGuest.getAddress().setCountry(scanner.nextLine());

            System.out.println("Enter new zipcode : ");
            existingGuest.getAddress().setZipcode(scanner.nextLine());

            //Merge islemi: ID oldugu icin UPDATE calisti
            guestRepository.saveOrUpdate(existingGuest);
            System.out.println("Guest is updated...");
        }
    }
}