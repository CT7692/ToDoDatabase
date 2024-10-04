package org.example;
import org.hibernate.Session;

public class TaskDaoFactory {

    private static TaskDao taskDao;

    private TaskDaoFactory(){}

    public static TaskDao getTaskDao(Session session) {
        if(taskDao == null)
            taskDao = new TaskDaoImpl(session);
        return taskDao;
    }
}
