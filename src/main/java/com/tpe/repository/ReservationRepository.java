package com.tpe.repository;

import com.tpe.config.HibernateUtils;
import com.tpe.domain.Reservation;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;

public class ReservationRepository {

    private Session session;

    //1- -------------ID ile Bulma
    public Reservation findById(Long id) {

        Reservation reservation = null;

        try {

            session = HibernateUtils.getSessionFactory().openSession();
            reservation = session.find(Reservation.class, id);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            HibernateUtils.closeSession(session);
        }
        return reservation;

    }

    //2- -------------Tümünü Listeleme
    public List<Reservation> findAll() {

        List<Reservation> reservations = new ArrayList<>();

        try {

            session = HibernateUtils.getSessionFactory().openSession();
            reservations = session.createQuery("FROM Reservation", Reservation.class).getResultList();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            HibernateUtils.closeSession(session);
        }
        return reservations;
    }

    //3- -------------SAVE (Rezervasyon Kaydetme)
    public void save(Reservation reservation) {
        try {

            session = HibernateUtils.getSessionFactory().openSession();
            Transaction tx = session.beginTransaction();

            session.persist(reservation);
            tx.commit();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            HibernateUtils.closeSession(session);
        }
    }

    //4- -------------DELETE (Rezervasyon İptali/Silme)
    public void delete(Reservation reservation) {

        try {

            session = HibernateUtils.getSessionFactory().openSession();
            Transaction tx = session.beginTransaction();

            session.remove(reservation);
            tx.commit();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            HibernateUtils.closeSession(session);
        }

    }

    //5- -------------UPDATE (Rezervasyon Güncelleme)
    public void update(Reservation reservation) {

        try {

            session = HibernateUtils.getSessionFactory().openSession();
            Transaction tx = session.beginTransaction();

            session.merge(reservation);
            tx.commit();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            HibernateUtils.closeSession(session);
        }

    }

}
