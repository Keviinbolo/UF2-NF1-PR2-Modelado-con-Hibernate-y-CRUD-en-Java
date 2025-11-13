package org.pr2.hibernate.DAO;


import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.pr2.hibernate.Entidades.Review;
import org.pr2.hibernate.util.HibernateUtil;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class ReviewDAO {

    public void guardar( Review review) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.save(review);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();

        }
    }

    public List<Review> obtenerTodos() {
        String hql = "select r from Review r left join fetch r.game";
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            Query<Review> query = session.createQuery(hql, Review.class);
            List<Review> resultados = query.getResultList();
            tx.commit();
            return resultados;
        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    public Review obtenerPorId(int id) {
        String hql = "select r from Review r left join fetch r.game where r.id = :id";
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            Query<Review> query = session.createQuery(hql, Review.class);
            query.setParameter("id", Optional.of(id));
            Review review = query.uniqueResult();
            tx.commit();
            return review;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void actualizar(Review review) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.update(review);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }

    public void eliminar(Review review) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.delete(review);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }

}
