package com.tpe.repository;

import com.tpe.config.HibernateUtils;
import com.tpe.domain.Guest;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;

public class GuestRepository {

    private Session session;

    // 1- ------------ ID ile Bulma
    public Guest findById(Long id) {

        Guest guest = null;

        try {

            session = HibernateUtils.getSessionFactory().openSession();
            guest = session.find(Guest.class, id);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            HibernateUtils.closeSession(session);
        }
        return guest;

    }

    // 2- ------------ Tümünü Listeleme
    public List<Guest> findAll() {

        List<Guest> guests = new ArrayList<>();

        try {

            session = HibernateUtils.getSessionFactory().openSession();
            guests = session.createQuery("FROM Guest", Guest.class).getResultList();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            HibernateUtils.closeSession(session);
        }
        return guests;
    }

    // 3- -------------- SAVE OR UPDATE (Kaydet veya Güncelle)
    public void saveOrUpdate(Guest guest) {

        try {

            session = HibernateUtils.getSessionFactory().openSession();
            Transaction tx = session.beginTransaction();

            //Merge() kod tekrarini onler
            //1. Eger guest objesinin ID'si yoksa --> Yeni kayit olusturur (INSERT)
            //2. Eger ID'si varsa ve DB'de kayitliysa --> Veriyi gunceller (UPDATE)
            session.merge(guest);
            tx.commit();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            HibernateUtils.closeSession(session);
        }

    }

    // 4- -------------- DELETE
    public void delete(Guest guest) {
        try {
            session = HibernateUtils.getSessionFactory().openSession();
            Transaction tx = session.beginTransaction();

            session.remove(guest);
            tx.commit();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            HibernateUtils.closeSession(session);
        }
    }

}








