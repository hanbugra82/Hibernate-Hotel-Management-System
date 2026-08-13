package com.tpe.exception;

public class RoomAlreadyReservedException extends RuntimeException{
    public RoomAlreadyReservedException(String message) {
        super(message);
    }
}