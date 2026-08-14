package com.tpe.controller;

import com.tpe.config.HibernateUtils;
import org.hibernate.Session;

public class Runner {
    public static void main(String[] args) {

/*        System.out.println("------Veritabani testi baslatiliyor-------");

        try {

            Session session = HibernateUtils.getSessionFactory().openSession();

            if (session != null && session.isOpen()) {

                System.out.println(" BASARILI: Veritabanina baglandiniz ve Session acildi");

                HibernateUtils.closeSession(session);
                HibernateUtils.shutdown();
                System.out.println("----------Baglanti guvenli bir sekilde kapatildi-----------");

            }

        } catch (Exception e) {
            System.err.println("HATA: Baglanti kurulurken bir sorun olustu!");
            e.printStackTrace();
        }*/

        //Uygulamayi baslat
        HotelManagementApp.displayHotelManagementAppMenu();

    }
}