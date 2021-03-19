package com.franktran;

import com.franktran.model.Contact;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.util.List;

public class Application {

    // Hold a reusable reference to a SessionFactory (sine we need only one)
    private static final SessionFactory SESSION_FACTORY = buildSessionFactory();

    private static SessionFactory buildSessionFactory() {
        // Create a StandardServiceRegistry
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure().build();
        return new MetadataSources(registry).buildMetadata().buildSessionFactory();
    }

    public static void main(String[] args) {
        Contact contact = new Contact.ContactBuilder("Frank", "Tran")
                .withEmail("franktran@gmail.com")
                .withPhone(1112223334L)
                .build();
        save(contact);

        List<Contact> contacts = fetchAllContacts();
        contacts.stream().forEach(System.out::println);
    }

    @SuppressWarnings("unchecked")
    private static List<Contact> fetchAllContacts() {
        // Open a session
        Session session = SESSION_FACTORY.openSession();

        // Create Criteria
        Criteria criteria = session.createCriteria(Contact.class);
        List<Contact> contacts = criteria.list();

        // Close the session
        session.close();

        return contacts;
    }

    public static void save(Contact contact) {
        // Open a session
        Session session = SESSION_FACTORY.openSession();

        // Begin a transaction
        session.beginTransaction();

        // Use the session to save the contact
        session.save(contact);

        // Commit the transaction
        session.getTransaction().commit();

        // Close the session
        session.close();
    }
}
