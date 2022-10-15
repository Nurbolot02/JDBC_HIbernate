package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.ModelConnector;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.io.File;


public class Util {
    // реализуйте настройку соеденения с БД
    private static ModelConnector modelConnector;
    private static final SessionFactory sessionFactory = buildSessionFactory();


    private static SessionFactory buildSessionFactory() {
        try {
            return new Configuration().configure(new File("src/main/resources/hibernate.cfg.xml")).buildSessionFactory();
        } catch (Throwable e){
            System.err.println(" init sessionFactory creation failed " + e);
            throw  new ExceptionInInitializerError();
        }
    }

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null){
            buildSessionFactory();
        }
        return sessionFactory;
    }

    public Util(ModelConnector modelConnector) {
        Util.modelConnector = modelConnector;
    }

    public static ModelConnector getModelConnector() {
        return modelConnector;
    }

}
