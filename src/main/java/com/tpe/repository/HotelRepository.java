package com.tpe.repository;

import com.tpe.config.HibernateUtils;
import com.tpe.domain.Hotel;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;

public class HotelRepository {

    // Hibernate ile DB işlemleri yapmak için Session objesine ihtiyacımız var.
    private Session session;

    // ----- 1. SAVE (Otel Kaydetme) -----
    public void save(Hotel hotel) {
        try {

            // 1. Session Başlatma: Veritabanı ile bağlantı kuruyoruz.
            session = HibernateUtils.getSessionFactory().openSession();

            // 2. Transaction Başlatma: Yazma (Insert/Update/Delete) işlemleri bir transaction içinde olmalıdır.
            Transaction tx = session.beginTransaction();

            // 3. Persist İşlemi: Java objesini veritabanına kaydeder. SQL: INSERT INTO t_hotel...
            session.persist(hotel);

            // 4. Commit: İşlemi onayla ve veritabanına kalıcı olarak işle.
            tx.commit();

        } catch (Exception e) {

            System.out.println(e.getMessage());
            //Best Practice: Hata durumunda catch blogunda rollback yapmaktir yani islemi geri almaktir

        } finally {

            // 5. Kaynak Yönetimi: Session kapatılarak bağlantı havuza iade edilir.
            HibernateUtils.closeSession(session);

        }
    }

    // ----- 2. FIND BY ID (ID ile Otel Bulma) -----
    public Hotel findById(Long id) {

        Hotel hotel = null;

        try {

            session = HibernateUtils.getSessionFactory().openSession();

            //Okuma islemi veri tabaninda degisiklik yapmaz, bu sebeple Transaction baslatmak zorunlu degildir
            //SELECT * FROM t_hotel WHERE id = ?

            hotel = session.find(Hotel.class, id);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            HibernateUtils.closeSession(session);
        }
        return hotel;

    }

    // ----- 3. FIND ALL (Tüm Otelleri Listeleme) ----
    public List<Hotel> findAll() {

        List<Hotel> hotels = new ArrayList<>();

        try {
            session = HibernateUtils.getSessionFactory().openSession();
            //SQL: SELECT * FROM t_hotel
            hotels = session.createQuery("FROM Hotel", Hotel.class).getResultList();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            HibernateUtils.closeSession(session);
        }

        return hotels;

    }

    // ----- 4. DELETE (Otel Silme) -----
    public void delete(Hotel hotel) {
        try {

            session = HibernateUtils.getSessionFactory().openSession();
            Transaction tx = session.beginTransaction(); //Yazma islemi oldugundan Transaction actik

            //SQL: DELETE FROM t_hotel WHERE id = ?
            session.remove(hotel);
            tx.commit();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            HibernateUtils.closeSession(session);
        }
    }

    // ----- 5. UPDATE (Otel Güncelleme) -----
    public void update(Hotel existingHotel) {

        try {

            session = HibernateUtils.getSessionFactory().openSession();
            //merge() bir objedeki degisiklikleri veritabanindaki kayitla birlestirir ve gunceller
            Transaction tx = session.beginTransaction();

            session.merge(existingHotel); //1,A,Y --> 1,B,X
            tx.commit();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            HibernateUtils.closeSession(session);
        }

    }

}