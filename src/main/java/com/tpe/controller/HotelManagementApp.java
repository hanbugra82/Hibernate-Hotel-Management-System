package com.tpe.controller;

import com.tpe.config.HibernateUtils;
import com.tpe.repository.GuestRepository;
import com.tpe.repository.HotelRepository;
import com.tpe.repository.ReservationRepository;
import com.tpe.repository.RoomRepository;
import com.tpe.service.GuestService;
import com.tpe.service.HotelService;
import com.tpe.service.ReservationService;
import com.tpe.service.RoomService;

import java.util.Scanner;

public class HotelManagementApp {
    public static Scanner scanner = new Scanner(System.in);

    //Ana menu kullaniciya gosterilir ve secimi alinir

    public static void displayHotelManagementAppMenu() {

        //Kablolama (wiring) - Dependency Injection (manuel)
        //uygulamanin calismasi icin gerekli olan tum objeleri burada tek bir defa olusturalim
        //Spring bunu @Autowired ile otomatik yapacak

        //1.repo tarafi
        HotelRepository hotelRepository = new HotelRepository();
        RoomRepository roomRepository = new RoomRepository();
        GuestRepository guestRepository = new GuestRepository();
        ReservationRepository reservationRepository = new ReservationRepository();

        //2.servis tarafi
        HotelService hotelService = new HotelService(hotelRepository);
        RoomService roomService = new RoomService(roomRepository, hotelService);
        GuestService guestService = new GuestService(guestRepository);
        ReservationService reservationService = new ReservationService(reservationRepository, roomService, guestService);

        // -----------------------------------------------------------------------
        //  MENÜ DÖNGÜSÜ
        // -----------------------------------------------------------------------

        int choice;

        do {

            System.out.println("=================== Hotel Management System ===================");
            System.out.println("1. Hotel Operations");
            System.out.println("2. Room Operations");
            System.out.println("3. Guest Operations");
            System.out.println("4. reservation Operations");
            System.out.println("0. Exit");
            System.out.println("Enter your choice : ");

            choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {

                case 1:
                    displayHotelOperationsMenu(hotelService);
                    break;
                case 2:
                    displayRoomOperationsMenu(roomService);
                    break;
                case 3:
                    displayGuestOperationsMenu(guestService);
                    break;
                case 4:
                    displayReservationOperationsMenu(reservationService);
                    break;
                case 0:
                    System.out.println("Good bye...");
                    //uygulama kapanirken fabrikayi da kapatalim
                    HibernateUtils.shutdown();
                    break;
                default:
                    System.out.println("Invalid choice, please try again!");
                    break;
            }
        } while (choice != 0);
    }

    // -----------------------------------------------------------------------
    // ALT MENÜLER (Helper Methods)
    // -----------------------------------------------------------------------

    //1. HOTEL OPERATIONS
    private static void displayHotelOperationsMenu(HotelService hotelService) {
        boolean exit = false;
        while (!exit) {
            System.out.println("==== Hotel operations =====");
            System.out.println("1. Add a New Hotel");
            System.out.println("2. Find Hotel By ID");
            System.out.println("3. Delete Hotel By ID");
            System.out.println("4. Find All Hotels");
            System.out.println("5. Update Hotel By ID");
            System.out.println("0. Return to Main Menu");
            System.out.println("Enter your choice");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    hotelService.saveHotel();
                    break;
                case 2:
                    System.out.println("Enter hotel id : ");
                    long id = scanner.nextLong();
                    scanner.nextLine();
                    hotelService.findHotelById(id);
                    break;
                case 3:
                    System.out.println("enter hotel id : ");
                    long delId = scanner.nextLong();
                    scanner.nextLine();
                    hotelService.deleteHotelById(delId);
                    break;
                case 4:
                    hotelService.getAllHotels();
                    break;
                case 5:
                    System.out.println("Enter hotel id : ");
                    long updId = scanner.nextLong();
                    scanner.nextLine();
                    hotelService.updateHotelById(updId);
                    break;
                case 0:
                    exit = true;
                    break;
                default:
                    System.out.println("Invalid choice!");
                    break;
            }
        }
    }

    //2. ROOM OPERATIONS
    private static void displayRoomOperationsMenu(RoomService roomService) {
        boolean exit = false;
        while (!exit) {

            System.out.println("==== Room operations ====");
            System.out.println("1. Add a New Room");
            System.out.println("2. Find Room By ID");
            System.out.println("3. Delete Room By ID");
            System.out.println("4. Find All Rooms");
            System.out.println("5. Update Room By ID");
            System.out.println("6. Find All rooms Of A Hotel");
            System.out.println("0. Return to Main Menu");
            System.out.println("Enter your choice");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    roomService.saveRoom();
                    break;
                case 2:
                    System.out.println("Enter room id : ");
                    long roomId2 = scanner.nextLong();
                    scanner.nextLine();
                    roomService.findRoomById(roomId2);
                    break;
                case 3:
                    System.out.println("Enter room id : ");
                    long roomId3 = scanner.nextLong();
                    scanner.nextLine();
                    roomService.deleteRoomById(roomId3);
                    break;
                case 4:
                    roomService.getAllRooms();
                    break;
                case 5:
                    System.out.println("Enter room id : ");
                    long roomId5 = scanner.nextLong();
                    scanner.nextLine();
                    roomService.updateRoomById(roomId5);
                    break;
                case 6:
                    System.out.println("Enter Hotel id : ");
                    long hotelId = scanner.nextLong();
                    scanner.nextLine();
                    roomService.getAllRoomsByHotelId(hotelId);
                    break;
                case 0:
                    exit = true;
                    break;
                default:
                    System.out.println("Invalid choice!");
                    break;
            }
        }
    }

    //3. GUEST OPERATIONS
    private static void displayGuestOperationsMenu(GuestService guestService) {

        boolean exit = false;
        while (!exit) {
            System.out.println("==== Guest Operations ====");
            System.out.println("1. Add a New Guest");
            System.out.println("2. Find Guest By ID");
            System.out.println("3. Delete Guest By ID");
            System.out.println("4. Find All Guests");
            System.out.println("5. Update Guest By ID");
            System.out.println("0. Return to Main Menu");
            System.out.println("Enter your choice");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 :
                    guestService.saveGuest();
                    break;
                case 2 :
                    System.out.println("Enter guest id : ");
                    long guestId2 = scanner.nextLong();
                    scanner.nextLine();
                    guestService.findGuestById(guestId2);
                    break;
                case 3:
                    System.out.println("Enter guest id : ");
                    long guestId3 = scanner.nextLong();
                    scanner.nextLine();
                    guestService.deleteGuestById(guestId3);
                    break;
                case 4:
                    guestService.getAllGuests();
                    break;
                case 5:
                    System.out.println("Enter guest id : ");
                    long guestId5 = scanner.nextLong();
                    scanner.nextLine();
                    guestService.updateGuestById(guestId5);
                    break;
                case 0:
                    exit = true;
                    break;
                default:
                    System.out.println("Invalid choice!");
                    break;
            }
        }
    }

    //4. RESERVATION OPERATIONS
    private static void displayReservationOperationsMenu(ReservationService reservationService) {

        boolean exit = false;
        while (!exit) {

            System.out.println("===== Reservation Operations ======");
            System.out.println("1. Add a new Reservation");
            System.out.println("2. Find Reservation By ID");
            System.out.println("3. Find All Reservations");
            System.out.println("4. Delete Reservation By ID");
            System.out.println("5. Update Reservation By ID");
            System.out.println("0. Return to Main Menu");
            System.out.println("Enter your choice");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:

                    try {
                        reservationService.createReservation();
                    } catch (Exception e) {
                        System.out.println("Exception : " + e.getMessage());
                        System.out.println("Please try a diffrent date or room");
                    }
                    break;
                case 2:
                    System.out.println("Enter reservation id : ");
                    long resId2 = scanner.nextLong();
                    scanner.nextLine();
                    reservationService.findReservationById(resId2);
                    break;
                case 3:
                    reservationService.getAllReservations();
                    break;
                case 4:
                    System.out.println("Enter reservation id :");
                    long resId4 = scanner.nextLong();
                    scanner.nextLine();
                    reservationService.deleteReservationById(resId4);
                    break;
                case 5:
                    try {
                        System.out.println("Enter reservation id : ");
                        long resId5 = scanner.nextLong();
                        scanner.nextLine();
                        reservationService.updateReservation(resId5);
                    } catch (Exception e) {
                        System.out.println("An error occurred during the update : " + e.getMessage());
                    }
                    break;
                case 0:
                    exit = true;
                    break;
                default:
                    System.out.println("Invalid choice!");
                    break;
            }
        }
    }
}