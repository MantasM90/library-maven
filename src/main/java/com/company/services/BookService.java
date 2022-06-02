package com.company.services;
import com.company.books.Book;
import com.company.books.Genre;
import com.company.exeptions.BookException;
import com.company.exeptions.UserException;
import com.company.users.User;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class BookService {

    private SessionFactory sessionFactory;

    public BookService() {

        try {
            sessionFactory = new Configuration().configure().buildSessionFactory();
        } catch (HibernateException e) {
            throw e;
        }
    }

    public List<Book> getAllBooks()  {

        Session session = sessionFactory.openSession();

        Query<Book> query = session.createQuery("from Book", Book.class);
        return query.getResultList();
    }


    public List<Book> getBookByTitle(String title) throws BookException {

        Session session = sessionFactory.openSession();

        Query<Book> query = session.createQuery("Select b from Book b where title =:title", Book.class);
        query.setParameter("title", title);
        List<Book> booksByTitle = query.getResultList();
        if (booksByTitle.isEmpty()) {
            throw new BookException(String.format("Books with title '%s' doesn't exist\n", title));
        }
        return booksByTitle;
    }

    public List<Book> getAllBooksByGenre(Genre genre) throws BookException {

        Session session = sessionFactory.openSession();

        Query<Book> query = session.createQuery("Select b from Book b where genre =:genre", Book.class);
        query.setParameter("genre", genre);
        List<Book> booksByGenre = query.getResultList();
        if (booksByGenre.isEmpty()) {
            throw new BookException(String.format("Books with genre '%s' doesn't exist\n", genre));
        }
        return booksByGenre;
    }

    public void addBook(Book book) throws BookException {

        Transaction tx = null;

        try (Session session = sessionFactory.openSession()) {
            tx = session.beginTransaction();
            session.save(book);
            tx.commit();
        } catch (PersistenceException e) {
            if (tx != null) {
                tx.rollback();
            }
            throw new BookException(String.format("Book '%s' already exists", book.getTitle()));
        }
    }

    public void deleteBook(String title) throws BookException {

        Session session = sessionFactory.openSession();

        Query<Book> q = session.createQuery("Select b from Book b where b.title =:title", Book.class);
        q.setParameter("title", title);

        Book toDelete = null;
        try {
            toDelete = q.getSingleResult();
        }catch (NoResultException e) {
            throw new BookException(String.format("Book '%s' doesn't exist", title));
        }
        Transaction tx = session.beginTransaction();
        try {
            session.delete(toDelete);
            tx.commit();
        }catch (Exception e) {
            tx.rollback();
        }finally {
            session.close();
        }
    }


}
