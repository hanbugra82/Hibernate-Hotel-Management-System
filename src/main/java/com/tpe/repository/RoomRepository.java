package com.tpe.repository;

import com.tpe.config.HibernateUtils;
import com.tpe.domain.Room;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;

public class RoomRepository {

    private Session session;

    // 1- ------------SAVE (Oda Kaydetme)
    public void save(Room room) {
        try {

            session = HibernateUtils.getSessionFactory().openSession();
            Transaction tx = session.beginTransaction();

            session.persist(room);
            tx.commit();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            HibernateUtils.closeSession(session);
        }
    }

    // 2- ------------ FIND BY ID
    public Room findById(Long id) {

        Room room = null;

        try {

            session = HibernateUtils.getSessionFactory().openSession();
            room = session.find(Room.class, id);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            HibernateUtils.closeSession(session);
        }
        return room;
    }

    // 3- ------------ FIND ALL
    public List<Room> findAll() {

        List<Room> rooms = new ArrayList<>();

        try {

            session = HibernateUtils.getSessionFactory().openSession();
            rooms = session.createQuery("FROM Room", Room.class).getResultList();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            HibernateUtils.closeSession(session);
        }
        return rooms;

    }

    // 4- ------------ DELETE
    public void delete(Room room) {
        try {
            session = HibernateUtils.getSessionFactory().openSession();
            Transaction tx = session.beginTransaction();

            session.remove(room);
            tx.commit();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            HibernateUtils.closeSession(session);
        }
    }

    // 5- ------------ UPDATE
    public void update(Room existingRoom) {
        try {

            session = HibernateUtils.getSessionFactory().openSession();
            Transaction tx = session.beginTransaction();

            session.merge(existingRoom);
            tx.commit();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            HibernateUtils.closeSession(session);
        }
    }

    // ---------------------------------------------------------------
    //  YENİ METOT: Belirli Bir Otelin Odalarını Bulma
    // ---------------------------------------------------------------
    public List<Room> findAllRoomsByHotelId(Long hotelId) {

        List<Room> rooms = new ArrayList<>();

        try {
            session = HibernateUtils.getSessionFactory().openSession();
            String hql = "FROM Room r WHERE r.hotel.id = :id";

            rooms = session.createQuery(hql, Room.class).
                    setParameter("id", hotelId).
                    getResultList();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            HibernateUtils.closeSession(session);
        }
        return rooms;
    }

}
