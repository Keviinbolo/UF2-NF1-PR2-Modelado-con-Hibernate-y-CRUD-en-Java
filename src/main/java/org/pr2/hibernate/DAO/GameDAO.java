package org.pr2.hibernate.DAO;


import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.pr2.hibernate.Entidades.Game;
import org.pr2.hibernate.util.HibernateUtil;

import java.util.Collections;
import java.util.List;

public class GameDAO {

    public void guardar(Game game) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.save(game);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();

        }
    }

    public Game obtenerPorId(int id) {
        String hql = "select g from Game g " +
                "left join fetch g.teams " +
                "left join fetch g.genres " +
                "left join fetch g.reviews " +
                "where g.idGame = :id";
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            Query<Game> query = session.createQuery(hql, Game.class);
            query.setParameter("id", id);
            Game game = query.uniqueResult();
            tx.commit();
            return game;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Game> obtenerTodos() {
        String hql = "select distinct g from Game g " +
                "left join fetch g.teams " +
                "left join fetch g.genres " +
                "left join fetch g.reviews";
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            Query<Game> query = session.createQuery(hql, Game.class);
            List<Game> resultados = query.getResultList();
            tx.commit();
            return resultados;
        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    public void actualizar(Game game) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.update(game);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }

    public void eliminar(Game game) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.delete(game);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }


}
