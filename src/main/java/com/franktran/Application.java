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
        int id = save(contact);

        // Display a list of contacts before the update
        System.out.printf("Before update:%n");
        fetchAllContacts().stream().forEach(System.out::println);

        // Get the persisted contact
        Contact c = findContactById(id);

        // Update the contact
        c.setFirstName("Henry");

        // Persist the changes
        System.out.println("Updating...");
        update(c);
        System.out.println("Update complete!");

        // Display a list of contacts after the update
        System.out.printf("After update:%n");
        fetchAllContacts().stream().forEach(System.out::println);

        // Get the contact with id of 1
        c = findContactById(1);

        // Delete the contact
        System.out.println("%Deleting...");
        delete(c);
        System.out.println("Deleted!");
        System.out.println("After delete:");
        fetchAllContacts().stream().forEach(System.out::println);
    }

    private static Contact findContactById(int id) {
        // Open a session
        Session session = SESSION_FACTORY.openSession();

        // Retrieve the persistent object (or null if not found)
        Contact contact = session.get(Contact.class, id);

        // Close the session
        session.close();

        return contact;
    }

    private static void update(Contact contact) {
        // Open a session
        Session session = SESSION_FACTORY.openSession();

        // Begin a transaction
        session.beginTransaction();

        // Use the session to update the contact
        session.update(contact);

        // Commit the transaction
        session.getTransaction().commit();

        // Close the session
        session.close();
    }

    private static void delete(Contact contact) {
        // Open a session
        Session session = SESSION_FACTORY.openSession();

        // Begin a transaction
        session.beginTransaction();

        // Use the session to update the contact
        session.delete(contact);

        // Commit the transaction
        session.getTransaction().commit();

        // Close the session
        session.close();
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

    public static int save(Contact contact) {
        // Open a session
        Session session = SESSION_FACTORY.openSession();

        // Begin a transaction
        session.beginTransaction();

        // Use the session to save the contact
        int id = (int) session.save(contact);

        // Commit the transaction
        session.getTransaction().commit();

        // Close the session
        session.close();

        return id;
    }
}
