package org.example;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class TaskDaoImpl implements TaskDao {

    private final Session session;

    public TaskDaoImpl(Session session) {
        this.session = session;
    }

    @Override
    public void addTask(Task task) {
        Transaction transaction = session.beginTransaction();
        session.save(task);
        transaction.commit();
    }

    @Override
    public void updateTask(int id, String status) {
        try {
            Task task = session.get(Task.class, id);
            task.setCategory(status);
            Transaction transaction = session.beginTransaction();
            session.save(task);
            transaction.commit();
            System.out.println("Task updated.");

        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void deleteTask(int id) {
        try {
            Task task = session.get(Task.class, id);
            Transaction transaction = session.beginTransaction();
            session.delete(task);
            transaction.commit();
            System.out.println("Task deleted.");
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }

    }

    @Override
    public List<Task> getTasks() {
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Task> criteriaQuery = criteriaBuilder.createQuery(Task.class);
        criteriaQuery.from(Task.class);

        return session.createQuery(criteriaQuery).getResultList();
    }
}
