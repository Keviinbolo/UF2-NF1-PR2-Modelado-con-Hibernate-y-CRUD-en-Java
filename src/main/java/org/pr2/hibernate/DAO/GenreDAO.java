package org.pr2.hibernate.DAO;


import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.pr2.hibernate.Entidades.Genre;
import org.pr2.hibernate.util.HibernateUtil;

import java.util.Collections;
import java.util.List;

public class GenreDAO {

    public void guardar( Genre genre) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.save(genre);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();

        }
    }

    public List<Genre> obtenerTodos() {
        String hql = "select distinct g from Genre g left join fetch g.games";
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            Query<Genre> query = session.createQuery(hql, Genre.class);
            List<Genre> resultados = query.getResultList();
            tx.commit();
            return resultados;
        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    public Genre obtenerPorId(int id) {
        String hql = "select g from Genre g left join fetch g.games where g.id = :id";
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            Query<Genre> query = session.createQuery(hql, Genre.class);
            query.setParameter("id", id);
            Genre genre = query.uniqueResult();
            tx.commit();
            return genre;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void actualizar(Genre genre) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.update(genre);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }

    public void eliminar(Genre genre) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.delete(genre);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }


}
