package org.pr2.hibernate.DAO;


import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.pr2.hibernate.Entidades.Team;
import org.pr2.hibernate.util.HibernateUtil;

import java.util.Collections;
import java.util.List;

public class TeamDAO {

    public void guardar( Team team) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.save(team);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();

        }
    }

    public List<Team> obtenerTodos() {
        String hql = "select distinct t from Team t left join fetch t.games";
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            Query<Team> query = session.createQuery(hql, Team.class);
            List<Team> resultados = query.getResultList();
            tx.commit();
            return resultados;
        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    public Team obtenerPorId(int id) {
        String hql = "select t from Team t left join fetch t.games where t.id = :id";
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            Query<Team> query = session.createQuery(hql, Team.class);
            query.setParameter("id", id);
            Team team = query.uniqueResult();
            tx.commit();
            return team;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void actualizar(Team team) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.update(team);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }

    public void eliminar(Team team) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.delete(team);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }


}
