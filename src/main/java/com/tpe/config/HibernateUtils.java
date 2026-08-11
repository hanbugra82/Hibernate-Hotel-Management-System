package com.tpe.config;

import com.tpe.domain.Guest;
import com.tpe.domain.Hotel;
import com.tpe.domain.Reservation;
import com.tpe.domain.Room;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

//Hibernate'in motor dairesi : SessionFactory Yonetimi
public class HibernateUtils {

    //1.SessionFactory : Veritabani ile ana baglanti hattidir
    private static final SessionFactory sessionFactory;

    //2.Statik Blok : Class bellege yuklendiginde OTOMATIK ve BIR KERE calisir
    static {

        try {

            //Config dosyasini oku
            Configuration configuration = new Configuration().configure();

            configuration.addAnnotatedClass(Hotel.class);
            configuration.addAnnotatedClass(Room.class);
            configuration.addAnnotatedClass(Guest.class);
            configuration.addAnnotatedClass(Reservation.class);

            sessionFactory = configuration.buildSessionFactory();

        } catch (Exception e) {

            System.err.println("Initialization of session factory is FAILED");
            throw new ExceptionInInitializerError(e);// e hem exception ın ismini yazdırıyor hemde hata mesajını yazdırır. e.getMessage yazsaydik sadece hata mesajını verirdi. Biz burada programın çalışmasını durdurmak istedik bilerek. e.printStackTrace() ve e.getMessage() çalişmayi durdurmaz. Ornek asagidaki gibi :
            /*
            // A) Sadece mesajı alırız (Sessizce metni döndürür)
            String mesaj = e.getMessage();
            System.out.println("Yakaladığımız Mesaj: " + mesaj);

            // B) Konsola tam hata izini basar
            e.printStackTrace();
             */

        }
    }

    //3.Getter - fabrikaya erismek icin (final degismez oldugundan getter otomatik sağ click ile olusturamayiz, generate objeler icin tasarlanmiştir static ve final icin olusturulamazlar, bu yzuden get i elimiz ile yazariz)
    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    //4. Shutdown
    public static void shutdown() { //normalde sf hibernate de biz direk kapattık ancak if kontrol şartıyla yapılması gerekir.
        if (sessionFactory != null && sessionFactory.isOpen()) {
            sessionFactory.close();
        }
    }

    //5. Session kapatma, fabrika kapatıldıgında session da kapatırdik, hangi session - birden fazla session olabildiginden session session nesnesi olusturduk parametreli olarak
    public static void closeSession(Session session) {
        if (session != null && session.isOpen()) {
            session.close();
        }
    }
}
